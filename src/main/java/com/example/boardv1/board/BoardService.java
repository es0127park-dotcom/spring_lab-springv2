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
    
}
