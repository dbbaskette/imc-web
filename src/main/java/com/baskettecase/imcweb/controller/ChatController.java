package com.baskettecase.imcweb.controller;

import com.baskettecase.imcweb.model.ChatMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class ChatController {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/chat/stream")
    public SseEmitter stream() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));

        try {
            emitter.send(SseEmitter.event().name("connected").data("Connection established"));
        } catch (IOException e) {
            this.emitters.remove(emitter);
        }

        return emitter;
    }

    @PostMapping("/chat/message")
    public void sendMessage(@RequestBody ChatMessage message) {
        for (SseEmitter emitter : this.emitters) {
            try {
                emitter.send(SseEmitter.event().name("message").data(message));
            } catch (IOException e) {
                // This emitter is likely dead, remove it
                this.emitters.remove(emitter);
            }
        }
    }
}
