package com.studentspace.dto;
public class StatsResponse {
    private long nombreEtudiants; private long nombreEnseignants; private long nombreModules;
    private long nombreFilieres; private long nombreExamens; private long nombreInscriptions;
    public StatsResponse() {}
    public long getNombreEtudiants(){return nombreEtudiants;} public void setNombreEtudiants(long v){nombreEtudiants=v;}
    public long getNombreEnseignants(){return nombreEnseignants;} public void setNombreEnseignants(long v){nombreEnseignants=v;}
    public long getNombreModules(){return nombreModules;} public void setNombreModules(long v){nombreModules=v;}
    public long getNombreFilieres(){return nombreFilieres;} public void setNombreFilieres(long v){nombreFilieres=v;}
    public long getNombreExamens(){return nombreExamens;} public void setNombreExamens(long v){nombreExamens=v;}
    public long getNombreInscriptions(){return nombreInscriptions;} public void setNombreInscriptions(long v){nombreInscriptions=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final StatsResponse r=new StatsResponse();
        public Builder nombreEtudiants(long v){r.nombreEtudiants=v;return this;}
        public Builder nombreEnseignants(long v){r.nombreEnseignants=v;return this;}
        public Builder nombreModules(long v){r.nombreModules=v;return this;}
        public Builder nombreFilieres(long v){r.nombreFilieres=v;return this;}
        public Builder nombreExamens(long v){r.nombreExamens=v;return this;}
        public Builder nombreInscriptions(long v){r.nombreInscriptions=v;return this;}
        public StatsResponse build(){return r;}
    }
}
