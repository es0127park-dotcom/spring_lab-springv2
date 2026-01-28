package com.example.boardv1.board;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;


/** 
 * 하이버네이트 기술
 */

@RequiredArgsConstructor // final이 붙어 있는 모든 필드를 초기화하는 생성자를 만들어줌
@Repository  // DB에 연결되어 있는 컴퍼넌트
public class BoardRepository {
    
    private final EntityManager em;  // EntityManager(DBConnection, 이름만 다름)는 인터페이스라서 new 할 수 없고, 내가 new해서 아래 매개변수로 넣기 어려움->프레임워크가 미리 띄워놓음

    // DI = 의존성 주입 (의존하고 있는 게 IoC에 떠있어야 함)
    // public BoardRepository(EntityManager em) {
    //     this.em = em;
    // }

    public Board findById(int id) {
        Board board = em.find(Board.class, id);
        return board;
    }

    public List<Board> findAll() {
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class); // 객체 지향 쿼리
        List<Board> list = query.getResultList();
        return list;
    }

    public void findAllV2() {
        em.createQuery("select b.id, b.title from Board b").getResultList();
    }

    public Board save(Board board) {
        em.persist(board); // 영속화(영구히 저장한다)->디스크에
        return board;
    }

    public void delete(Board board) {
        em.remove(board);
    }
}
