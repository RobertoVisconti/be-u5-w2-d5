package robertovisconti.be_u5_w2_d5.configs;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {
    @Bean
    public Cloudinary getCloudinaryUploader(@Value("${cloudinary.name}") String cloudName,
                                            @Value("${cloudinary.key}") String apiKey,
                                            @Value("${cloudinary.secret}") String secret) {

        System.out.println("IL MIO CLOUD NAME E': " + cloudName);

        
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", secret);

        return new Cloudinary(config);
    }
}
