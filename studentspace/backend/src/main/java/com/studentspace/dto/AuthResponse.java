package com.studentspace.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    private String token;
    private String role;
    private Long   userId;
    private String nom;
    private String prenom;
    private String email;
    private String numeroApogee;
}
