package com.proposta.aceita.matchservice.services.integrations;

import com.proposta.aceita.matchservice.entities.Negotiation;
import com.proposta.aceita.matchservice.entities.NegotiationApprovedBySeller;
import com.proposta.aceita.matchservice.services.integrations.clients.NotificationClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;

@Service
public class NotificationService extends IntegrationService {

    @Value("${services.notification.username}")
    private String username;

    @Value("${services.notification.password}")
    private String password;

    private final NotificationClient notificationClient;

    private String authHeader;

    public NotificationService(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    @PostConstruct
    void postConstruct() {
        final String auth = username + ":" + password;
        authHeader = "Basic " + Base64Utils.encodeToString(auth.getBytes());
    }

    public void sendMatchEmailForSeller(Negotiation negotiation) {
        notificationClient.sendMatchEmailForSeller(authHeader, negotiation);
    }

    public void sendMatchEmailForBuyer(Negotiation body) {
        notificationClient.sendMatchEmailForBuyer(authHeader, body);
    }

    public void sendDealEmail(NegotiationApprovedBySeller body) {
        notificationClient.dealEmail(authHeader, body);
    }
}