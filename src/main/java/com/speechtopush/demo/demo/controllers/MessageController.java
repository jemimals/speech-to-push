package com.speechtopush.demo.demo.controllers;

import com.speechtopush.demo.demo.api.MessageRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.speechtopush.demo.demo.service.MessageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/")
@AllArgsConstructor
public class MessageController {

    @Autowired
    private MessageService messageService;

    private final OllamaChatModel ollamaChatModel;

    @Autowired
    public MessageController(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }


    @PostMapping( "/message")
    public String getSoundbite(@RequestBody MessageRequestDto requestDto) throws IOException, InterruptedException {

        String messagePrompt = messageService.getMessage(requestDto.getContext().getMessage());

        String res =  this.ollamaChatModel.call(messagePrompt);

       // new ProcessBuilder("/usr/bin/say", res, "-o", "/tmp/msg.aiff").start().waitFor();

       byte [] wav = messageService.convertToWav(res);

       String id = UUID.randomUUID().toString();

       String path = "/tmp/" + id + ".wav";

        Path p = Paths.get(path);
        Files.write(p, wav);

       return path;

       // return null;

    }
}
