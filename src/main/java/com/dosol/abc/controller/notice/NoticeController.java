package com.dosol.abc.controller.notice;

import com.dosol.abc.dto.notice.NoticeDTO;
import com.dosol.abc.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // /notice/list 경로에서 공지사항 리스트 페이지를 반환
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("notices", noticeService.getAllNotices());
        return "notice/list";
    }

    // /notice/create 경로에서 공지사항 생성 페이지를 반환
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("notice", new NoticeDTO());
        return "notice/create";
    }

    // 공지사항 생성 폼에서 제출된 데이터를 처리
    @PostMapping("/create")
    public String create(NoticeDTO noticeDTO) {
        noticeService.createNotice(noticeDTO);
        return "redirect:/notice/list";
    }
}