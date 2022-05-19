package com.module.gomodules.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.module.gomodules.EventVO;
import com.module.gomodules.VO.ReservationVO;
//import com.module.gomodules.VO.modefiedEvent;
import com.module.gomodules.VO.modifyingDateAndTime;
import com.module.gomodules.VO.modifyingReservation;
//import com.module.gomodules.service.EventService;
import com.module.gomodules.repository.ReservationRepository;
import com.module.gomodules.service.ReservationService;
//import com.module.gomodules.service.TableService;
import com.module.gomodules.repository.UserRepository;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class ReservationController {
    @Autowired
    ReservationService ReservationService;
    @Autowired
    ReservationRepository ReservationRepository;
    @Autowired
    UserRepository userRepository;
/* 예약 삭제 기능, 하나는 관리자페이지 하나는 유저가 삭제
    @ResponseBody
    @RequestMapping(value = "/cancleReservation.do")
    public String cancleReservation(HttpServletRequest request, Model model) {
        int cancleoid = Integer.parseInt(request.getParameter("cancleoid"));
        ReservationVO vo = ReservationService.findByOid(cancleoid); //취소할 예약의 vo
        ReservationService.removeReservationForUser(vo);
        return "<script> alert('삭제 완료');  location.href= '/listReservation'; </script>";
    }
*/

    @RequestMapping(value = "/callDeleteReserve/{oid}", produces = "text/html; charset=UTF-8")
    public String callDeleteReserve(@PathVariable int oid, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (session.getAttribute("loginCheck") == null) {//세션아이디가 존재하지않는다면 index페이지로 보냄
            out.println("<script>alert('로그인 후 이용하세요.');</script>");
            out.flush();
            return "redirect:/index";
        }
        //ReservationVO vo = ReservationService.findByOid(oid);
        ReservationService.deleteReservationbyoid(oid);
        out.println("<script>alert('삭제 완료되었습니다.');  location.href= '/listReservation'; </script>");
        out.flush();
        return "redirect:/listReservation";
    }

    @RequestMapping(value = "/modifyReservation/{oid}", produces = "text/html; charset=UTF-8")
    public String showTableView(@PathVariable int oid, HttpServletRequest request, Model model, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (session.getAttribute("loginCheck") == null) {//세션아이디가 존재하지않는다면 index페이지로 보냄
            out.println("<script>alert('로그인 후 이용하세요.');</script>");
            out.flush();
            return "redirect:/index";
        }
        ReservationVO vo = ReservationService.findByOid(oid);
        // covers / date / start_time / table_number
        String[] r = new String[4];
        r[0] = String.valueOf(vo.getVal_covers());
        r[1] = vo.getVal_date();
        r[2] = vo.getVal_start_time();
        r[3] = String.valueOf(vo.getVal_table_number());

        model.addAttribute("userid", session.getAttribute("id"));
        return "redirect:/listReservation";
    }
}