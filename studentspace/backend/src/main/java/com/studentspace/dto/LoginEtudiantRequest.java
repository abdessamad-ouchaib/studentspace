package com.studentspace.dto;
import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginEtudiantRequest {
    @NotBlank(message = "Le numéro APOGEE est obligatoire")
    private String numeroApogee;
}
