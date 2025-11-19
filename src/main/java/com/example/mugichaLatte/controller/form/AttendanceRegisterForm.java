package com.example.mugichaLatte.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceRegisterForm {

    @NotBlank
    private String date;

    @NotBlank
    private Integer workType;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    private Integer rest;

    private Integer status; // 0が未申請 ・ 1が申請済み

    @Size(max = 50)
    private String memo;


}
