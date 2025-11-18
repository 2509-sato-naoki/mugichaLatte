package com.example.mugichaLatte.service;

import com.example.mugichaLatte.controller.form.AttendancesSearchForm;
import com.example.mugichaLatte.repository.AttendancesRepository;
import com.example.mugichaLatte.repository.entity.Attendances;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class AttendancesService {
    @Autowired
    AttendancesRepository attendancesRepository;
    public Map<String, Attendances> findAllAttendances(AttendancesSearchForm form) {
        //何も検索されていない場合,今月の月の勤怠を取得する（初期画面表示）
        if (form.getDate() == null) {
            LocalDate now = LocalDate.now();
            // 今月の最初の日
            LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
            // 今月の最後の日
            LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
            //暫定対応
            int userId = 1;

            //リストで月の勤怠記録を取得
            List<Attendances> attendancesList= attendancesRepository.findAllAttendances(firstDay, lastDay, userId);

            Map<String, Attendances> dateToAttendance = new HashMap<>();
            for (Attendances a : attendancesList) {
                dateToAttendance.put(a.getDate().toString(), a);
            }
            return dateToAttendance;

        } else {
            // 検索月の最初の日
            LocalDate firstDay = form.getDate().with(TemporalAdjusters.firstDayOfMonth());
            // 検索月の最後の日
            LocalDate lastDay = form.getDate().with(TemporalAdjusters.lastDayOfMonth());
            //暫定対応
            int userId = 1;

            //リストで月の勤怠記録を取得
            List<Attendances> attendancesList= attendancesRepository.findAllAttendances(firstDay, lastDay, userId);

            Map<String, Attendances> dateToAttendance = new HashMap<>();
            for (Attendances a : attendancesList) {
                dateToAttendance.put(a.getDate().toString(), a);
            }
            return dateToAttendance;
        }
    }
}
