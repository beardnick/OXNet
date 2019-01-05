package com.example.demo.dao;
import com.example.demo.model.ResultData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ResultDataRepository extends JpaRepository<ResultData, Long> {

    //start,head_up,head_down,stand_up,lie_down,move_distance,breath_a
    @Modifying
    @Query(value = "insert into analysis_result(start, head_up, head_down, stand_up, lie_down, move_distance, breath_a)values(?1,?2,?3,?4,?5,?6,?7)", nativeQuery = true)
    void saveAnalysisResult(int start,int head_up, int head_down, int stand_up, int lie_down, float move_distance, float breath_a);

//    @Modifying
//    @Query(value = "select * from analysis_result where start=?",nativeQuery = true)
//    ResultData select_breath(int start);
}
