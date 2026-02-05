package com.example.boardv1.reply;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.example.boardv1.board.Board;
import com.example.boardv1.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시글 1 : 댓글 N
 * 유저 1 : 댓글 N
 */

@NoArgsConstructor
@Data
@Entity
@Table(name = "reply_tb")
public class Reply {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String comment;

    @ManyToOne // 디폴트 : EAGER 전략
    private Board board; // board_tb

    @ManyToOne
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
