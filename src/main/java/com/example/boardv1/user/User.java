package com.example.boardv1.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // object mapping을 hibernate가 할 때 디폴트 생성자를 new한다.
@Data
@Entity
@Table(name = "user_tb")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(unique = true) // 테이블의 제약조건 (pk가 unique일 때 인덱스를 만들어준다)
    private String username;

    @Column(nullable = false, length = 100) // password 컬럼은 null일 수 없다! 길이는 100자!
    private String password;
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
