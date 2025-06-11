package com.swfinal.home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/home")
    @ResponseBody
    public String home() {
        log.info("메인페이지 요청");
        return "Hello, SW Framework!";
    }
}
