package com.module.gomodules.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.module.gomodules.EventVO;
import com.module.gomodules.VO.ReservationVO;
//import com.module.gomodules.VO.modefiedEvent;
//import com.module.gomodules.service.EventService;
import com.module.gomodules.repository.ReservationRepository;
import com.module.gomodules.service.ReservationService;
//import com.module.gomodules.service.TableService;
import com.module.gomodules.repository.UserRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class ReservationController {
    @Autowired
    ReservationService ReservationService;
    @Autowired
    ReservationRepository ReservationRepository;
    @Autowired
    UserRepository userRepository;

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
        model.addAttribute("old_reservation", r);
        model.addAttribute("reservation_id", oid);

        //예약 정보 불러오기
        List<ReservationVO> list = ReservationService.getAllReservation();
        model.addAttribute("length", list.size());

        //예약 정보중 date, time, number쌍을 view로 넘김
        String[][] rlist = new String[list.size()][3];
        for(int i=0; i<list.size(); i++){
            //date start_time table
            rlist[i][0] = list.get(i).getVal_date();
            rlist[i][1] = list.get(i).getVal_start_time();
            rlist[i][2] = list.get(i).getVal_table_number()+"";
        }
        model.addAttribute("list", rlist);

        return "/modifyReservation";
    }


    @RequestMapping(value = "/modifyre/{oid}", produces = "text/html; charset=UTF-8")
    public String modifyre(@PathVariable int oid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);// 현재 세션 로드
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (session.getAttribute("loginCheck") == null) {//세션아이디가 존재하지않는다면 index페이지로 보냄
            out.println("<script>alert('로그인 후 이용하세요.'); location.href= '/index'; </script>");
            out.flush();
            return "redirect:/index";
        }

        //vo.setVal_uid((int) session.getAttribute("oid"));// 세션의 oid(유저기본키)값을 uid(예약.고객아이디)로
        //vo.setVal_name((String) session.getAttribute("name"));// 이름가져오기

        //form태그 값 받아오기
        int covers = Integer.parseInt(request.getParameter("num_people"));//인원수
        String date = request.getParameter("date");// 날짜 가져오기
        String time = request.getParameter("start_time");// 시간 가져오기
        //vo.setVal_date(date);
        //vo.setVal_start_time(time);// 날짜+시간 가져오기
        int a = Integer.parseInt(request.getParameter("table_num"));
        //vo.setVal_table_number(a);

        //본인에 대한 중복인지 미리 검사
        if(ReservationService.isSelf(date,  time, a, oid) > 0){
            out.println("<script>alert('정상적으로 수정되었습니다.'); location.href= '/listReservation';  </script>");
            out.flush();
            return "redirect:/listReservation";
        }
        //중복 예약 검사
        else if(ReservationService.findByEquals(date, time, a) > 0 ){
            out.println("<script>alert('해당 테이블은 이미 예약이 있습니다.'); location.href= '/modifyReservation/"+oid+"';  </script>");
            out.flush();
            return "redirect:/modifyReservation";
        }
        else{
            //cover, date, time, table, oid
            ReservationService.updateReservation(covers, date, time, a, oid);
            out.println("<script>alert('정상적으로 수정되었습니다.'); location.href= '/listReservation';  </script>");
            out.flush();
            return "redirect:/listReservation";
        }
    }
}