package com.module.gomodules.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.module.gomodules.VO.CustomerVO;
import com.module.gomodules.VO.ReservationVO;
import com.module.gomodules.repository.UserRepository;
import com.module.gomodules.service.ReservationService;
//import com.module.gomodules.service.TableService;
import com.module.gomodules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ReservationService ReservationService;

    @ResponseBody // return to body
    @RequestMapping(value = "/joinUs.do", method = RequestMethod.POST)
    public String joinUs(HttpServletRequest req, CustomerVO vo) {
        vo.setVal_id(req.getParameter("id"));
        System.out.print(vo.getVal_id());
        if (userRepository.findById(vo.getVal_id()) != null) {
            System.out.println("중복아이디 감지");
            return "<script> alert('중복된 아이디 입니다.');  location.href= '/signin.html'; </script>";
        }
        String password = req.getParameter("password");
        String encoded_password = passwordEncoder.encode(password);
        // vo.setVal_password(password); //not_crypto
        vo.setVal_password(encoded_password);
        vo.setVal_name(req.getParameter("name"));
        vo.setVal_phonenumber(req.getParameter("phone"));
        System.out.print(vo.getVal_password());
        if (vo.getVal_id().equals("") || vo.getVal_name().equals("") || password.equals("")
                || vo.getVal_phonenumber().equals(""))
            return "<script> alert('정보를 모두 입력해주세요.');  location.href= '/signin.html'; </script>";

        userService.joinUser(vo);
        return "<script> alert('가입 되었습니다.'); location.href= '/index.html'; </script>";
    }

    @RequestMapping(value = "/join")
    public String join() {
        return "join";
    }

    @RequestMapping(value = "/failed")
    public String failed() {
        System.out.print("nooo..");
        return "failed";
    }

    //로그인 : 로그인시 세션부여
    @ResponseBody // return to body
    @PostMapping(value = "/signIn.do", produces = "text/html; charset=UTF-8")
    public String signIn(HttpSession session, HttpServletRequest req) {
        String id = req.getParameter("id");
        String pw = req.getParameter("password");
        if (id.equals("")) {
            return "<script> alert('아이디를 입력해주세요.');  location.href= '/index.html' ; </script>";
        }
        if (pw.equals("")) {
            return "<script> alert('비밀번호를 입력하세요');  location.href= '/index.html'; </script>";
        }
        if (userRepository.findById(id) == null) {
            return "<script> alert('없는 아이디 입니다.');  location.href= '/index.html'; </script>";
        }

        if (userService.loginCheck(id, pw)) {
            System.out.println("\n" + id + "님 login");
            // 유저의 oid도 세션에 함께 저장한다. (DB연동관련)
            // 세션은 oid, logincheck, id가 저장된다.
            CustomerVO vo = userRepository.findById(id);
            int oid = vo.getVal_oid();
            String name = vo.getVal_name();
            session.setAttribute("oid", oid);
            session.setAttribute("loginCheck", true);
            session.setAttribute("id", id);
            session.setAttribute("name", name);
            return "<script> alert('" + session.getAttribute("name") + "님 로그인 되셨습니다!'); location.href= '/home.html'; </script>";
        } else {
            System.out.println("False");
            return "<script> alert('아이디와 비밀번호가 일치하지 않습니다.');  location.href= '/index.html'; </script>";
        }

    }

    // logout
    @ResponseBody
    @RequestMapping(value = "/logOut.do")
    public String logOut(HttpSession session) {
        String name = (String) session.getAttribute("name");
        session.setAttribute("oid", null);
        session.setAttribute("loginCheck", null);
        session.setAttribute("id", null);
        session.setAttribute("name", null);
        return "<script> alert('" + name + "님 로그아웃 되었습니다.');location.href='/index.html'; </script>";
    }

    // page mapping
    // home.html 에는 리다이렉션이 잘이루어짐 접근제한기능추가.
    @ResponseBody
    @RequestMapping(value = "/home")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("loginCheck") == null)
            return "<script> alert('로그인 후 이용해주시길 바랍니다.');location.href='/index.html'; </script>";   //로그인이 x, 인덱스로 돌려보냄
        //model에 userid속성을 추가하고 값은 id로 설정, 일단 보류
        //model.addAttribute("userid", session.getAttribute("id"));
        return "/home";
    }

    /* 관리자 일단 보류
    @RequestMapping(value = "/adminhome")
    public String adminhome(HttpSession session, Model model) {
        if (session.getAttribute("loginCheck") == null || (int) session.getAttribute("level") == 0)
            return "index";

        model.addAttribute("userid", session.getAttribute("id"));
        return "adminhome";
    }*/

    @RequestMapping(value = "/index")
    public String index(HttpSession session) {
        session.setAttribute("oid", null);
        session.setAttribute("loginCheck", null);
        session.setAttribute("id", null);
        session.setAttribute("name", null);
        return "/index";
    }

    @RequestMapping(value = "/noEventReservation")
    public String noEventReservation() {
        return "/noEventReservation";
    }

    @RequestMapping(value = "/listReservation")
    public String listReservation(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(true);// 현재 세션 로드

        if (session.getAttribute("loginCheck") == null) //세션아이디가 존재하지않는다면 index페이지로 보냄
            return "/index";

        List<ReservationVO> list = ReservationService.getReservationList((Integer) session.getAttribute("oid"));
        model.addAttribute("name", session.getAttribute("name"));
        model.addAttribute("length", list.size());
        String[][] r = new String[list.size()][5];
        for(int i=0; i<list.size(); i++){
            //name covers date start_time table_number
            r[i][0] = list.get(i).getVal_name();
            r[i][1] = list.get(i).getVal_covers()+"";
            r[i][2] = list.get(i).getVal_date();
            r[i][3] = list.get(i).getVal_start_time();
            r[i][4] = list.get(i).getVal_table_number()+"";
        }
        model.addAttribute("list", r);
        return "/listReservation";
    }
