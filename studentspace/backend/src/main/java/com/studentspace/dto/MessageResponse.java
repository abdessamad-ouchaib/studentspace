package com.studentspace.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageResponse {
    private Long   id;
    private String contenu;
    private String expediteurNom;
    private Long   expediteurId;
    private String dateEnvoi;
    private Long   moduleId;
}
