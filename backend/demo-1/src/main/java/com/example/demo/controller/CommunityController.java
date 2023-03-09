package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.mapping.BoardListMapping;
import com.example.demo.mapping.CommentMapping;
import com.example.demo.mapping.CommunityListMapping;
import com.example.demo.repository.*;
import com.example.demo.service.SecurityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@Log4j2
@RequestMapping(value = "/api/community")
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분

//
//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class CommunityController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    ChattingRoomRepository chattingRoomRepository;
    @Autowired
    ChattingRoomUserRepository chattingRoomUserRepository;

    @Autowired
    CommunityParticipateRepository communityParticipateRepository;

    @Autowired
    CommunityBanUserRepository communityBanUserRepository;

    @Autowired
    SecurityService securityService;


    // 방 생성 - 제목 내용 시 군 동 최대인원 출발일, 생성유저아이디
    // 진행상태(boolean 신청 가능(진행) / 신청 불가(마감) )
    @PostMapping("/")
    public ResponseEntity CreateCommunity(@RequestBody HashMap<String, Object> param) { // 토큰과 같이 보내야 한다.

        System.out.println(param);
        String token = (String) param.get("token");
        String id = securityService.getSubject(token);
        //Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
        String title = (String) param.get("title");
        String content = (String) param.get("content");
        String si = (String) param.get("si");
        String gun = (String) param.get("gun");
        String dong = (String) param.get("dong");
        int maxPerson = Integer.parseInt(String.valueOf(param.get("max_person")));  // int로 넣기
        String startTime = (String) param.get("start_time");    // ex) "start_time" : "2023-12-12 05:30:10"
        Boolean inProgress = (Boolean) param.get("in_progress");    // boolean 형태로 넣어주세요

        User user = userRepository.findById(id);

        // 이부분 입력받은 날짜로 대체되는데 어떤건지 잘 몰름
        //LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");   // 시각을 저장할 형태

        //String nowDateTime = now.format(dateTimeFormatter);
        LocalDateTime nowTime = LocalDateTime.parse(startTime, dateTimeFormatter);                  // 입력받은 시간을 저장형태로 바꾸어 저장
        // 여기까지 날짜 관련 바꿔야 되는 부분
        System.out.println(nowTime);


        // 여기서 그룹 채팅방을 생성해주자
        UUID uuid = UUID.randomUUID();
        ChattingRoom chattingRoom = ChattingRoom.builder()
                .name(title)    // 채팅방제목은 미팅룸과 같이 해주자
                .roomId(uuid.toString())
                .roomType(99)   // 커뮤니티 룸타입은 99로 넣자
                .build();
        chattingRoomRepository.save(chattingRoom);

        // 매핑 테이블도 연결
        ChattingRoomUser chattingRoomUser = ChattingRoomUser.builder()
                .userId(user.getUserId())
                .chattingRoomId(uuid.toString())
                .build();
        chattingRoomUserRepository.save(chattingRoomUser);



        Community community = Community.builder()
                .userId(user)
                .title(title)
                .content(content)
                .si(si)
                .gun(gun)
                .dong(dong)
                .currentPerson(1)       // 본인을 포합해야하니 일단 1을 초기값으로 넣음
                .maxPerson(maxPerson)
                .date(nowTime)          // 마감일로 바꿔야되는 부분?? 일단은 모임 모이는 날이라는 의미로 사용
                .inProgress(inProgress)
                .chattingRoomId(uuid.toString())    // 채팅방 번호도 추가해주자
                .build();



        // 유저와 커뮤니티 매핑, 일단 생성한 사람만 넣는다.
        try {
            communityRepository.save(community);

            CommunityParticipate communityParticipate = CommunityParticipate.builder()
                    .communityId(community.getCommunityId())
                    .userId(user.getUserId())
                    .build();

            communityParticipateRepository.save(communityParticipate);

            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 방 목록 조회
    @GetMapping("/")
    public ResponseEntity CommunityList(@PageableDefault(size=10) Pageable pageable) {
        try {
            Page<CommunityListMapping> result = communityRepository.findAllBy(pageable);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 특정 유저가 방에 존재하는지를 확인하기 위한 API 존재시 true, 존재안할 경우 false반환
    @GetMapping("/check/{community_id}")
    public boolean userCommunityCheck(@PathVariable String community_id, @RequestHeader Map<String, Object> requestHeader) {
        log.debug("-----------이상찬--------------");
//        log.debug(headers);
//        log.debug(headers.get("token"));
//        log.debug(headers.get("token").getClass().getName());
        log.debug(requestHeader.get("token"));
        log.debug(community_id);

        String id = securityService.getSubject((String) requestHeader.get("token"));
        Long communityId = Long.parseLong(community_id);
        User user = userRepository.findById(id);
//        log.debug(user.getUserId());
//        log.debug(communityId);
//        System.out.println(user.getUserId());
//        System.out.println(communityId);

//        CommunityParticipate communityParticipate = communityParticipateRepository.findByCommunityIdAndUserId(communityId, user.getUserId());

        if (communityParticipateRepository.existsByCommunityIdAndUserId(communityId, user.getUserId())){
            return true;
        }else{
            return false;
        }
//        return true;

    }
//



    // 방 상세 정보 불러오기, 방 수정시 또는 채팅방 번호 불러올 때 사용
    @GetMapping("/info")
    public ResponseEntity<HashMap<String, Object>> communityInfo(String community_id) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        try {
            Community community = communityRepository.findById(Long.parseLong(community_id));
            map.put("title", community.getTitle());
            map.put("content", community.getContent());
            map.put("si", community.getSi());
            map.put("gun", community.getGun());
            map.put("dong", community.getDong());
            map.put("max_person", community.getMaxPerson());
            map.put("current_person", community.getCurrentPerson());
            map.put("date", community.getDate());
            map.put("in_progress", community.isInProgress());
            map.put("chatting_room_id", community.getChattingRoomId());

            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "방을 찾지 못함" // 방을 못찾을경우 404에러
            );
        }


    }


    // 방 수정
    @PutMapping("/")
    public ResponseEntity editCommunity(@RequestBody HashMap<String, Object> param){


        String token = (String) param.get("token");
        String userLoginId = securityService.getSubject(token);
        Long communityId = Long.parseLong((String) param.get("community_id"));
        System.out.println(userLoginId);
        User user = userRepository.findById(userLoginId);

//        Optional<Community> community = communityRepository.findById(communityId);
//        System.out.println(community);
        Community community = communityRepository.findById(communityId).orElseThrow(IllegalArgumentException::new);

        System.out.println(community.getUserId().getId().equals(userLoginId));
        if(community.getUserId().getId().equals(userLoginId)) {
            System.out.println("유저확인 완료");
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "작성 유저가 아닙니다."
            );
        }
//         이 윗 부분은 작성자 확인을 위한 부분
//         이 아랫 부분은 실제로 값을 바꿀 부분

        String title = (String) param.get("title");
        String content = (String) param.get("content");
        String si = (String) param.get("si");
        String gun = (String) param.get("gun");
        String dong = (String) param.get("dong");
        int maxPerson = Integer.parseInt(String.valueOf(param.get("max_person")));
        String startTime = (String) param.get("start_time");    // ex) "start_time" : "2023-12-12 05:30:10"
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime newTime = LocalDateTime.parse(startTime, dateTimeFormatter);
        Boolean inProgress = (Boolean) param.get("in_progress"); // boolean 형태로 넣어주세요

        community.setTitle(title);
        community.setContent(content);
        community.setSi(si);
        community.setGun(gun);
        community.setDong(dong);
        community.setMaxPerson(maxPerson);
        community.setDate(newTime);
        community.setInProgress(inProgress);

        communityRepository.save(community);

        return new ResponseEntity(HttpStatus.OK);
    }

    // 방 삭제
    @DeleteMapping("/")
    public ResponseEntity deleteCommunity(@RequestBody HashMap<String, Object> param){
        String token = (String) param.get("token");
        String userLoginId = securityService.getSubject(token);
        Long communityId = Long.parseLong((String) param.get("community_id"));
        System.out.println(userLoginId);
        User user = userRepository.findById(userLoginId);

//        Optional<Community> community = communityRepository.findById(communityId);
//        System.out.println(community);
        Community community = communityRepository.findById(communityId).orElseThrow(IllegalArgumentException::new);

        System.out.println(community.getUserId().getId().equals(userLoginId));
        if(community.getUserId().getId().equals(userLoginId)) {
            System.out.println("유저확인 완료");
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "작성 유저가 아닙니다."
            );
        }

        try {
            List<CommunityParticipate> deleteList =  communityParticipateRepository.findByCommunityId(communityId);
            for (int i = 0 ; i < deleteList.size(); i ++){
                communityParticipateRepository.delete(deleteList.get(i));
            }

            List<CommunityBanUser> communityBanUserList = communityBanUserRepository.findByCommunityId(communityId);
            for (int i = 0 ; i < communityBanUserList.size(); i ++){
                communityBanUserRepository.delete(communityBanUserList.get(i));
            }


            communityRepository.delete(community);
            // 매핑 테이블에서 제거하기


            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "삭제실패"
            );
        }


    }

    // 현재 참여 인원을 늘리기위한 API, 토큰을 받아서 그 사람을 가입시킨다.
    @PutMapping("/addPerson")
    public ResponseEntity personAddCommunity(@RequestBody HashMap<String, Object> param) {
        String token = (String) param.get("token");
        String userLoginId = securityService.getSubject(token);
        User user = userRepository.findById(userLoginId);

        Long communityId = Long.parseLong((String) param.get("community_id"));

        Community community = communityRepository.findById(communityId).orElse(null);
        if (community != null){

            CommunityBanUser communityBanUser = communityBanUserRepository.findByCommunityIdAndUserId(communityId, user.getUserId());
            if (communityBanUser != null){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "해당 모임에서 추방되었습니다."
                );
            }

            if (communityParticipateRepository.existsByCommunityIdAndUserId(communityId, user.getUserId())){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "이미 방에 존재하는 유저입니다."
                );
            }

            if (community.getCurrentPerson() == community.getMaxPerson()){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "이미 가득 찬 모임입니다."
                );
            }


            community.setCurrentPerson(community.getCurrentPerson() + 1);   // 한사람 추가됨
            if (community.getCurrentPerson() == community.getMaxPerson()){
                community.setInProgress(false);     // 사람 가득참을 알림
            }

            CommunityParticipate communityParticipate = CommunityParticipate.builder()
                    .communityId(community.getCommunityId())
                    .userId(user.getUserId())
                    .build();

            communityParticipateRepository.save(communityParticipate);

            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "해당 커뮤니티는 없음"
            );
        }

    }

    // 참여중인 사람을 빼기 위한 API, 토큰으로 자기가 스스로 빠질때 사용
    @DeleteMapping("/removePerson")
    public ResponseEntity personRemoveCommunity(@RequestBody HashMap<String, Object> param) {
        String token = (String) param.get("token");
        String userLoginId = securityService.getSubject(token);
        User user = userRepository.findById(userLoginId);

        Long communityId = Long.parseLong((String) param.get("community_id"));

        Community community = communityRepository.findById(communityId).orElse(null);
        if (community != null){

            CommunityParticipate communityParticipate = communityParticipateRepository.findByCommunityIdAndUserId(communityId, user.getUserId());

            if(communityParticipate == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "해당 커뮤니티에 참여중이 아닙니다."
                );
            }

            if (community.getCurrentPerson() == community.getMaxPerson()){
                community.setInProgress(true);     // 한 사람이 빠져서 자리가 빔을 알림
            }


            community.setCurrentPerson(community.getCurrentPerson() - 1);   // 한사람 제거됨

            communityParticipateRepository.delete(communityParticipate);

            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "해당 커뮤니티 존재하지 않음"
            );
        }

    }

    // 방장이 다른 사람을 초대하기 위한 API
    @PutMapping("/invitePerson")
    public ResponseEntity invitePersonToCommunity(@RequestBody HashMap<String, Object> param) {
        String token = (String) param.get("token");
        String userLoginId = securityService.getSubject(token);
        User user = userRepository.findById(userLoginId);


        User invitedUser = userRepository.findByNickname((String) param.get("invited_user_nickname"));

        Long communityId = Long.parseLong((String) param.get("community_id"));


        Community community = communityRepository.findById(communityId).orElse(null);
        if (community != null){                                                     // 커뮤니티 존재 확인

            if (user.getUserId().equals(community.getUserId().getUserId())) {       // 커뮤니티 방장인지 확인

                if(invitedUser != null){                                            // 초대유저 존재확인

//                    CommunityParticipate communityParticipate = communityParticipateRepository.findByCommunityIdAndUserId(communityId, invitedUser.getUserId());

                    if(communityParticipateRepository.existsByCommunityIdAndUserId(communityId, invitedUser.getUserId())) {
                        throw new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "이미 방에 존재하는 유저"
                        );
                    } else {



                        if (community.getCurrentPerson() == community.getMaxPerson()){
                            System.out.println();
                            throw new ResponseStatusException(
                                    HttpStatus.BAD_REQUEST, "이미 가득 찬 모임"
                            );
                        }

                        community.setCurrentPerson(community.getCurrentPerson() + 1);   // 한사람 추가됨
                        if (community.getCurrentPerson() == community.getMaxPerson()){
                            community.setInProgress(false);     // 사람 가득참을 알림
                        }

                        System.out.println("-------------");
                        System.out.println("여기오나?");
                        System.out.println("-------------");
                        System.out.println(community.getCommunityId());
                        System.out.println(user.getUserId());
                        System.out.println("-------------");


                        CommunityParticipate communityParticipate = CommunityParticipate.builder()
                                .communityId(community.getCommunityId())
                                .userId(invitedUser.getUserId())
                                .build();

                        communityParticipateRepository.save(communityParticipate);



//                        CommunityParticipate communityParticipate = CommunityParticipate.builder()
//                                .communityId(community.getCommunityId())
//                                .userId(user.getUserId())
//                                .build();
//
//                        communityParticipateRepository.save(communityParticipate);

                        return new ResponseEntity(HttpStatus.OK);
                    }


                } else {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "없는 유저"
                    );
                }

            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "방장이 아님"
                );
            }

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "해당 커뮤니티는 없음"
            );
        }
    }


    @PostMapping("/banPerson")      // 유저를 모임에서 밴해서 쫓아냄
    public ResponseEntity banPerson(@RequestBody HashMap<String, Object> param) {
        String token = (String) param.get("token");
        String userLoginId = securityService.getSubject(token);
        User user = userRepository.findById(userLoginId);


        User banUser = userRepository.findByNickname((String) param.get("ban_user_nickname"));

        Long communityId = Long.parseLong((String) param.get("community_id"));


        Community community = communityRepository.findById(communityId).orElse(null);
        if (community != null){                                                     // 커뮤니티 존재 확인

            if (user.getUserId().equals(community.getUserId().getUserId())) {       // 커뮤니티 방장인지 확인

                if(banUser != null){                                            // 밴유저 존재확인

                    CommunityParticipate communityParticipate = communityParticipateRepository.findByCommunityIdAndUserId(communityId, banUser.getUserId());

                    if(communityParticipate != null) {


                        community.setCurrentPerson(community.getCurrentPerson() - 1);   // 한사람 추가됨
                        if (community.getCurrentPerson() + 1 == community.getMaxPerson()){
                            community.setInProgress(true);     // 한 사람이 비어서 자리가 생김을 알림
                        }

                        communityParticipateRepository.delete(communityParticipate);    // 매핑된 유저-커뮤니티 삭제

                        CommunityBanUser communityBanUser = CommunityBanUser.builder()
                                .communityId(community.getCommunityId())
                                .userId(banUser.getUserId())
                                .build();

                        communityBanUserRepository.save(communityBanUser);


                        return new ResponseEntity(HttpStatus.OK);


                    } else {
                            throw new ResponseStatusException(
                                    HttpStatus.BAD_REQUEST, "방에 존재하지 않는 유저");
                    }


                } else {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "없는 유저"
                    );
                }

            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "방장이 아님"
                );
            }

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "해당 커뮤니티는 없음"
            );
        }
    }


    @DeleteMapping("/cancelBanPerson")      // 밴 한 사람을 풀어주기
    public ResponseEntity banCancelPerson(@RequestBody HashMap<String, Object> param) {
        String token = (String) param.get("token");
        String userLoginId = securityService.getSubject(token);
        User user = userRepository.findById(userLoginId);

        User banUser = userRepository.findByNickname((String) param.get("ban_user_nickname"));

        Long communityId = Long.parseLong((String) param.get("community_id"));
        Community community = communityRepository.findById(communityId).orElse(null);
        if (community != null) {                                                     // 커뮤니티 존재 확인

            if (user.getUserId().equals(community.getUserId().getUserId())) {       // 커뮤니티 방장인지 확인

                CommunityBanUser communityBanUser = communityBanUserRepository.findByCommunityIdAndUserId(community.getCommunityId(), banUser.getUserId());

                if (communityBanUser != null) {  // 밴 된 사람이 맞으면 밴 해제
                    communityBanUserRepository.delete(communityBanUser);
                    return new ResponseEntity(HttpStatus.OK);
                }else {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "밴 되었던 사람이 아닙니다."
                    );
                }

            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "방장이 아님"
                );
            }

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "해당 커뮤니티는 없음"
            );
        }

    }

    @GetMapping("/showBanList")     // 밴 목록을 보여줌
    public List<String> showBanList(@RequestHeader Map<String, Object> requestHeader) {
        String id = securityService.getSubject((String) requestHeader.get("token"));
        Long communityId = Long.parseLong((String) requestHeader.get("community_id"));


        List<CommunityBanUser> banUsers = communityBanUserRepository.findByCommunityId(communityId);

        List<String> returnList = new ArrayList<>();

        for (int i = 0; i < banUsers.size(); i++) {
            User user = userRepository.findById(banUsers.get(i).getUserId()).orElse(null);
            if (user != null) {
                returnList.add(user.getNickname());
            } else {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "없는 유저"
                );
            }

        }
        return returnList;
    }

    // 모임방 리스트 검색 기능
    @GetMapping("/searchRoom")
    public ResponseEntity searchRooms(@PageableDefault(size=10) Pageable pageable, String si, String gun, String dong, String search_word){
        try {
            if(si.equals("")){              // 시 데이터 빈값일 경우
                if (search_word.equals("")){    // 검색어 입력도 안되어 있으면 전체목록 반환
                    Page<CommunityListMapping> result = communityRepository.findAllBy(pageable);
                    return new ResponseEntity<>(result, HttpStatus.OK);

                } else {    // 검색어만으로 검색
                    Page<CommunityListMapping> result = communityRepository.findByTitleContaining(pageable, search_word);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }

            } else {                        // 시 데이터가 존재할 경우

                if(gun.equals("")){         // 군 데이터가 빈값일 경우, 시로 검색, 검색어가 있으면 검색어로도 검색

                    if (search_word.equals("")){    // 시
                        Page<CommunityListMapping> result = communityRepository.findBySi(pageable, si);
                        return new ResponseEntity<>(result, HttpStatus.OK);
                    } else {                        // 시 + 검색어
                        Page<CommunityListMapping> result = communityRepository.findBySiAndTitleContaining(pageable, si, search_word);
                        return new ResponseEntity<>(result, HttpStatus.OK);
                    }

                }
                else {                      // 군 데이터가 존재할 경우

                    if(dong.equals("")){    // 동 데이터가 빈 값일 경우
                        if (search_word.equals("")){    // 시 + 군
                            Page<CommunityListMapping> result = communityRepository.findBySiAndGun(pageable, si, gun);
                            return new ResponseEntity<>(result, HttpStatus.OK);
                        } else {                        // 시 + 군 + 검색어
                            Page<CommunityListMapping> result = communityRepository.findBySiAndGunAndTitleContaining(pageable, si, gun, search_word);
                            return new ResponseEntity<>(result, HttpStatus.OK);
                        }
                    }
                    else {                  // 동 데이터가 존재할 경우
                        if (search_word.equals("")){    // 시 + 군 + 동
                            Page<CommunityListMapping> result = communityRepository.findBySiAndGunAndDong(pageable, si, gun, dong);
                            return new ResponseEntity<>(result, HttpStatus.OK);
                        } else {                        // 시 + 군 + 동 + 검색어
                            Page<CommunityListMapping> result = communityRepository.findBySiAndGunAndDongAndTitleContaining(pageable, si, gun, dong, search_word);
                            return new ResponseEntity<>(result, HttpStatus.OK);
                        }
                    }
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    // 모임 방의 사람들 모두 조회
    @GetMapping("/usersInfo")
    public List<HashMap<String, Object>> userInfo (String communityId) {

        Long communityLongId = Long.parseLong(communityId);
        List<CommunityParticipate> communityParticipateList = communityParticipateRepository.findByCommunityId(communityLongId); // 채팅방에 속한 사람들을 찾음

        List<HashMap<String, Object>> returnList = new ArrayList<>();

        for (int i = 0; i < communityParticipateList.size(); i++){
            User user = userRepository.findById(communityParticipateList.get(i).getUserId()).orElse(null);
            if (user != null) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("nickname", user.getNickname());
                map.put("image_path", user.getImagePath());
                returnList.add(map);
            }
        }
        return returnList;

    }


}