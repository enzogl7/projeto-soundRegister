package com.ogl.soundregister.service;



import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.stereotype.Service;

@Service
public class ConsultaChatGpt {
    public static String obterInformacao(String texto) {

        ChatLanguageModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey("AIzaSyBpS1tFTnmeWpOecNz0ClOiTi8yHNUPQ6w")
                .modelName("gemini-1.5-flash")
                .topK(40)
                .build();


        String response = gemini.generate("Me fale sobre o artista musical brevemente: " + texto);
        return response;
    }
}
