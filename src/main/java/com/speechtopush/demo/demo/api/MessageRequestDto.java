package com.speechtopush.demo.demo.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageRequestDto {

    private SpeechContext context;


    @Data
    @Builder
    public static class SpeechContext {

        private String message;

    }

}
