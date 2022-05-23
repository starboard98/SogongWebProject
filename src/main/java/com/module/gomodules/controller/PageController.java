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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
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
    public String adminPage(HttpSession session, HttpServletResponse response) throws IOException {
        if (!session.getAttribute("id").toString().equals("admin")) {//세션아이디가 존재하지않는다면 index페이지로 보냄
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('관리자가 아닙니다.'); location.href= '/index'; </script>");
            out.flush();
            return "redirect:/index";
        }
        return "/admin";
    }
    @GetMapping("/showUserList.do")
    public String showUserListDo(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        if (!session.getAttribute("id").toString().equals("admin")) {//세션아이디가 존재하지않는다면 index페이지로 보냄
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('관리자가 아닙니다.'); location.href= '/index'; </script>");
            out.flush();
            return "redirect:/index";
        }
        List<CustomerVO> customerVOList = userRepository.findAll();
        model.addAttribute("userList", customerVOList);
        return "/showUserList";
    }

    @GetMapping("/showReservationAll.do")
    public String showReservationAll(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        if (!session.getAttribute("id").toString().equals("admin")) {//세션아이디가 존재하지않는다면 index페이지로 보냄
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('관리자가 아닙니다.'); location.href= '/index'; </script>");
            out.flush();
            return "redirect:/index";
        }
        List<ReservationVO> reservationVOList = reservationRepository.findAll();
        model.addAttribute("reservationList", reservationVOList);
        return "/showUserReservation";
    }

    @GetMapping("/showReservation.do")
    public String showReservation(Model model, HttpServletResponse response, HttpSession session) throws IOException {
        if (!session.getAttribute("id").toString().equals("admin")) {//세션아이디가 존재하지않는다면 index페이지로 보냄
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('관리자가 아닙니다.'); location.href= '/index'; </script>");
            out.flush();
            return "redirect:/index";
        }
        List<ReservationVO> reservationVOList = reservationRepository.findReservationOfDate();
        model.addAttribute("reservationList", reservationVOList);
        return "/showUserReservation";
    }
}
