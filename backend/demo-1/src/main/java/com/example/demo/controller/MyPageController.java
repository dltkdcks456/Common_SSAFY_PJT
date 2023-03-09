package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.repository.*;
import com.example.demo.service.SecurityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping(value = "/api/mypage")
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분


//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class MyPageController {
    @Autowired
    private BoardImageRepository boardImageRepository;

    @Autowired
    UserRepository userRepository;  // 유저 db

    @Autowired
    CommunityRepository communityRepository; // 커뮤니티 db

    @Autowired
    CommunityParticipateRepository communityParticipateRepository; // 커뮤니티-유저 db

    @Autowired
    BoardRepository boardRepository; // 게시판 db

    @Autowired
    DealRepository dealRepository; // 중고거래 db

    @Autowired
    SalesRepository salesRepository; // 중고거래 유저기록 db

    @Autowired
    UserAchievementRepository userAchievementRepository; // 유저 업적 달성 db

    @Autowired
    ZzimRepository zzimRepository; // 찜 목록 db

    @Autowired
    BoardCodeRepository boardCodeRepository;

    @Autowired
    SecurityService securityService; // jwt 이용

    @GetMapping("/communityList")   // 유저가 포함된 모임방 정보 리스트 반환
    public List<HashMap<String, Object>> getCommunityList(@RequestHeader Map<String, Object> requestHeader) {
        String id = securityService.getSubject((String) requestHeader.get("token"));
        User user = userRepository.findById(id);

        List<CommunityParticipate> communityParticipateList = communityParticipateRepository.findByUserId(user.getUserId());
        List<HashMap<String, Object>> returnList = new ArrayList<>();   // 반환할 리스트

        for (int i = 0; i < communityParticipateList.size(); i++) {
            Community community = communityRepository.findById(communityParticipateList.get(i).getCommunityId()).orElse(null);
            if(community != null){
                HashMap<String,Object> map = new HashMap<String,Object>();          // 필요한 정보들을 골라서 넣을 리스트
                map.put("community_id", community.getCommunityId());
                map.put("community_title", community.getTitle());
                map.put("community_content", community.getContent());
                map.put("community_si", community.getSi());
                map.put("community_gun", community.getGun());
                map.put("community_dong", community.getDong());
                map.put("community_date", community.getDate());
                map.put("community_max_person", community.getMaxPerson());
                map.put("community_current_person", community.getCurrentPerson());
                map.put("community_room_leader", community.getUserId());            // 모임방 방장
                returnList.add(map);
            }
        }

        return returnList;

    }


    @GetMapping("/boardList")   // 유저가 작성한 게시판 정보 목록 반환
    public List<HashMap<String, Object>> getBoardList(String nickname){//@RequestHeader Map<String, Object> requestHeader) {
//        String id = securityService.getSubject((String) requestHeader.get("token"));

        User user = userRepository.findByNickname(nickname);

        List<Board> boardList = boardRepository.findByUserId(user);

        List<HashMap<String, Object>> returnList = new ArrayList<>();   // 반환할 리스트

        for(int i = 0; i < boardList.size(); i++){
            Board board = boardList.get(i);
            BoardCode boardCode = boardCodeRepository.findByCode(board.getBoardCode());
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("board_code_name", boardCode.getName());
            map.put("board_code", boardCode.getCode());
            map.put("board_id", board.getBoardId());
            map.put("board_title", board.getTitle());
            map.put("board_content", board.getContent());
            map.put("board_visited", board.getVisited());
            map.put("board_like_count", board.getLikeCount());
            map.put("board_hate_count", board.getHateCount());
            map.put("board_reg_time", board.getRegTime());

            returnList.add(map);
        }

        return returnList;
    }

    @GetMapping("/achievementList")         // 유저가 달성한 업적들 정보 목록 반환
    public List<HashMap<String, Object>> getAchievementList(String nickname){//@RequestHeader Map<String, Object> requestHeader) {
//        String id = securityService.getSubject((String) requestHeader.get("token"));
        User user = userRepository.findByNickname(nickname);

        List<UserAchievement> userAchievementList = userAchievementRepository.findByUserId(user);
        List<HashMap<String, Object>> returnList = new ArrayList<>();

        for (int i = 0; i < userAchievementList.size(); i++){
            HashMap<String,Object> map = new HashMap<String,Object>();
            Achievement achievement = userAchievementList.get(i).getAchievementId();

            map.put("achievement_name", achievement.getName());
            map.put("achievement_terms", achievement.getTerms());
            map.put("achievement", achievement.getAchievement());

            returnList.add(map);
        }

        return returnList;

    }

    @GetMapping("/saleList")     // 유저가 판매한 기록 리스트
    public List<HashMap<String, Object>> getSalesList(String nickname){             //@RequestHeader Map<String, Object> requestHeader) {

        User user = userRepository.findByNickname(nickname);

        List<Deal> dealList = dealRepository.findByUserId(user);
        List<HashMap<String, Object>> returnList = new ArrayList<>();
//        List<SalesRecord> salesRecordList = salesRepository.findByUserId(user);

        for (int i = 0; i < dealList.size(); i++){
            Deal deal = dealList.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();

            map.put("deal_id", deal.getDealId());
            map.put("deal_title", deal.getTitle());
            map.put("deal_time", deal.getTime());
            map.put("deal_kind", deal.getKind());
            map.put("deal_name", deal.getName());
            map.put("deal_price", deal.getPrice());
            map.put("deal_on_sale", deal.isOnSale());

            if (!deal.isOnSale()){              // 판매중이 아닐경우
                SalesRecord salesRecord = salesRepository.findByDealId(deal);
                if (salesRecord != null){       // 판매완료 목록에 있다면
                    map.put("sales_record_clear_deal", salesRecord.isClearlyDeal());
                }
            }

            returnList.add(map);
        }

        return returnList;
    }


    @GetMapping("/zzimList")        // 유저 자신이 중고거래 찜한 목록을 반환
    public List<HashMap<String, Object>> getZzimList(@RequestHeader Map<String, Object> requestHeader) {
        String id = securityService.getSubject((String) requestHeader.get("token"));
        User user = userRepository.findById(id);


        List<ZzimList> zzimLists = zzimRepository.findByDealPKUserId(user.getUserId());
        List<HashMap<String, Object>> returnList = new ArrayList<>();

        for (int i = 0; i < zzimLists.size(); i++){
            Deal deal = dealRepository.findById(zzimLists.get(i).getDealPK().getDealId()).orElse(null);
            HashMap<String,Object> map = new HashMap<String,Object>();

            if(deal == null){
                System.out.println("없는 거래");
            } else{
                map.put("zzim_deal_id", deal.getDealId());
                map.put("zzim_deal_title", deal.getTitle());
                map.put("zzim_deal_time", deal.getTime());
                map.put("zzim_deal_kind", deal.getKind());
                map.put("zzim_deal_name", deal.getName());
                map.put("zzim_deal_price", deal.getPrice());
                map.put("zzim_deal_on_sale", deal.isOnSale());

                returnList.add(map);
            }


        }

        return returnList;

    }

}

