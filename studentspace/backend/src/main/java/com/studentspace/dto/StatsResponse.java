package com.studentspace.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StatsResponse {
    private long nombreEtudiants;
    private long nombreEnseignants;
    private long nombreModules;
    private long nombreFilieres;
    private long nombreExamens;
    private long nombreInscriptions;
}
