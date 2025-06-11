package com.swfinal.register;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class RegisterController {
    private final RegisterService registerService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/request-register")
    @ResponseBody
    public Map<String, Object> register(@RequestBody Map<String, Object> params) {
        log.info("파라미터 : {}", params);
        return registerService.register(params);
    }

    @GetMapping("/{user-id}")
    public ModelAndView register(@PathVariable(value = "user-id") String id) {
        Map<String, Object> result = new HashMap<>();
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> memberInfo = registerService.getUser(id);

        result.put("MEMBER_INFO", memberInfo);
        modelAndView.addObject("result", memberInfo);

        modelAndView.setViewName("member-detail");
        return modelAndView;
    }

    @PostMapping("/request-remove")
    @ResponseBody
    public Map<String, Object> deleteMember(@RequestBody Map<String, Object> parmas) {
        return registerService.deleteMember(parmas);
    }
}
