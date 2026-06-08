package com.studentspace.dto;
public class MessageRequest {
    private String contenu; private Long moduleId;
    public MessageRequest() {}
    public String getContenu(){return contenu;} public void setContenu(String v){contenu=v;}
    public Long getModuleId(){return moduleId;} public void setModuleId(Long v){moduleId=v;}
}
