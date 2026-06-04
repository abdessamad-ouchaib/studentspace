package com.studentspace.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EnseignantResponse {
    private Long   id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String specialite;
    private String departement;
    private String bureau;
    private int    nombreModules;
}
