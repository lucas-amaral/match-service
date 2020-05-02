package com.proposta.aceita.matchservice.configurations;

import com.proposta.aceita.matchservice.exceptions.RequestException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestErrorHandling requestErrorHandling() {
        return new RequestErrorHandling();
    }

    public static class RequestErrorHandling implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
             return new RequestException(response.status(), response.request().url(), response.reason());
        }
    }
}
