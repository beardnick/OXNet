package com.example.demo.controller;
import com.example.demo.dao.OxDataRepository;
import com.example.demo.model.OxData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OxDataController {

    @Autowired
    private OxDataRepository oxDataRepository;

    @RequestMapping("/getAll")
    public List<OxData> getAll(){
        return oxDataRepository.findAll();
    }


}
