package org.example.aiimagetext_summarizer.config;

import com.google.cloud.vertexai.VertexAI;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

//    @Bean
//    public VertexAI vertexAi(
//            @Value("${spring.ai.vertex.ai.gemini.project-id}") String projectId,
//            @Value("${spring.ai.vertex.ai.gemini.location}") String location) {
//        return new VertexAI(projectId, location);
//    }
//
//    @Bean
//    public ChatModel vertexAiGeminiChatModel(VertexAI vertexAi) {
//        return new VertexAiGeminiChatModel(vertexAi,
//                VertexAiGeminiChatOptions.builder()
//                        .model("gemini-1.5-pro-001")
//                        .build());
//    }
}