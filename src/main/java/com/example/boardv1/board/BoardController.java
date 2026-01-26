package com.example.boardv1.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller // @Controller 적어야 리턴값이 파일이 됨
public class BoardController {
    
    @GetMapping("/")
    public String index(){
        return "index"; 
    }

    @GetMapping("/boards/save-form")
    public String saveForm(){
        return "board/save-form"; 
    }

    @GetMapping("/boards/{id}/update-form")
    public String updateForm(@PathVariable("id") int id){
        return "board/update-form"; 
    }

    @GetMapping("/boards/{id}")
    public String detail(@PathVariable("id") int id){
        return "board/detail"; 
    }
}
