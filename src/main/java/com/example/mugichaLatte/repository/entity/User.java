package com.example.mugichaLatte.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** アカウント */
    @Column(name = "account", length = 20, nullable = false, unique = true)
    private String account;

    /** パスワード */
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    /** 氏名 */
    @Column(name = "name", length = 10, nullable = false)
    private String name;

    /** 部署 (0〜3) */
    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    /**
     * 区分
     * 0 : 管理者
     * 1 : 承認者
     * 2 : 一般
     * 3 : 管理者兼承認者
     */
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 停止しているか
     * 0 : 停止
     * 1 : 稼働
     */
    @Column(name = "is_stopped", nullable = false)
    private Integer isStopped;

    /** 登録日時 */
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    /** 更新日時 */
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;
}
