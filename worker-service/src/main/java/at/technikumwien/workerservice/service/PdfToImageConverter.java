package at.technikumwien.workerservice.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfToImageConverter {
    public static List<byte[]> convertPdfToImages(byte[] pdfBytes) throws IOException {
        List<byte[]> imageBytesList = new ArrayList<>();

        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            PDFRenderer renderer = new PDFRenderer(document);

            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300); // Render at 300 DPI

                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(image, "PNG", baos); // Convert image to byte array in PNG format
                    imageBytesList.add(baos.toByteArray());
                }
            }
        }

        return imageBytesList;
    }

}
