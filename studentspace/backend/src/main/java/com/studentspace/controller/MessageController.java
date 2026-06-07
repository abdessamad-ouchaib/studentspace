package com.studentspace.controller;

import com.studentspace.dto.MessageRequest;
import com.studentspace.dto.MessageResponse;
import com.studentspace.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://studentspace01.vercel.app"})
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<MessageResponse>> parModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(messageService.parModule(moduleId));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> envoyer(
            @RequestBody MessageRequest req,
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(messageService.envoyer(req, userId));
    }
}
