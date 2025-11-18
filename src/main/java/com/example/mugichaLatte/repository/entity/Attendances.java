package com.example.mugichaLatte.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "attendances")
@Getter
@Setter
public class Attendances {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "work_type")
    private int workType;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "rest")
    private int rest;

    @Column(name = "status")
    private int status;

    @Column(name = "memo")
    private String memo;

//    private int workTime;

}
