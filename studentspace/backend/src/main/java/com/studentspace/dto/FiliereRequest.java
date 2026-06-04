package com.studentspace.dto;
import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FiliereRequest {
    @NotBlank private String nom;
    private String  description;
    private Integer anneeUniversitaire;
    private Integer semestre;
}
