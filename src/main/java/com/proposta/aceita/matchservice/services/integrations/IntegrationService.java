package com.proposta.aceita.matchservice.services.integrations;


import com.proposta.aceita.matchservice.exceptions.RequestException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class IntegrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationService.class);

    <R> R request(Supplier<R> requestHandler) {

        try {
            return requestHandler.get();
        }
        catch (final RequestException e) {
            LOGGER.error(e.getMessage());
        }
        catch (final FeignException e) {
            LOGGER.error("Failed request with status:" + e.status() +". Message: " + e.getLocalizedMessage());
        }
        return (R) Optional.empty();
    }

}
