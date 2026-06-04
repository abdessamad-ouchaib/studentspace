package com.studentspace.dto;
import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ModuleRequest {
    @NotBlank private String nom;
    private String description;
    private Long   filiereId;
    private Long   enseignantId;
}
