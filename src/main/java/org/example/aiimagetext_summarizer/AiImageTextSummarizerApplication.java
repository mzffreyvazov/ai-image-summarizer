package org.example.aiimagetext_summarizer;

import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { OpenAiAutoConfiguration.class })
public class AiImageTextSummarizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiImageTextSummarizerApplication.class, args);
	}

}
