package com.example.boardv1._core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.example.boardv1._core.errors.ex.Exception400;

@Aspect
@Component
public class ValidationHandler {
    
    // @Before : 컨트롤러 메서드 실행 전에 가로채기
    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping)") // Controller에 import된 PostMapping의 패키지명 복사해서 붙여넣기
    public void validationCheck(JoinPoint jp) {
        // 메서드의 모든 파라미터를 순회
        for (Object arg : jp.getArgs()) {
            // Errors 타입 파라미터를 찾으면
            if (arg instanceof Errors errors) {
                // 에러가 있으면 Exception400 throw
                if (errors.hasErrors()) {
                    throw new Exception400(errors.getAllErrors().get(0).getDefaultMessage());
                }
            }
        }
    }
}
