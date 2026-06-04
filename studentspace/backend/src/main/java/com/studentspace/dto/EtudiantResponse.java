package com.studentspace.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EtudiantResponse {
    private Long   id;
    private String numeroApogee;
    private String nom;
    private String prenom;
    private String email;
    private String filiere;
    private Long   filiereId;
    private String dateCreation;
}
