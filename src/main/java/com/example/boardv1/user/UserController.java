package com.example.boardv1.user;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.boardv1._core.errors.ex.Exception400;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/logout")
    public String logout(){
        session.invalidate();  // sessionKey 삭제
        return "redirect:/";
    }

    // 조회인데, 예외로 post 요청
    @PostMapping("/login")
    public String login(@Valid UserRequest.LoginDTO reqDTO, Errors errors, HttpServletResponse resp) {
        // 유효성 검사 -> AOP가 자동 처리

        // HttpSession session = req.getSession();
        User sessionUser = userService.로그인(reqDTO.getUsername(), reqDTO.getPassword());
        session.setAttribute("sessionUser", sessionUser);

        // http Response header에 Set-Cookie: sessionKey 저장되서 응답됨.
        Cookie cookie = new Cookie("username", sessionUser.getUsername());
        cookie.setHttpOnly(false);
        resp.addCookie(cookie);

        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(@Valid UserRequest.JoinDTO reqDTO, Errors errors){
        // 유효성 검사 -> AOP가 자동 처리

        userService.회원가입(reqDTO.getUsername(), reqDTO.getPassword(), reqDTO.getEmail());
        return "redirect:/login-form"; 
    }
    
    @GetMapping("/login-form")
    public String loginForm(){
        return "user/login-form"; 
    }

    @GetMapping("/join-form")
    public String joinForm(){
        return "user/join-form"; 
    }
}
