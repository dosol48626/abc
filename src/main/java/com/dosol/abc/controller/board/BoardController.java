package com.dosol.abc.controller.board;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.dto.board.PageRequestDTO;
import com.dosol.abc.dto.board.PageResponseDTO;
import com.dosol.abc.dto.board.upload.UploadFileDTO;
import com.dosol.abc.service.board.BoardService;
import com.dosol.abc.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Value("${com.dosol.abc.upload.path}")
    private String uploadPath;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public void registerGET() {
    }

    @PostMapping("/register")
    public String registerPOST(BoardDTO boardDTO, HttpSession session, UploadFileDTO uploadFileDTO) {

        User user = (User) session.getAttribute("user");

        boardDTO.setUsername(user.getUsername());


        // 파일 업로드 처리
        List<String> strFileNames = null;
        if (uploadFileDTO.getFiles() != null && !uploadFileDTO.getFiles().get(0).getOriginalFilename().equals("")) {
            strFileNames = fileUpload(uploadFileDTO);
            log.info("Uploaded Files: " + strFileNames.size());
        }
        boardDTO.setFileNames(strFileNames);

        log.info("Board POST register with files...");

        Long boardId = boardService.register(boardDTO);
        return "redirect:/board/list";
    }


    private List<String> fileUpload(UploadFileDTO uploadFileDTO) {
        List<String> list = new ArrayList<>();
        uploadFileDTO.getFiles().forEach(multipartFile -> {
            String originalName = multipartFile.getOriginalFilename();
            log.info(originalName);

            String uuid = UUID.randomUUID().toString();
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

            try {
                multipartFile.transferTo(savePath); // 서버에 파일 저장

                // 이미지 파일인 경우 썸네일 생성
                if (Files.probeContentType(savePath).startsWith("image")) {
                    File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalName);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                }
                list.add(uuid + "_" + originalName); // 업로드된 파일명 추가
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return list;
    }



    @GetMapping("/read")
    public void readGET(Model model, Long boardId, HttpSession session) {
        User user = (User)session.getAttribute("user");

        // 세션에 로그인된 사용자의 username을 모델에 추가
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }

        // 게시글 데이터를 가져와 모델에 추가
        BoardDTO boardDTO = boardService.readOne(boardId);
        model.addAttribute("boardDTO", boardDTO);
    }

    @GetMapping("/modify")
    public void modifyGET(Model model, Long boardId) {
        BoardDTO boardDTO = boardService.readOne(boardId);
        model.addAttribute("boardDTO", boardDTO);
    }

//    @PostMapping("/modify")
//    public String modifyPOST(BoardDTO boardDTO) {
//
//        boardService.modify(boardDTO);
//        return "redirect:/board/read?boardId=" + boardDTO.getBoardId();
//    }

    @PostMapping("/modify")
    public String modify(UploadFileDTO uploadFileDTO,
                         PageRequestDTO pageRequestDTO,
                         @Valid BoardDTO boardDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){

        log.info("board modify post......." + boardDTO);

        List<String> strFileNames=null;
        if(uploadFileDTO.getFiles()!=null &&
                !uploadFileDTO.getFiles().get(0).getOriginalFilename().equals("") ){

            List<String> fileNames = boardDTO.getFileNames();

            if(fileNames != null && fileNames.size() > 0){
                removeFile(fileNames);
            }

            strFileNames=fileUpload(uploadFileDTO);
            log.info("!!!!!!!!!!!!!!!!"+strFileNames.size());
            boardDTO.setFileNames(strFileNames);
        }

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            redirectAttributes.addAttribute("boardId", boardDTO.getBoardId());
            return "redirect:/board/modify?"+link;
        }

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("boardId", boardDTO.getBoardId());
        return "redirect:/board/read";
    }

    private void removeFile(List<String> fileNames){
        log.info("AAAAA"+fileNames.size());

        for(String fileName:fileNames){
            log.info("fileRemove method: "+fileName);
            Resource resource = new FileSystemResource(uploadPath+File.separator + fileName);
            String resourceName = resource.getFilename();

            // Map<String, Boolean> resultMap = new HashMap<>();
            boolean removed = false;

            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                removed = resource.getFile().delete();

                //섬네일이 존재한다면
                if(contentType.startsWith("image")){
                    String fileName1=fileName.replace("s_","");
                    File originalFile = new File(uploadPath+File.separator + fileName1);
                    originalFile.delete();
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }





    @GetMapping("/remove")
    public String removeGET(Long boardId) {
        boardService.remove(boardId);
        return "redirect:/board/list";
    }
}
