package com.module.gomodules.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.module.gomodules.VO.ReservationVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationVO, Long> {

    @Query("SELECT COUNT(*) FROM RESERVATION WHERE start_time = ?1 AND table_number = ?2 AND isdeleted = 0")
    public int numberOfReservationBytimeAndtid(String start_time, int table_number);

    @Query("SELECT COUNT(*) FROM RESERVATION WHERE table_number = ?1")
    public int numberOfReservationByTableId(int uid);

    @Query("SELECT COUNT(*) FROM RESERVATION WHERE start_time between ?1 and ?2")
    public int numberOfReservationByTime(String t1, String t2);

//    @Query("SELECT a FROM RESERVATION a WHERE a.start_time = ?1 AND a.table_number = ?2 AND a.rank > ?3 ORDER BY a.rank asc")//확인필요
//    public List<ReservationVO> findAllReservationBytimeAndtidAndRank(String start_time, int table_number, int rank);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE RESERVATION SET covers = ?1, date = ?2 ,start_time = ?3, table_number = ?4 WHERE oid = ?5" )
    public void updateReservationValues(int people_num, String date, String start_time, int table_number, int oid);

    //유저아이디로 예약목록 찾기
    public List<ReservationVO> findAllByuid(int uid);

    public ReservationVO findByOid(int oid);


    @Transactional
    @Modifying
    @Query("DELETE FROM RESERVATION WHERE oid=?1")  //예약삭제
    public void deleteReservationbyoid(int oid);

    @Query("SELECT COUNT(*) FROM RESERVATION WHERE uid=?1")
    public int countReservationByUser(int oid);

    //예약 수정시, 예약 추가시
    //3개의 데이터는 같은데, key값은 다르다. -> 예약을 추가하는 경우에는 항상 key값이 다르다.
    //3개의 데이터는 같은데, key값도 같다. -> 같은 데이터에 대한 참조
    @Query("SELECT COUNT(*) FROM RESERVATION WHERE date=?1 and start_time=?2 and table_number=?3")
    public int numberOfReservationEquals(String date, String start_time, int table_number);

    @Query("SELECT COUNT(*) FROM RESERVATION WHERE date=?1 and start_time=?2 and table_number=?3 and oid=?4")
    public int isSelf(String date, String start_time, int table_number, int oid);

    //날짜와 시간이 같으면
    @Query("SELECT table_number FROM RESERVATION WHERE date=?1 and start_time=?2")
    public List<Integer> reservedTable(String date, String start_time);

    public List<ReservationVO> findAll();
}