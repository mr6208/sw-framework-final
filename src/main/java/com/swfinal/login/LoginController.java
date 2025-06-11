package com.swfinal.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/user")
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/request-login")
    @ResponseBody
    public Map<String, Object> requestLogin(@RequestBody Map<String, Object> parmas) {
        return loginService.login(parmas);
    }
}
