package com.dosol.abc.controller.note;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.note.NotesDTO;
import com.dosol.abc.dto.note.PageRequestDTO;
import com.dosol.abc.dto.note.PageResponseDTO;
import com.dosol.abc.dto.note.UploadFileDTO;
import com.dosol.abc.service.note.NotesService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NotesController {
    private final NotesService notesService;
    private final ModelMapper modelMapper;

    @Value("${com.dosol.abc.upload.path}")
    private String uploadPath;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model, HttpSession session) {

        /* NotesServiceImpl의 list 메서드를 호출하여 페이징된 데이터를 받아옴
        PageResponseDTO<NotesDTO> responseDTO = notesService.list(pageRequestDTO);
         받아온 페이징 데이터를 Model에 추가하여 뷰에 전달
        model.addAttribute("responseDTO", responseDTO);*/

//        로그인한 사용자 ID 가져오기
        User user = (User) session.getAttribute("user"); // 세션에서 user를 가져옴 (예시)
        String userId = String.valueOf(user.getUserId());
        // NotesService의 list 메서드에 user 전달
        PageResponseDTO<NotesDTO> responseDTO = notesService.list(pageRequestDTO, session);

        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public void registerForm() {
        // 등록 폼 페이지 이동
    }

    @PostMapping("/register")
    public String register(UploadFileDTO uploadFileDTO,
                           NotesDTO notesDTO,
                           RedirectAttributes redirectAttributes) {
        List<String> strFileNames = new ArrayList<>();

        if(uploadFileDTO.getFiles() != null &&
                !uploadFileDTO.getFiles().get(0).getOriginalFilename().equals("")) {
            strFileNames = fileUpload(uploadFileDTO);
        }

        notesDTO.setFileNames(strFileNames); //notesDTO에다가 file이름들 추가
        log.info(notesDTO); // 확인해주고

        Long noteId = notesService.createNote(notesDTO);
        redirectAttributes.addFlashAttribute("message", "Note registered successfully");
        return "redirect:/notes/list";
    }

    @GetMapping({"/read/{noteId}", "/modify/{noteId}"})
    public void read(Long noteId, PageRequestDTO pageRequestDTO, Model model) {
        log.info("Reading Note with ID: " + noteId);
        NotesDTO notesDTO = notesService.readOne(noteId);
        model.addAttribute("dto", notesDTO);
    }

    @PostMapping("/modify")
    public String modify(UploadFileDTO uploadFileDTO,
                         PageRequestDTO pageRequestDTO,
                         NotesDTO notesDTO,
                         RedirectAttributes redirectAttributes) {
        List<String> strFileNames = null;
        if(uploadFileDTO.getFiles() != null &&
                !uploadFileDTO.getFiles().get(0).getOriginalFilename().equals("")) {
            //업로드된 파일이 있다면
            List<String> fileNames = notesDTO.getFileNames();
            // 기존 notesDTO 파일을 가져와서 fileNames를 얻고

            if(fileNames != null && fileNames.size() > 0) {
                removeFile(fileNames);
            }
            //기존 파일 지우고

            strFileNames = fileUpload(uploadFileDTO);
            //새롭게 저장
            notesDTO.setFileNames(strFileNames);
        }

        notesService.modifyNote(notesDTO);
        redirectAttributes.addFlashAttribute("message", "Note modified successfully");
        //redirectAttributes.addAttribute("noteId", notesDTO.getNoteId()); 이렇게 하면 /read/noteId=? 임
        return "redirect:/notes/read/" + notesDTO.getNoteId(); // 수정 후 읽기 페이지로 리다이렉트
    }

    @GetMapping("/remove")
    public String remove(NotesDTO notesDTO, RedirectAttributes redirectAttributes) {

        List<String> fileNames = notesDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0) {
            removeFile(fileNames);
        }
        notesService.deleteNote(notesDTO.getNoteId());
        redirectAttributes.addFlashAttribute("message", "Note removed successfully");
        return "redirect:/notes/list";
    }

    private List<String> fileUpload(UploadFileDTO uploadFileDTO) {
        List<String> list = new ArrayList<>();
        uploadFileDTO.getFiles().forEach(multipartFile -> {
            String originalName = multipartFile.getOriginalFilename();
            log.info(originalName);

            String uuid = UUID.randomUUID().toString();
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
            boolean image = false;

            try {
                multipartFile.transferTo(savePath); //서버에 파일 저장
                //이미지 파일 종류라면 .startswith에 이미지가 있기때문
                if (Files.probeContentType(savePath).startsWith("image")) {
                    image = true;
                    File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalName);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    //이미지가 섬네일 파일이 된다. savePath에 있는 파일을 thumFile로
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(uuid+"_"+originalName);
            //리스트에다가 파일네임을 준다.
        });
        return list;
    }

    @GetMapping("/view/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName){
        Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType( resource.getFile().toPath()));
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    private void removeFile(List<String> fileNames) {
        log.info("ssss" + fileNames.size());
        //지우는 파일이 한개가 아니므로 반복문사용
        for (String fileName : fileNames) {
            Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);
            String resourceName = resource.getFilename();

            boolean removed = false;

            try{
                String contentType = Files.probeContentType(resource.getFile().toPath());
                removed = resource.getFile().delete();
                //원본 파일 지우기

                //만약 섬네일이 존재하는 경우
                if(contentType.startsWith("image")) {
                    String fileName1 = fileName.replace("s_", "");
                    File originalFile = new File(uploadPath+File.separator+fileName1);
                    originalFile.delete();
                }
            } catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }
}
