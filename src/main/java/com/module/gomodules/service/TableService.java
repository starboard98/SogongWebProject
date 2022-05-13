package com.module.gomodules.service;
import com.module.gomodules.VO.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TableService {
    @Autowired
    TableRepository repository;
    public int numberofTable(){
        return repository.numberOfTable();
    }
    public List<TableVO> getAllTable() {
        return repository.findAll();
    }
}
