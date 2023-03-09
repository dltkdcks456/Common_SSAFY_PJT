package com.example.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.log4j.Log4j2;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Controller
@Log4j2
public class testController {


	@RequestMapping(value = "/api/test")
	public String test() {

		System.out.println("연결되었습니다!!!");
		log.debug("연결되었습니다!");

		return "test";
	}

}