package com.studentspace.dto;
import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ExamenRequest {
    @NotBlank private String titre;
    private Integer anneeExamen;
    private String  fichierUrl;
    private Long    moduleId;
}
