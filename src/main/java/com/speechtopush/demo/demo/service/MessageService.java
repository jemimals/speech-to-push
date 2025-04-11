package com.speechtopush.demo.demo.service;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;
   // @Value("${spring.ai.openai.api-key}")
  //  private String apiKey;
    @Autowired
    private Environment env;



    public MessageService(OpenAiAudioSpeechModel openAiAudioSpeechModel) {
        this.openAiAudioSpeechModel = openAiAudioSpeechModel;
    }

    public byte [] getWavResponse(String msg) {

       return convertToWav(msg);

    }

    public String getMessage(String message) {

        return "Please generate a short and snappy soundbite in English, without any emojis, that summarises the following message in an engaging way: " + message;

    }

    public byte [] convertToWav(String p) {



        OpenAiAudioApi a = OpenAiAudioApi.builder()
                .apiKey(env.getProperty("spring.ai.openai.api-key"))
                .build();

        OpenAiAudioSpeechModel m = new OpenAiAudioSpeechModel(a);

        OpenAiAudioSpeechOptions o = OpenAiAudioSpeechOptions.builder()
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.WAV)
                .speed(1.0f)
              //  .model(OpenAiAudioApi.TtsModel)
                .build();
       // SpeechResponse speechResponse

        SpeechPrompt speechPrompt = new SpeechPrompt(p, o);

        SpeechResponse s = m.call(speechPrompt);

        return s.getResult().getOutput();


    }

    public void saveFile() {


    }

    public void buildResponse() {


    }

}
