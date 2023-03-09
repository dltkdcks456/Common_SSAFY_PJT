package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RegisterMail;

@RestController
@RequestMapping(value = "/api/mail")
public class AccountController {

    @Autowired
    RegisterMail registerMail;
    UserRepository userRepository;

    //127.0.0.1:8080/api/mail/mailcert (form-data -> key : email, value - ? )
    @PostMapping(value = "/mailcert")
    public String mailConfirm(@RequestParam(name = "email") String email) throws Exception{
        String code = registerMail.sendSimpleMessage(email);
        System.out.println("사용자에게 발송한 인증코드 ==> " + code);

        return code;
    }


}