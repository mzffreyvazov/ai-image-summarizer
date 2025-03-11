# AI Image Text Summarizer

A Spring Boot application that extracts text from images using OCR (Optical Character Recognition) and provides AI-powered summaries using Google's Vertex AI Gemini model.

## Demo

https://github.com/user-attachments/assets/28a17aa7-fae8-468a-b273-c17b182f369a

## Features

- Upload multiple images via drag-and-drop or file browser
- Extract text from images using Tesseract OCR
- Summarize extracted text using Google Vertex AI Gemini
- Real-time preview of uploaded images
- Markdown-formatted summary output
- Modern and responsive UI

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Tesseract OCR installed on your system
- Google Cloud account with Vertex AI API enabled
- Google Cloud project credentials configured

## Installation

1. Install Tesseract OCR:
   ```bash
   # Windows (using chocolatey)
   choco install tesseract

   # Linux
   sudo apt-get install tesseract-ocr
   ```

2. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/AI-Image-Text_Summarizer.git
   cd AI-Image-Text_Summarizer
   ```

3. Configure Google Cloud credentials:
   - Set up a Google Cloud project
   - Enable Vertex AI API
   - Update `application.properties` with your project details:
     ```properties
     spring.ai.vertex.ai.gemini.project-id=your-project-id
     spring.ai.vertex.ai.gemini.location=your-location
     spring.ai.vertex.ai.gemini.chat.options.model=gemini-1.5-pro-001
     ```

4. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

## Usage

1. Open your browser and navigate to `http://localhost:8080`
2. Upload images by either:
   - Dragging and dropping images onto the upload area
   - Clicking "Browse Files" to select images
3. Click "Summarize Images" to process the uploaded images
4. View the AI-generated summary in markdown format

## Technology Stack

- **Backend:**
  - Spring Boot 3.4.2
  - Spring AI
  - Tesseract OCR (via Tess4J)
  - Google Vertex AI Gemini

- **Frontend:**
  - HTML5
  - CSS3
  - JavaScript
  - Marked.js for Markdown rendering

## Configuration

### Tesseract OCR Path
By default, the application looks for Tesseract at:
```java
tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
```

Modify the path in `OcrController.java` if your Tesseract installation is in a different location.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Acknowledgments

- [Spring AI](https://docs.spring.io/spring-ai/reference/index.html) for AI integration
- [Tesseract OCR](https://github.com/tesseract-ocr/tesseract) for text extraction
- [Google Vertex AI](https://cloud.google.com/vertex-ai) for AI services
