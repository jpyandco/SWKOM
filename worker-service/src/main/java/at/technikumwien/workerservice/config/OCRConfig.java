package at.technikumwien.workerservice.config;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OCRConfig {

    @Bean
    public ITesseract tesseract() {
        return new Tesseract();
    }
}
