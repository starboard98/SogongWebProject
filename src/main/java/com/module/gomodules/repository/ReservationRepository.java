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


    @Query("SELECT a FROM RESERVATION a WHERE a.start_time = ?1 AND a.table_number = ?2 AND a.rank > ?3 ORDER BY a.rank asc")//확인필요
    public List<ReservationVO> findAllReservationBytimeAndtidAndRank(String start_time, int table_number, int rank);

    @Query("SELECT a FROM RESERVATION a WHERE a.uid = ?1 AND a.isdeleted = 0" )
    public List<ReservationVO> findAllByuidForUser(int table_number);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE RESERVATION SET rank = rank - 1 WHERE oid = ?1" )
    public void updateReservationRank(int oid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE RESERVATION SET wait = 0 WHERE rank = 0" )
    public void updateReservationWait(int oid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE RESERVATION SET isdeleted = 1 WHERE oid = ?1" )
    public void updateReservationIsdeleted(int oid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE RESERVATION SET covers = ?1 WHERE oid = ?2" )
    public void updateReservationPeopleNumber(int covers, int oid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE RESERVATION SET covers = ?1, start_time = ?2, table_number = ?3, wait = ?4, rank = ?5  WHERE oid = ?6" )
    public void updateReservationValues(int covers, String start_time, int table_number, int wait, int rank ,int oid);


    public List<ReservationVO> findAllByuid(int uid);

    public ReservationVO findByOid(int oid);


    @Transactional
    @Modifying
    @Query("DELETE FROM RESERVATION WHERE oid=?1")
    public void deleteReservationbyoid(int oid);

    @Query("SELECT COUNT(*) FROM RESERVATION WHERE uid=?1")
    public int countReservationByUser(int oid);

}