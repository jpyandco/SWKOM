package at.technikumwien.workerservice.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;
import org.springframework.stereotype.Service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class OCRService {

    private final ITesseract tesseract;

    public OCRService(ITesseract tesseract) {
        this.tesseract = tesseract;
    }

    public String performOCR(byte[] documentData) throws Exception {
        List<BufferedImage> bufferedImages = convertPdfToImages(documentData);
        return extractTextFromImages(bufferedImages);
    }

    private List<BufferedImage> convertPdfToImages(byte[] pdfBytes) throws Exception {
        List<BufferedImage> bufferedImages = new ArrayList<>();
        PDFDocument pdfDocument = new PDFDocument();
        pdfDocument.load(new ByteArrayInputStream(pdfBytes));

        SimpleRenderer renderer = new SimpleRenderer();
        List<Image> images = renderer.render(pdfDocument);

        for (Image image : images) {
            BufferedImage bufferedImage = new BufferedImage(
                    image.getWidth(null),
                    image.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB
            );
            bufferedImage.getGraphics().drawImage(image, 0, 0, null);
            bufferedImages.add(bufferedImage);
        }

        return bufferedImages;
    }

    private String extractTextFromImages(List<BufferedImage> images) throws TesseractException {
        StringBuilder extractedText = new StringBuilder();
        for (BufferedImage image : images) {
            extractedText.append(tesseract.doOCR(image)).append("\n");
        }
        return extractedText.toString();
    }
}
