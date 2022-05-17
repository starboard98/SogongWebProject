package com.module.gomodules.service;
import com.module.gomodules.VO.TableVO;
import com.module.gomodules.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class TableService {
    @Autowired
    TableRepository repository;
    public int numberofTable(){
        return repository.numberOfTable();
    }
    public List<TableVO> showTable() {
        return repository.findAll();
    }
}