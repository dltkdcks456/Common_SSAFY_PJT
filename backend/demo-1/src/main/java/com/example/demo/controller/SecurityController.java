package com.example.demo.controller;


import com.example.demo.service.SecurityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/security")  // 여기서 jwt 토큰을 테스트해볼 수 있다.
public class SecurityController {
    @Autowired
    private SecurityService securityService;

    @GetMapping("/gen/token")
    public Map<String, Object> genToken(@RequestBody HashMap<String, String> param) {
        String subject = param.get("id");
        String token = securityService.createToken(subject, (120 * 1000 * 60));
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("result", token);
        return map;
    }

    @ResponseBody
    @GetMapping("/get/subject")
    public Map<String, Object> getSubject(@RequestBody HashMap<String, String> param) {
        String token = param.get("token");
        String subject = securityService.getSubject(token);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("result", subject);
        return map;
    }
}
