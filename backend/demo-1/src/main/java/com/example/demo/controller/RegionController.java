package com.example.demo.controller;

import com.example.demo.domain.Dong;
import com.example.demo.domain.Gun;
import com.example.demo.domain.Si;
import com.example.demo.repository.DongRepository;
import com.example.demo.repository.GunRepository;
import com.example.demo.repository.SiRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequestMapping(value = "/api/region")
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분

//
//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class RegionController {
    @Autowired
    DongRepository dongRepository;

    @Autowired
    GunRepository gunRepository;

    @Autowired
    SiRepository siRepository;

    @GetMapping("/si")  // 모든 시 코드와 이름 리턴
    public List<Si> showAllSi() {//@RequestBody HashMap<String, String> param){
        return siRepository.findAll();
    }

    @GetMapping("/gun")  // 모든 군의 시 코드와 군 코드와 이름 리턴
    public List<Gun> showAllGun() {//@RequestBody HashMap<String, String> param){
        return gunRepository.findAll();
    }

    @GetMapping("/dong")  // 모둔 동의 시, 군, 동 코드와 이름 리턴
    public List<Dong> showAllDong() {
        return dongRepository.findAll();
    }

    @GetMapping("si/gun")           // 시 코드가 일치하는 군들을 불러옴
    public List<Gun> showSiGun(String si_code){

        Integer si_code_int =  Integer.valueOf(si_code);
        return gunRepository.findByName(si_code_int);
    }

    @GetMapping("si/gun/dong")      // 시, 군 코드가 일치하는 동을 불러옴
    public List<Dong> showSiGunDong(String si_code, String gun_code){

        Integer si_code_int =  Integer.valueOf(si_code);
        Integer gun_code_int =  Integer.valueOf(gun_code);
        return dongRepository.findByIdAndName(si_code_int, gun_code_int);
        //return dongRepository.findByName(si_code_int, gun_code_int);
//        List<Dong> dongList = dongRepository.findAll();
//        List<Dong> returnList = new ArrayList<Dong>();
//
//        for (int i = 0; i < dongList.size(); i++){
//            if (dongList.get(i).getSi_code().equals(si_code_int)){      // 시 코드가 일치하는 지역
//                if (dongList.get(i).getGun_code().equals(gun_code_int)){    // 군 코드가 일치하는 지역
//                    returnList.add(dongList.get(i));
//                }
//            }
//        }
//
//        //return dongRepository.findAll();
//        return returnList;

        //return dongRepository.findAll();
//        return returnList;
    }
}