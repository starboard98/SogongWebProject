package com.module.gomodules.service;

import com.module.gomodules.VO.CustomerVO;
import com.module.gomodules.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public void joinUser(CustomerVO vo){
        System.out.println("회원가입 요청 들어옴");
        userRepository.save(vo);
        System.out.println("회원가입 요청 성공");
    }

    public boolean loginCheck(String id, String pw) {
        /*test*/
        System.out.println("   loginCheck 동작 ");
        System.out.println("id: "+ id+"pw: "+pw);

        CustomerVO vo = userRepository.findById(id);

        if (passwordEncoder.matches(pw, vo.getVal_password())) {
            return true;
        }

        else
            return false;
    }
    public CustomerVO isExist(int uid){return userRepository.findByUid(uid);}
    public int increaseNoShowCount(int uid){return userRepository.increaseNoShowCount(uid);}
}





