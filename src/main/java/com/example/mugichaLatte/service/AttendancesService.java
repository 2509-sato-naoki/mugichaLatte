package com.example.mugichaLatte.service;

import com.example.mugichaLatte.controller.form.AttendancesEditForm;
import com.example.mugichaLatte.controller.form.AttendancesSearchForm;
import com.example.mugichaLatte.repository.AttendancesRepository;
import com.example.mugichaLatte.repository.entity.Attendances;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class AttendancesService {
    @Autowired
    AttendancesRepository attendancesRepository;
    public Map<String, Attendances> findAllAttendances(AttendancesSearchForm form, int userId) {
        //何も検索されていない場合,今月の月の勤怠を取得する（初期画面表示）
        if (form.getDate() == null) {
            LocalDate now = LocalDate.now();
            // 今月の最初の日
            LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
            // 今月の最後の日
            LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());

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

            //リストで月の勤怠記録を取得
            List<Attendances> attendancesList= attendancesRepository.findAllAttendances(firstDay, lastDay, userId);

            Map<String, Attendances> dateToAttendance = new HashMap<>();
            for (Attendances a : attendancesList) {
                dateToAttendance.put(a.getDate().toString(), a);
            }
            return dateToAttendance;
        }
    }

    public AttendancesEditForm findAttendances(String id) {
        int attendancesId = Integer.parseInt(id);
        Attendances attendances = attendancesRepository.findById(attendancesId).orElse(null);
        AttendancesEditForm form = new AttendancesEditForm();
        form.setId(attendances.getId());
        form.setUserId(attendances.getUserId());
        form.setDate(attendances.getDate());
        form.setWorkType(attendances.getWorkType());
        form.setStartTime(attendances.getStartTime());
        form.setEndTime(attendances.getEndTime());
        form.setRest(attendances.getRest());
        form.setStatus(attendances.getStatus());
        form.setMemo(attendances.getMemo());
        form.setApprovalStatus(attendances.getApprovalStatus());
        return form;
    }

    public void saveAttendances(AttendancesEditForm form) {
        Attendances attendances = new Attendances();
        attendances.setId(form.getId());
        attendances.setUserId(form.getUserId());
        attendances.setDate(form.getDate());
        attendances.setWorkType(
                form.getWorkType() == null ? 0 : form.getWorkType()
        );
        attendances.setStartTime(form.getStartTime());
        attendances.setEndTime(form.getEndTime());
        attendances.setRest(form.getRest() == null ? 0 :form.getRest());
        attendances.setStatus(form.getStatus());
        attendances.setMemo(form.getMemo());
        //attendances.setApprovalStatus(form.getApprovalStatus());
        attendances.setUpdatedDate(LocalDateTime.now());
        attendancesRepository.save(attendances);
    }

    public void deleteAttendances(int userId) {
        attendancesRepository.deleteById(userId);
    }
}