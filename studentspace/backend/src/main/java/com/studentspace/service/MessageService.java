package com.studentspace.service;

import com.studentspace.dto.MessageRequest;
import com.studentspace.dto.MessageResponse;
import com.studentspace.model.Message;
import com.studentspace.repository.MessageRepository;
import com.studentspace.repository.ModuleRepository;
import com.studentspace.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepo;
    private final ModuleRepository moduleRepo;
    private final UtilisateurRepository utilisateurRepo;

    public List<MessageResponse> parModule(Long moduleId) {
        return messageRepo.findByModuleIdOrderByDateEnvoiAsc(moduleId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public MessageResponse envoyer(MessageRequest req, Long userId) {
        Message m = Message.builder()
                .contenu(req.getContenu())
                .module(moduleRepo.findById(req.getModuleId()).orElseThrow())
                .expediteur(utilisateurRepo.findById(userId).orElseThrow())
                .build();
        return toResponse(messageRepo.save(m));
    }

    private MessageResponse toResponse(Message m) {
        String nomExpediteur = "Inconnu";
        if (m.getExpediteur() != null) {
            try {
                com.studentspace.model.Etudiant et = (com.studentspace.model.Etudiant) m.getExpediteur();
                nomExpediteur = et.getPrenom() + " " + et.getNom();
            } catch (ClassCastException ex) {
                try {
                    com.studentspace.model.Enseignant ens = (com.studentspace.model.Enseignant) m.getExpediteur();
                    nomExpediteur = ens.getPrenom() + " " + ens.getNom();
                } catch (ClassCastException ex2) {
                    nomExpediteur = m.getExpediteur().getEmail();
                }
            }
        }
        return MessageResponse.builder()
                .id(m.getId())
                .contenu(m.getContenu())
                .expediteurNom(nomExpediteur)
                .expediteurId(m.getExpediteur() != null ? m.getExpediteur().getId() : null)
                .dateEnvoi(m.getDateEnvoi() != null ? m.getDateEnvoi().toString() : null)
                .moduleId(m.getModule() != null ? m.getModule().getId() : null)
                .build();
    }
}
