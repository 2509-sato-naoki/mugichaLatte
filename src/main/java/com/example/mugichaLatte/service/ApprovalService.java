package com.example.mugichaLatte.service;

import com.example.mugichaLatte.repository.AttendancesRepository;
import com.example.mugichaLatte.repository.entity.Attendances;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalService {
    @Autowired
    AttendancesRepository attendancesRepository;

    public List<Attendances> getApprovalList(){
        return attendancesRepository.findByStatusAndApprovalStatus(1,0);
    }

    @Transactional
    public void permit (List<Integer>ids) {
        for (Integer id : ids) {
            attendancesRepository.approve(id);
        }
    }

    @Transactional
    public void reject(List<Integer> ids) {
        for (Integer id : ids) {
            attendancesRepository.reject(id);
        }
    }
}
