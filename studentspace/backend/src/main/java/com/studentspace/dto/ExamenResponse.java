package com.studentspace.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ExamenResponse {
    private Long    id;
    private String  titre;
    private Integer anneeExamen;
    private String  fichierUrl;
    private String  module;
    private Long    moduleId;
    private String  dateTeleversement;
}
