package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.example.demo.domain.User;
import com.example.demo.mapping.UserMapping;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByNameAndEmail(String name, String email);

    User findByNameAndEmailAndId(String name, String email, String id);

    User findByNickname(String nickname);           // 별명으로 찾기
    List<User> findByNicknameContaining(String nickname); // 별명 포함된 사람들 모두 찾기
    User findByIdAndPassword(String id, String password);   // 아이디와 비밀번호 둘다 일치하는 사람 찾기 용
    User findById(String id); // 로그인 할때 쓰는 ID로 사람찾기 용


    boolean existsById(String id);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String Email);

    User findByUserId(Long userId);

    //User findByNickname(String nickname);

}