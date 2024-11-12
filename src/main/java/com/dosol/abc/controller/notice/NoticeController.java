package com.dosol.abc.controller.notice;

import com.dosol.abc.dto.notice.NoticeDTO;
import com.dosol.abc.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 공지사항 리스트 페이지를 반환 (페이징 포함)
    @GetMapping("/list")
    public String list(Model model, Pageable pageable) {
        Page<NoticeDTO> notices = noticeService.getNotices(pageable);
        model.addAttribute("notices", notices);
        return "notice/list";  // list.html 템플릿을 반환
    }

    // 공지사항 생성 페이지 반환
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("notice", new NoticeDTO());
        return "notice/create";  // create.html 템플릿을 반환
    }

    // 공지사항 생성 데이터 처리
    @PostMapping("/create")
    public String create(NoticeDTO noticeDTO) {
        noticeService.createNotice(noticeDTO);
        return "redirect:/notice/list";
    }

    // 공지사항 수정 페이지 반환
    @GetMapping("/edit/{noticeId}")
    public String editForm(@PathVariable Long noticeId, Model model) {
        NoticeDTO noticeDTO = noticeService.getNoticeById(noticeId);
        model.addAttribute("notice", noticeDTO);
        return "notice/edit";  // edit.html 템플릿을 반환
    }

    // 공지사항 수정 데이터 처리
    @PostMapping("/edit/{noticeId}")
    public String update(@PathVariable Long noticeId, NoticeDTO noticeDTO) {
        noticeService.updateNotice(noticeId, noticeDTO);
        return "redirect:/notice/list";
    }

    // 공지사항 삭제 처리
    @PostMapping("/delete/{noticeId}")
    public String delete(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return "redirect:/notice/list";
    }
}
