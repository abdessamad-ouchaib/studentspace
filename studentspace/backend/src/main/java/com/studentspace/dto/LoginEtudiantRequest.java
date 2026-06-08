package com.studentspace.dto;
public class LoginEtudiantRequest {
    private String numeroApogee;
    public LoginEtudiantRequest() {}
    public LoginEtudiantRequest(String numeroApogee) { this.numeroApogee = numeroApogee; }
    public String getNumeroApogee() { return numeroApogee; }
    public void setNumeroApogee(String v) { this.numeroApogee = v; }
}
