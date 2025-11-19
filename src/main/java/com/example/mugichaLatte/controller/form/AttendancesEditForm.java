package com.example.mugichaLatte.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AttendancesEditForm {

    private Integer id;

    private Integer userId;

    private LocalDate date;

    private Integer workType;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer rest;

    private Integer status;

    private String memo;

    //approval_status
    //private Integer approvalStatus;
}
