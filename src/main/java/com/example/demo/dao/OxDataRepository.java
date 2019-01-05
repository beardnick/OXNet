package com.example.demo.dao;

import com.example.demo.model.OxData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OxDataRepository extends JpaRepository<OxData, Long> {
    public List<OxData> findAll();

//    @Modifying
//    @Query(value = "select * from start", nativeQuery = true)
//    int query_start();

//    @Query(value = "update start set start=?", nativeQuery = true)
//    void update_start(int start);

//    @Query(value = "insert into analysis_result(start,end,head_up,head_down,stand_up,lie_down,breath_frequency,breath_depth,move_distance,breath_a)value(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)", nativeQuery = true)
//    @Modifying
//    void saveAnalysisResult(int start,int end, int head_up, int head_down, int stand_up, int lie_down, float breath_frequency, float breath_depth, float move_distance, float breath_a);
//
//    @Modifying
//    @Query(value = "select ar.breath_frequency,ar.berath_a from analysis_result ar where ar.start=?",nativeQuery = true)
//    List<Object> select_breath(int start);
    @Modifying
    @Query(value = "insert into analysis_result(start, head_up, head_down, stand_up, lie_down, move_distance, breath_a)values(?1,?2,?3,?4,?5,?6,?7)", nativeQuery = true)
    void saveAnalysisResult(int start,int head_up, int head_down, int stand_up, int lie_down, float move_distance, float breath_a);

    @Modifying
    @Query(value = "select * from ox_data LIMIT ?1,?2", nativeQuery = true)
    List<OxData> findNext(int start,int offset);
    //@Query("update t_user set name=:name where id=:id")
    //    void updateUserImg(@Param(value = "name")String name,@Param(value = "id") Integer id);

    @Query(value = "SELECT count(*) FROM ox_data", nativeQuery = true)
    int getLastId();

    @Query(value = "select count(*) from analysis_result where stand_up=1", nativeQuery = true)
    int count_standUp();

    @Query(value = "select count(*) from analysis_result where  lie_down=1", nativeQuery = true)
    int count_lieDown();

    @Query(value = "select count(*) from analysis_result where head_up=1", nativeQuery = true)
    int count_headUp();

    @Query(value = "select count(*) from analysis_result where head_down=1", nativeQuery = true)
    int count_headDown();

}
