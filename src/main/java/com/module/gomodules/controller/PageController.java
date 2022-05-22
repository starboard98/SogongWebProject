package com.module.gomodules.controller;

import com.module.gomodules.VO.CustomerVO;
import com.module.gomodules.VO.ReservationVO;
import com.module.gomodules.VO.TableVO;
import com.module.gomodules.repository.ReservationRepository;
import com.module.gomodules.repository.TableRepository;
import com.module.gomodules.repository.UserRepository;
import com.module.gomodules.service.TableService;
import com.module.gomodules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PageController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    TableRepository tableRepository;

    @Autowired
    TableService tableService;

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

    @ResponseBody
    @RequestMapping(value = "/addTable.do", produces = "text/html; charset=UTF-8")
    public String addTable(HttpServletRequest request, TableVO vo) {

        int tableNumber;

        if(request.getParameter("tableNumber") =="") {
            return "<script> alert('테이블 번호를 입력해주세요.');  location.href= 'locateAddTable.do'; </script>";
        }
        tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
        vo.setVal_rid(tableNumber);
        tableRepository.save(vo);

        return "<script> alert('추가되었습니다.'); window.close();</script>";
    }

    @RequestMapping(value = "/locateAddTable.do")
    public String locateAddTable() {
        return "/addTable";
    }

    @RequestMapping(value = "/locateShowTable.do")
    public String locateShowTable(Model model){
        model.addAttribute("table", tableService.getAllTable());
        return "/showTable";
    }

//    @GetMapping("/showReservation.do")
//    public String tableList(Model model){
//        List<TableVO> tableVOList = tableRepository.findAll();
//        model.addAttribute("list", tableVOList);
//        return "/noEventReservation";
//    }


}
