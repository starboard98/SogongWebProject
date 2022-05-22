package com.module.gomodules.controller;

import com.module.gomodules.VO.CustomerVO;
import com.module.gomodules.VO.ReservationVO;
import com.module.gomodules.repository.ReservationRepository;
import com.module.gomodules.repository.UserRepository;
import com.module.gomodules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("/admin")
    public String adminPage(){
        return "/admin";
    }
    @GetMapping("/showUserList.do")
    public String showUserListDo(Model model){
        List<CustomerVO> customerVOList = userRepository.findAll();
        model.addAttribute("userList", customerVOList);
        return "/showUserList";
    }

    @GetMapping("/showReservationAll.do")
    public String showReservationAll(Model model){
        List<ReservationVO> reservationVOList = reservationRepository.findAll();
        model.addAttribute("reservationList", reservationVOList);
        return "/showUserReservation";
    }

    @GetMapping("/showReservation.do")
    public String showReservation(Model model){
        List<ReservationVO> reservationVOList = reservationRepository.findReservationOfDate();
        model.addAttribute("reservationList", reservationVOList);
        return "/showUserReservation";
    }
}
