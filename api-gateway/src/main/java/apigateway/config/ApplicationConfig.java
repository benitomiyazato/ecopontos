package apigateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
        System.out.println("CRIANDO WEB CLIENT");
        return WebClient.builder();
    }
}
