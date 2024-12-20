package at.technikumwien.workerservice.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OCRService {

    private final ITesseract tesseract;

    @Value("${spring.ocr.temp-folder}")
    private String tempFolder;

    public OCRService(ITesseract tesseract) {
        this.tesseract = tesseract;
    }

    public String performOCR(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        PDFDocument pdfDocument = new PDFDocument();
        pdfDocument.load(file);

        SimpleRenderer renderer = new SimpleRenderer();
        List<Image> images = renderer.render(pdfDocument);

        List<BufferedImage> bufferedImages = convertToBufferedImages(images);
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
}
