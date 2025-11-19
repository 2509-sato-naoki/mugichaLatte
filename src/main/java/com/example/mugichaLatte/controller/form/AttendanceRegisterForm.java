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
public class AttendanceRegisterForm {

    @NotNull(message = "日付を入力してください")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "勤務区分を選択してください")
    private Integer workType;

    @NotNull(message = "勤務開始時刻を入力してください")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "勤務終了時刻を入力してください")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private Integer rest;

    private Integer status; // 0が未申請 ・ 1が申請済み

    @Size(max = 50, message = "メモは50文字以内で入力してください")
    private String memo;

    private Integer approvalStatus;


}
