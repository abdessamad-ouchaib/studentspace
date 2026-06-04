package com.studentspace.dto;
import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginEnseignantRequest {
    @NotBlank @Email(message = "Email invalide")
    private String email;
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
}
