package com.studentspace.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class InscriptionResponse {
    private Long   id;
    private String etudiantNom;
    private Long   etudiantId;
    private String moduleNom;
    private Long   moduleId;
    private String dateInscription;
}
