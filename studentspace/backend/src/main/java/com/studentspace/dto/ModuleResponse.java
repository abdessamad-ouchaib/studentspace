package com.studentspace.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ModuleResponse {
    private Long   id;
    private String nom;
    private String description;
    private String filiere;
    private Long   filiereId;
    private String enseignant;
    private Long   enseignantId;
    private int    nombreEtudiants;
}
