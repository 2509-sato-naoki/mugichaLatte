package com.example.mugichaLatte.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AttendancesEditForm {

    private Integer id;

    private Integer userId;

    private LocalDate date;

    @NotNull(message = "勤務区分を選択してください")
    private Integer workType;

    @NotNull(message="開始時間を入力してください")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message="開始時間を入力してください")
    private LocalTime endTime;

    private Integer rest;

    private Integer status;

    private String memo;

    private Integer approvalStatus;

}
