package com.module.gomodules.repository;

import com.module.gomodules.VO.CustomerVO;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<CustomerVO, Long> {
    public CustomerVO findById(String username);

    @Query("SELECT a FROM USER a WHERE a.oid = ?1")
    public CustomerVO findByUid(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE USER SET noshow = noshow + 1 WHERE oid = ?1")
    public int increaseNoShowCount(int uid);
}