package com.module.gomodules.repository;

import com.module.gomodules.VO.TableVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<TableVO, Long> {
    @Query("SELECT COUNT(*) FROM TABLE1")
    public int numberOfTable();
}