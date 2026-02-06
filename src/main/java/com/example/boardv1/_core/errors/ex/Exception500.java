package com.example.boardv1._core.errors.ex;

// 서버측 에러(미리 설계할 수 없음) -> 만들어놨다가 나중에 예외가 터지면 잡으면 됨
public class Exception500 extends RuntimeException {

    public Exception500(String message) {
        super(message);
    }
    
}
