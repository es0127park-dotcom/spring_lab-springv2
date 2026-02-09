package com.example.boardv1.reply;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class ReplyRequest {
    
    @Data
    public static class SaveDTO {
        private Integer boardId;
        @NotBlank(message = "댓글 내용을 입력하세요")
        private String comment;
    }

    @Data
    public static class DeleteDTO {
        private Integer boardId;
    }
}
