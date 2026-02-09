package com.example.boardv1.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.boardv1._core.errors.ex.Exception400;
import com.example.boardv1._core.errors.ex.Exception401;
import com.example.boardv1._core.errors.ex.Exception500;
import com.example.boardv1.user.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 적혀있는 애로 생성자 만들어줌
@Controller // @Controller 적어야 리턴값이 파일이 됨 (외부진입점)
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    // title : title=title7&content=content7 (x-www-form)
    @PostMapping("/boards/save")
    public String save(@Valid BoardRequest.SaveOrUpdateDTO reqDTO, Errors errors) { // new해서 넣어줌! 필드가 많을 때 상태만 추가하면 되기 때문에 매우 편함! 재사용 가능!(필드명 잘 적어야 함)
        // 유효성 검사 -> AOP가 자동 처리

        // 인증(v) 권한(x)
        // 인증
        User sessionUser = (User) session.getAttribute("sessionUser");

        boardService.게시글쓰기(reqDTO.getTitle(), reqDTO.getContent(), sessionUser.getId());
        return "redirect:/";
    }

    // body : title=제목&content=내용
    @PostMapping("/boards/{id}/update")
    public String update(@PathVariable("id") int id, @Valid BoardRequest.SaveOrUpdateDTO reqDTO, Errors errors) {
        // 유효성 검사 -> AOP가 자동 처리

        // 인증(v) 권한(v)
        // 인증
        User sessionUser = (User) session.getAttribute("sessionUser");

        boardService.게시글수정(id, reqDTO.getTitle(), reqDTO.getContent(), sessionUser.getId());
        return "redirect:/boards/" + id;
    }

    @GetMapping("/")
    public String index(HttpServletRequest req) {
        List<Board> list = boardService.게시글목록();
        req.setAttribute("models", list);
        return "index";
    }

    @GetMapping("/boards/save-form")
    public String saveForm() {
        // 인증(v) 권한(x)
        // 인증

        return "board/save-form";
    }

    @GetMapping("/boards/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest req) {
        // 인증(v) 권한(v)
        // 인증
        User sessionUser = (User) session.getAttribute("sessionUser");

        Board board = boardService.수정폼게시글정보(id, sessionUser.getId());
        req.setAttribute("model", board);
        return "board/update-form";
    }

    @GetMapping("/boards/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest req) {
        User sessionUser = (User) session.getAttribute("sessionUser"); // Object타입으로 반환하기 때문에 다운캐스팅해야 함
        Integer sessionUserId = sessionUser == null ? null : sessionUser.getId();
        BoardResponse.DetailDTO dto = boardService.상세보기(id, sessionUserId);
        req.setAttribute("model", dto);
        return "board/detail"; // mustache 파일의 경로
    }

    @PostMapping("/boards/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        // 인증(v) 권한(v)
        // 인증
        User sessionUser = (User) session.getAttribute("sessionUser");

        try {
            boardService.게시글삭제(id, sessionUser.getId());
        } catch (Exception e) {
            throw new Exception500("댓글이 있는 게시글을 삭제할 수 없습니다");
        }

        return "redirect:/";
    }

    @GetMapping("/api/boards/{id}")
    public @ResponseBody BoardResponse.DetailDTO apiDetail(@PathVariable("id") int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer sessionUserId = sessionUser == null ? null : sessionUser.getId();
        BoardResponse.DetailDTO dto = boardService.상세보기(id, sessionUserId);
        return dto;
    }
}
