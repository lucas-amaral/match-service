package com.proposta.aceita.matchservice.services.integrations.clients;

import com.proposta.aceita.matchservice.entities.Negotiation;
import com.proposta.aceita.matchservice.entities.NegotiationApprovedBySeller;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "${services.notification.name}", url = "${services.notification.url}", decode404 = true)
public interface NotificationClient {

    @PutMapping("/emails/match-to-seller")
    void sendMatchEmailForSeller(@RequestHeader("Authorization") String authHeader, @RequestBody Negotiation body);

    @PutMapping("/emails/match-to-buyer")
    void sendMatchEmailForBuyer(@RequestHeader("Authorization") String authHeader, @RequestBody Negotiation body);

    @PutMapping("/emails/deal")
    void dealEmail(@RequestHeader("Authorization") String authHeader, @RequestBody NegotiationApprovedBySeller body);

}
