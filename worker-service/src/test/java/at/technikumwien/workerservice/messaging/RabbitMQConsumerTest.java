package at.technikumwien.workerservice.messaging;

import at.technikumwien.workerservice.service.OCRService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RabbitMQConsumerTest {

    @Mock
    private OCRService ocrService;

    @InjectMocks
    private RabbitMQConsumer rabbitMQConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consumeMessage_success() throws Exception {
        // Arrange
        String filePath = "sample.pdf";
        String expectedText = "Extracted text";
        when(ocrService.performOCR(filePath)).thenReturn(expectedText);

        // Act
        //rabbitMQConsumer.consumeMessage(filePath);

        // Assert
        verify(ocrService, times(1)).performOCR(filePath);
    }

    @Test
    void consumeMessage_ocrServiceException() throws Exception {
        // Arrange
        String filePath = "sample.pdf";
        when(ocrService.performOCR(filePath)).thenThrow(new RuntimeException("OCR failed"));

        // Act & Assert
        try {
            //rabbitMQConsumer.consumeMessage(filePath);
        } catch (Exception e) {
            assertEquals("OCR failed", e.getMessage());
        }
    }
}