/*
    @RequestMapping(value = "/showUserReservation")
    public String showUserReservation(HttpServletRequest request, Model model) { // 예약리스트 조회관련 코드 추가함 ㅁㅁ
        HttpSession session = request.getSession(true);// 현재 세션 로드
        int currentOid = (int) session.getAttribute("oid");
        String currentid = (String) session.getAttribute("id");
        List<ReservationVO> list = ReservationService.getReservationListForUser(currentOid);
        ArrayList<modefiedReservation> list2 = new ArrayList<modefiedReservation>();
        for (ReservationVO vo : list) {
            int oid = vo.getVal_oid();
            int people_number = vo.getVal_people_number();
            int rank = vo.getVal_rank();
            int tid = vo.getVal_tid();
            String start_time = vo.getVal_start_time();

            /// 날짜 형식 변환 코드부분 //////////////////////
            SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd HH시");

            Date date = null;
                        try {
                date = in.parse(start_time);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String result = out.format(date);
            /// 날짜 형식 변환 끝 //////////////////////////

            modefiedReservation mReserv = new modefiedReservation();
            mReserv.setVal_oid(oid);
            mReserv.setVal_people_number(people_number);
            mReserv.setVal_start_time(start_time);
            mReserv.setVal_start_time(result);
            list2.add(mReserv);
        }
        model.addAttribute("list", list2);
        model.addAttribute("userid", currentid);
        return "showUserReservation";
    }
*/

    @ResponseBody
    @RequestMapping(value = "/addReservation")
    public String addReservation(HttpServletRequest request, ReservationVO vo, Model model) {
        HttpSession session = request.getSession(true);// 현재 세션 로드
        if (session.getAttribute("loginCheck") == null) //세션아이디가 존재하지않는다면 index페이지로 보냄
            return "/index";

        vo.setVal_uid((int) session.getAttribute("oid"));// 세션의 oid(유저기본키)값을 uid(예약.고객아이디)로
        vo.setVal_name((String) session.getAttribute("name"));// 이름가져오기

        //form태그 값 받아오기
        vo.setVal_covers(Integer.parseInt(request.getParameter("num_people")));//인원수
        String date = request.getParameter("date");// 날짜 가져오기
        String time = request.getParameter("start_time");// 시간 가져오기
        vo.setVal_date(date);
        vo.setVal_start_time(time);// 날짜+시간 가져오기
        int a = Integer.parseInt(request.getParameter("table_num"));
        vo.setVal_table_number(a);

        ReservationService.addReservation(vo);
        return "<script> alert('예약완료 되었습니다.'); location.href='/listReservation'; </script>";
    }
}