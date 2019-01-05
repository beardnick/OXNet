package com.example.demo.model;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class ResultData implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int start;

    @Column
    private int head_up;

    @Column
    private int head_down;

    @Column
    private int lie_down;

    @Column
    private int stand_up;

    @Column
    private float move_distance;

//    @Column
////    private float breath_frequency;
////
////    @Column
////    private float breath_depth;

    @Column
    private float breath_a;

    public int getHead_up() {
        return head_up;
    }

    public void setHead_up(int head_up) {
        this.head_up = head_up;
    }

    public int getHead_down() {
        return head_down;
    }

    public void setHead_down(int head_down) {
        this.head_down = head_down;
    }

    public int getStand_up() {
        return stand_up;
    }

    public void setStand_up(int stand_up) {
        this.stand_up = stand_up;
    }

    public int getLie_down() {
        return lie_down;
    }

    public void setLie_down(int lie_down) {
        this.lie_down = lie_down;
    }

    public float getMove_distance() {
        return move_distance;
    }

    public void setMove_distance(float move_distance) {
        this.move_distance = move_distance;
    }

//    public float getBreath_frequency() {
//        return breath_frequency;
//    }
//
//    public void setBreath_frequency(float breath_frequency) {
//        this.breath_frequency = breath_frequency;
//    }
//
//    public float getBreath_depth() {
//        return breath_depth;
//    }
//
//    public void setBreath_depth(float breath_depth) {
//        this.breath_depth = breath_depth;
//    }

    public float getBreath_a() {
        return breath_a;
    }

    public void setBreath_a(float breath_a) {
        this.breath_a = breath_a;
    }
}
