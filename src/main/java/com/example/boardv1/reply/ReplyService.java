package com.example.boardv1.reply;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.boardv1.board.Board;
import com.example.boardv1.board.BoardRepository;
import com.example.boardv1.user.User;
import com.example.boardv1.user.UserRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final EntityManager em;

    @Transactional
    public void 댓글등록(int sessionUserId, int boardId, String comment) {
        Board board = em.getReference(Board.class, boardId); // 가짜로 영속화. 조회하면 계속 쿼리 터지는데 조회 안해도 됨
        User user = em.getReference(User.class, sessionUserId);

        Reply reply = new Reply();
        reply.setBoard(board);
        reply.setUser(user);
        reply.setComment(comment);

        replyRepository.save(reply);
    }

    @Transactional
    public void 댓글삭제(int id, int sessionUserId) {
        // 영속화(권한체크 하려면 findById해야 함. 권한체크 안해도 되면 getReference사용)
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("삭제할 댓글을 찾을 수 없어요"));

        // 권한
        if (sessionUserId != reply.getUser().getId())
            throw new RuntimeException("삭제할 권한이 없습니다");

        replyRepository.delete(reply);
    }

}
