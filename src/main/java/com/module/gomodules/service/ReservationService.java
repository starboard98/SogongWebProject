package com.module.gomodules.service;

import com.module.gomodules.VO.ReservationVO;
import com.module.gomodules.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository Repository;

    public void addReservation(ReservationVO vo) {
        System.out.println("예약 추가");
        Repository.save(vo);
        System.out.println("예약 추가 성공");
    }

    public int findByEquals(String date, String start_time, int table_number) {
        return Repository.numberOfReservationEquals(date, start_time, table_number);
    }

    public int isSelf(String date, String start_time, int table_number, int oid) {
        return Repository.isSelf(date, start_time, table_number, oid);
    }

    public List<ReservationVO> getReservationList(int uid) {
        return Repository.findAllByuid(uid);
    }


    public void deleteReservationbyoid(int oid) {
        // TODO Auto-generated method stub
        Repository.deleteReservationbyoid(oid);
    }
    public ReservationVO findByOid(int oid){
        return Repository.findByOid(oid);
    }

    public void updateReservation(int people_num, String date, String start_time, int table_number, int oid){
        Repository.updateReservationValues(people_num, date, start_time, table_number, oid);
        System.out.println("예약 수정 성공");
    }
}