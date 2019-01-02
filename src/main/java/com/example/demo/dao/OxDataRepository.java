package com.example.demo.dao;

import com.example.demo.model.OxData;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OxDataRepository extends JpaRepository<OxData, Long> {
    public List<OxData> findAll();

}
