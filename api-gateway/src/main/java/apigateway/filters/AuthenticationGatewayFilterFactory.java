package apigateway.filters;

import org.apache.http.HttpHeaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    public AuthenticationGatewayFilterFactory(WebClient.Builder webClient, RouteValidator validator) {
        super(Config.class);
        this.webClient = webClient;
        this.validator = validator;
    }

    private final WebClient.Builder webClient;
    private final RouteValidator validator;
    Logger logger = LogManager.getLogger(AuthenticationGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            logger.info("Checking if its open ENDPOINT");

            if (validator.isSecured.test(exchange.getRequest())) {
                logger.info("Applying AuthenticationFilter for URI: {}", exchange.getRequest().getURI());
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.error("No authorization Header");
                    return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
                }

                logger.debug("Extracting Authorization Header from request");
                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);

                logger.debug("Extracting JWT from Authorization Header");
                String[] parts = authHeader.split(" ");

                if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                    logger.error("No Bearer Token");
                    return onError(exchange, "No Bearer Token", HttpStatus.UNAUTHORIZED);
                }

                logger.info("Calling for authentication service for token validation");
                return webClient.build().get().uri("http://authentication-service/api/auth/validate?token=" + parts[1]).retrieve().onStatus(HttpStatusCode::is4xxClientError, response -> {
                    logger.error("Invalid Token");
                    return Mono.error(new RuntimeException("Client error occurred"));
                }).onStatus(HttpStatusCode::is5xxServerError, response -> {
                    logger.error("Authentication Service Server Error");
                    return Mono.error(new Exception("Client error occurred"));
                }).bodyToMono(Void.class).then(chain.filter(exchange)).onErrorResume(RuntimeException.class, e -> {
                    return onError(exchange, "Invalid Token", HttpStatus.UNAUTHORIZED);
                }).onErrorResume(Exception.class, e -> {
                    return onError(exchange, "Invalid Token", HttpStatus.BAD_REQUEST);
                });
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(status);
        byte[] responseBody = error.getBytes(StandardCharsets.UTF_8);

        return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody)));
    }

    public static class Config {
    }
}
