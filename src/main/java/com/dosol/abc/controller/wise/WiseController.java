package com.dosol.abc.controller.wise;


import com.dosol.abc.service.wise.WiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/wise")
public class WiseController {
    private final WiseService wiseService;


    @Autowired
    public WiseController(WiseService wiseService) {
        this.wiseService = wiseService;
    }


    @GetMapping("/list")
    public void getAllWises(Model model) {
        model.addAttribute("list",wiseService.getAllWises());
    }


    @GetMapping("/random")
    public void getRandomWise(Model model) {
        model.addAttribute("wise",wiseService.getRandomWise());
    }
}