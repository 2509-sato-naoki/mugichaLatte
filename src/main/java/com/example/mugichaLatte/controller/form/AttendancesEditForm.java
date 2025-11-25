package com.example.mugichaLatte.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    private Integer workType;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    private LocalTime endTime;

    private Integer rest;

    private Integer status;

    @Size(max = 50, message = "メモは50文字以内で入力してください")
    private String memo;

    private Integer approvalStatus;

}
