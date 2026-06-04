package com.studentspace.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class InscriptionRequest {
    private Long etudiantId;
    private Long moduleId;
}
