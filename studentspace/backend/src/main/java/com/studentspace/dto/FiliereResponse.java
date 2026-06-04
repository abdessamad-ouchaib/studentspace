package com.studentspace.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FiliereResponse {
    private Long    id;
    private String  nom;
    private String  description;
    private Integer anneeUniversitaire;
    private Integer semestre;
    private int     nombreEtudiants;
    private int     nombreModules;
}
