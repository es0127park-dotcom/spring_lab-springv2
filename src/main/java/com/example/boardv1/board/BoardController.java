package com.example.boardv1.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 적혀있는 애로 생성자 만들어줌
@Controller // @Controller 적어야 리턴값이 파일이 됨 (외부진입점)
public class BoardController {
    
    private final BoardService boardService;

    // title : title=title7&content=content7 (x-www-form)
    @PostMapping("/boards/save")
    public String save(BoardSaveDTO reqDTO){ // new해서 넣어줌! 필드가 많을 때 상태만 추가하면 되기 때문에 매우 편함! 재사용 가능!(필드명 잘 적어야 함)
        boardService.게시글쓰기(reqDTO.getTitle(), reqDTO.getContent());
        return "redirect:/"; 
    }

    // body : title=제목&content=내용
    @PostMapping("/boards/{id}/update")
    public String update(@PathVariable("id") int id, @RequestParam("title") String title, @RequestParam("content") String content){
        boardService.게시글수정(id,title,content);
        return "redirect:/boards/"+id; 
    }

    @GetMapping("/")
    public String index(HttpServletRequest req){
        List<Board> list = boardService.게시글목록();
        req.setAttribute("models", list);
        return "index"; 
    }

    @GetMapping("/boards/save-form")
    public String saveForm(){
        return "board/save-form"; 
    }

    @GetMapping("/boards/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest req){
        Board board = boardService.상세보기(id);
        req.setAttribute("model", board);
        return "board/update-form"; 
    }

    @GetMapping("/boards/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest req){
        Board board = boardService.상세보기(id);
        req.setAttribute("model", board);
        return "board/detail"; // mustache 파일의 경로
    }
}
