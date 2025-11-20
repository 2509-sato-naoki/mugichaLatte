package com.example.mugichaLatte.repository;

import com.example.mugichaLatte.repository.entity.Attendances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendancesRepository extends JpaRepository<Attendances, Integer> {
        @Query("""
    SELECT a
    FROM Attendances a
    WHERE a.userId = :userId
      AND a.date BETWEEN :startDate AND :endDate
    ORDER BY a.date ASC
    """)
        List<Attendances> findAllAttendances(
                @Param("startDate") LocalDate startDate,
                @Param("endDate") LocalDate endDate,
                @Param("userId") int userId
        );

    //重複チェック用
    boolean existsByUserIdAndDate(int userId, LocalDate date);
    // 申請済かつ未承認の勤怠一覧
    List<Attendances> findByStatusAndApprovalStatus(int status, int approvalStatus);
}
