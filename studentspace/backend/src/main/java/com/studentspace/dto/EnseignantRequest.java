package com.studentspace.dto;
import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EnseignantRequest {
    @NotBlank private String nom;
    @NotBlank private String prenom;
    @NotBlank @Email private String email;
    @NotBlank private String motDePasse;
    private String telephone;
    private String specialite;
    private String departement;
    private String bureau;
}
