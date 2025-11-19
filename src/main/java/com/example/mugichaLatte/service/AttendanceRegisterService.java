package com.example.mugichaLatte.service;

import com.example.mugichaLatte.controller.form.AttendanceRegisterForm;
import com.example.mugichaLatte.repository.AttendancesRepository;
import com.example.mugichaLatte.repository.entity.Attendances;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AttendanceRegisterService {
    @Autowired
    AttendancesRepository attendanceRepository;

    //ユーザーIDと日付で重複がないか教えるメソッド
    public boolean isDuplicate(Integer userId, LocalDate date) {
        return attendanceRepository.existsByUserIdAndDate(userId, date);
    }

    //登録するメソッド
    public void insertAttendance(Integer userId, AttendanceRegisterForm attendanceRegisterForm){
        Attendances attendances = new Attendances();
        attendances.setUserId(userId);
        attendances.setDate(attendanceRegisterForm.getDate());
        attendances.setWorkType(attendanceRegisterForm.getWorkType());
        attendances.setStartTime(attendanceRegisterForm.getStartTime());
        attendances.setEndTime(attendanceRegisterForm.getEndTime());
        attendances.setRest(attendanceRegisterForm.getRest() == null ? 0 :attendanceRegisterForm.getRest());
        attendances.setStatus(attendanceRegisterForm.getStatus() == null ? 0 :attendanceRegisterForm.getStatus());
        attendances.setApprovalStatus(0);
        attendanceRepository.save(attendances);
    }
}
