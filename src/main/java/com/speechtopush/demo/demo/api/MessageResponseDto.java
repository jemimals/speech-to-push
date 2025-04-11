package com.speechtopush.demo.demo.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponseDto {

    private String status;
    private String filePath;

}
