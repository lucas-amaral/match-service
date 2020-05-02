package com.proposta.aceita.matchservice.services.integrations.clients;

import com.proposta.aceita.matchservice.entities.Negotiation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "${services.notification.name}", url = "${services.notification.url}", decode404 = true)
public interface NotificationClient {

    @PostMapping("/emails/match-to-seller")
    void sendMatchEmail(@RequestHeader("Authorization") String authHeader, @RequestBody Negotiation body);

}
