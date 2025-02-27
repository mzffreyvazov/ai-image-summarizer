package org.example.aiimagetext_summarizer.controllers;

import org.apache.groovy.json.internal.IO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OcrController {
    private final Tesseract tesseract;
    private final PersistenceExceptionTranslationAutoConfiguration persistenceExceptionTranslationAutoConfiguration;

    @Autowired
    @Qualifier("vertexAiGeminiChat")
    private ChatModel chatModel;

    public OcrController(PersistenceExceptionTranslationAutoConfiguration persistenceExceptionTranslationAutoConfiguration) {
        tesseract = new Tesseract();
        tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
        this.persistenceExceptionTranslationAutoConfiguration = persistenceExceptionTranslationAutoConfiguration;
    }

    @GetMapping("/testOcr")
    public String testOcr() {
        String extractedText = "OCR Failed";

        try {
            File imageFile = new File("D:/Downloads/Telegram Desktop/photo_2025-02-13_22-47-26.jpg");

            extractedText = tesseract.doOCR(imageFile);
            System.out.println("Extracted text:\n" + extractedText);

        } catch (TesseractException e) {
            System.err.println("TesseractException: " + e.getMessage());
        }

        return extractedText;
    }

    @PostMapping(value ="/summarizeImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String summarizeImages(@RequestParam("file") List<MultipartFile> imageFiles) {
        StringBuilder combinedText = new StringBuilder();
        StringBuilder extractedTextOut = new StringBuilder();
        StringBuilder summarizedText = new StringBuilder();
        for (MultipartFile imageFile : imageFiles) {
            try {
                // Create temp file with proper extension
                String originalFileName = imageFile.getOriginalFilename();
                String extension = originalFileName != null ?
                        originalFileName.substring(originalFileName.lastIndexOf(".")) : ".png";
                File tempFile = File.createTempFile("img", extension);

                // Copy file
                Path tempFilePath = tempFile.toPath();
                Files.copy(imageFile.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

                // Convert image to compatible format if needed
                BufferedImage image = ImageIO.read(tempFile);
                if (image == null) {
                    throw new IOException("Failed to read image file");
                }

                // Perform OCR
                String extractedText = tesseract.doOCR(image);
                combinedText.append(extractedText).append("\n\n");

                extractedTextOut.append(extractedText).append("\n");
                // Cleanup
                Files.deleteIfExists(tempFilePath);
            } catch (IOException | TesseractException e) {
                System.err.println("Error processing image: " + imageFile.getOriginalFilename() + ", Error: " + e.toString());
                e.printStackTrace();
                combinedText.append("Error processing image: ").append(imageFile.getOriginalFilename()).append("\n\n");
            }
        }
        PromptTemplate promptTemplate = new PromptTemplate("These are the extracted texts (combined) from screenshots I took from various places around the internet. I want you to summarize them into clear logical orginized format (like a typical mail list):\n\n{text}\n\nSummary:");
        promptTemplate.add("text", extractedTextOut);

        ChatResponse response = (ChatResponse) chatModel.call(promptTemplate.create());
        Generation generation = response.getResult();
        String summary = generation.getOutput().getContent();

        combinedText.append("\n**Summary:**\n").append(summary).append("\n\n");
        summarizedText.append("\n**Summary:**\n").append(summary).append("\n\n");

        return summarizedText.toString();
    }
}
