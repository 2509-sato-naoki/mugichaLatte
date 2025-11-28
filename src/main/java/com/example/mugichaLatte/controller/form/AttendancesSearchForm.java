package com.example.mugichaLatte.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

@Getter
@Setter
public class AttendancesSearchForm {
    private YearMonth date;
}
