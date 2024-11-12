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

        //serviceImpl에서 이미 해놔서 굳이 User객체를 가져올 필요가 없다.
        User user = (User) session.getAttribute("user"); // 세션에서 user를 가져옴 (예시)
        //String userId = String.valueOf(user.getUserId());

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
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<String> strFileNames = new ArrayList<>();

        // user 객체가 존재하는 경우에만 username을 설정
        if (user != null) {
            notesDTO.setUsername(user.getUsername());
        } else {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        // 파일 이름 설정 로직
        if (uploadFileDTO.getFiles() != null &&
                !uploadFileDTO.getFiles().get(0).getOriginalFilename().equals("")) {
            strFileNames = fileUpload(uploadFileDTO);
        }

        notesDTO.setFileNames(strFileNames); // notesDTO에 파일 이름들 추가
        log.info("Before Saving to DB, notesDTO: " + notesDTO);

        // 노트 저장 및 생성된 noteId 반환
        Long noteId = notesService.createNote(notesDTO);
        notesDTO.setNoteId(noteId); // 생성된 noteId를 DTO에 설정하여 사용

        redirectAttributes.addFlashAttribute("message", "Note registered successfully");
        return "redirect:/notes/list";
    }



    /*@GetMapping({"/read/{noteId}", "/modify/{noteId}"})
    public void read(Long noteId, PageRequestDTO pageRequestDTO, Model model) {
        log.info("Reading Note with ID: " + noteId);
        NotesDTO notesDTO = notesService.readOne(noteId);
        model.addAttribute("dto", notesDTO);
    }*/

    @GetMapping({"/read", "/modify"})
    public void read(Long noteId, PageRequestDTO pageRequestDTO, Model model) {

        log.info("Reading Note with ID: " + noteId);
        NotesDTO notesDTO = notesService.readOne(noteId);
        model.addAttribute("dto", notesDTO);
    }

    @PostMapping("/modify")
    public String modify(UploadFileDTO uploadFileDTO,
                         PageRequestDTO pageRequestDTO,
                         NotesDTO notesDTO,
                         RedirectAttributes redirectAttributes,
                         HttpSession session) {
        List<String> strFileNames = null;

        User user = (User) session.getAttribute("user");

        // user 객체가 존재하는 경우에만 username을 설정
        if (user != null) {
            notesDTO.setUsername(user.getUsername());
        } else {
            throw new RuntimeException("로그인이 필요합니다.");
        }

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
            log.info("Uploading file: " + originalName);

            String uuid = UUID.randomUUID().toString();
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

            try {
                // 파일 저장
                multipartFile.transferTo(savePath);
                log.info("File saved successfully at: " + savePath);

                // 이미지 파일인지 확인하여 섬네일 생성
                String contentType = Files.probeContentType(savePath);
                if (contentType != null && contentType.startsWith("image")) {
                    File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalName);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    log.info("Thumbnail created for image: " + thumbFile.getPath());
                }

            } catch (IOException e) {
                log.error("Error while saving file: " + originalName, e);
            }

            // 파일 이름을 list에 추가 (uuid_filename 형식)
            list.add(uuid + "_" + originalName);
        });
        return list;
    }


    @GetMapping("/view/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName){
        Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
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
