package com.example.boardv1.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // 컴포넌트 스캔되서 IoC에 뜸 + 목적 : 트랜잭션 관리
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> 게시글목록() {
        return boardRepository.findAll();
    }

    public Board 상세보기(int id) {
        return boardRepository.findById(id);
    }

    @Transactional // update, delete, insert 할 때 붙이기!! import 주의! springframework(O) 자카르타(X)
    public void 게시글수정(int id, String title, String content) {
        Board board = boardRepository.findById(id);
        board.setTitle(title);
        board.setContent(content);
        // 자동 flush
    }

    // 역할 1. 원자성(모든 게 다 되면 commit, 하나라도 실패하면 rollback)
    // 역할 2. 트랜잭션 종료시 flush 됨
    @Transactional 
    public void 게시글쓰기(String title, String content) {
        // 1. 비영속 객체
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);

        System.out.println("before persist " + board.getId());

        // 2. persist
        boardRepository.save(board);

        System.out.println("after persist " + board.getId());
    }
    
}
