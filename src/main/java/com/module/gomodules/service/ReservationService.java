package com.module.gomodules.service;

import com.module.gomodules.VO.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.module.gomodules.VO.ReservationVO;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository Repository;

    public void addReservation(ReservationVO vo){
        System.out.println("예약 추가");
        Repository.save(vo);
        System.out.println("예약 추가 성공");
    }
    public int findWaitRank(String time, int tableid) {
        return Repository.numberOfReservationBytimeAndtid(time, tableid);
    }
    public int findByTableId(int uid){
        return Repository.numberOfReservationByTableId(uid);
    }
    public List<ReservationVO> getReservationListAll(){
        //Sort sort = Sort.by(Sort.Direction.DESC, "oid");
        return Repository.findAll();
    }

    public int countReservationByMonth(String t1, String t2){
        return Repository.numberOfReservationByTime(t1, t2);
    }

    public List<ReservationVO> getReservationList(int uid) {
        return Repository.findAllByuid(uid);
    }
    public void deleteReservationbyoid(int oid) {
        // TODO Auto-generated method stub
        Repository.deleteReservationbyoid(oid);
    }


    public List<ReservationVO> getReservationListForUser(int uid){
        return Repository.findAllByuidForUser(uid);
    }
    public void removeReservationForUser(ReservationVO vo) { //유저전용 예약 삭제기능 검증은 안됨
        int oid = vo.getVal_oid();
        String start_time = vo.getVal_start_time();
        int tid =vo.getVal_table_number();
        int rank = vo.getVal_rank();
        List<ReservationVO> list = Repository.findAllReservationBytimeAndtidAndRank(start_time, tid, rank);
        if(list.size() == 0) {//이 예약 뒤로 대기중인 예약이 없다면
            Repository.updateReservationIsdeleted(oid);
        }

        else { //이 예약 뒤로 대기중인 예약이 있다면
            //대기순서 조절 시작
            for (ReservationVO vo2 : list) {
                int nowoid = vo2.getVal_oid();
                Repository.updateReservationRank(nowoid);//rank 조절
                Repository.updateReservationWait(nowoid);//wait 조절
            }
            Repository.updateReservationIsdeleted(oid);
        }
    }
    public void modifyReservation(int oid, String input_start_time,int input_people_number, int new_tid, int new_wait, int new_rank) {
        ReservationVO vo = Repository.findByOid(oid);
        int tid = vo.getVal_table_number();
        int rank = vo.getVal_rank();
        String origin_start_time = vo.getVal_start_time();


        //변경이전 시간대의 rank영향받는 예약들의 리스트임
        List<ReservationVO> list = Repository.findAllReservationBytimeAndtidAndRank(origin_start_time, tid, rank);
        if(list.size() == 0) {//이 예약 뒤로 대기중인 예약이 없다면
            Repository.updateReservationValues(input_people_number, input_start_time, new_tid, new_wait, new_rank, oid);
        }

        else { //이 예약 뒤로 대기중인 예약이 있다면
            //대기순서 조절 시작
            for (ReservationVO vo2 : list) {
                int nowoid = vo2.getVal_oid();
                Repository.updateReservationRank(nowoid);//rank 조절
                Repository.updateReservationWait(nowoid);//wait 조절
            }
            Repository.updateReservationValues(input_people_number, input_start_time, new_tid, new_wait, new_rank, oid);
        }
    }

    public ReservationVO findByOid(int oid){
        return Repository.findByOid(oid);
    }

    public void updateReservationPeopleNumber(int people_num, int oid) {
        Repository.updateReservationPeopleNumber(people_num, oid);
    }

    public int countReservationByUser(int oid){ return Repository.countReservationByUser(oid); }
}
