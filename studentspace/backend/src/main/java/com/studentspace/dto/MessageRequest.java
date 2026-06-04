package com.studentspace.dto;
import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageRequest {
    @NotBlank private String contenu;
    private Long moduleId;
}
