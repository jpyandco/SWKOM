package at.technikumwien.workerservice.service;

import at.technikumwien.workerservice.entities.Document;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OCRService {

    private final ITesseract tesseract;


    @Autowired
    private static PdfToImageConverter pdfToImageConverter;

    @Value("${spring.ocr.temp-folder}")
    private String tempFolder;

    public OCRService(ITesseract tesseract) {
        this.tesseract = tesseract;
    }

    public void processDocument(String title, String author, byte[] fileBytes) throws TesseractException, IOException {
        // Perform OCR on the byte array (e.g., PDF file)
        String extractedText = extractTextFromBytes(fileBytes);  // Implement the OCR logic here using Tesseract or another library

        // Create a new document entity and set the extracted values
        Document document = new Document();
        document.setTitle(title);
        document.setAuthor(author);
        document.setData(fileBytes);

        // Save to Elasticsearch
       //documentRepository.save(document);
    }

    public String performOCR(byte[] document) throws Exception {
        List<BufferedImage> bufferedImages = null;
        System.out.println("OCR");
        try {
            PDFDocument pdfDocument = new PDFDocument();
            pdfDocument.load(new ByteArrayInputStream(document));

            SimpleRenderer renderer = new SimpleRenderer();
            List<Image> images = renderer.render(pdfDocument);

            bufferedImages = convertToBufferedImages(images);
            System.out.println("OCR try");

        }
        catch (Exception e){
            System.out.println("Exception: " + e);
        }
        return extractTextFromImages(bufferedImages);
    }

    private List<BufferedImage> convertToBufferedImages(List<Image> images) {
        List<BufferedImage> bufferedImages = new ArrayList<>();

        for (Image image : images) {
            if (image instanceof BufferedImage) {
                bufferedImages.add((BufferedImage) image);
            } else {
                BufferedImage bufferedImage = new BufferedImage(
                        image.getWidth(null),
                        image.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB
                );
                bufferedImage.getGraphics().drawImage(image, 0, 0, null);
                bufferedImages.add(bufferedImage);
            }
        }

        return bufferedImages;
    }

    private String extractTextFromImages(List<BufferedImage> bufferedImages) throws TesseractException {
        StringBuilder extractedText = new StringBuilder();

        for (BufferedImage bufferedImage : bufferedImages) {
            String text = tesseract.doOCR(bufferedImage);
            extractedText.append(text).append("\n");
        }

        return extractedText.toString();
    }

    public String extractTextFromBytes(byte[] fileBytes) throws IOException, TesseractException {
        File tempFile = File.createTempFile("uploaded", ".tmp");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(fileBytes);
        }

        try {
            return tesseract.doOCR(tempFile);
        } finally {
            // Delete the temporary file
            tempFile.delete();
        }
    }

}
