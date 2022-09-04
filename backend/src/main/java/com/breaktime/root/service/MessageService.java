package com.breaktime.root.service;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Service
public class MessageService {
    @Value("${VAPID.PUBLIC_KEY}")
    private String publicKey;
    @Value("${VAPID.PRIVATE_KEY}")
    private String privateKey;



    private PushService pushService;
    private List<Subscription> subscriptions = new ArrayList<>();

    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(publicKey, privateKey);
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void subscribe(Subscription subscription) {
        System.out.println("Subscribed to " + subscription.endpoint);
        this.subscriptions.add(subscription);
    }

    public void unsubscribe(String endpoint) {
        System.out.println("Unsubscribed from " + endpoint);
        subscriptions = subscriptions.stream().filter(s -> !endpoint.equals(s.endpoint)).collect(Collectors.toList());
    }

    public void sendNotification(Subscription subscription, String messageJson) {
        System.out.println("sending notif to latest sub");
        try {
            pushService.send(new Notification(subscription, messageJson));
        } catch (GeneralSecurityException | IOException | JoseException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendNotifications() {
        System.out.println("Sending notifications to all subscribers");

         JSONObject obj=new JSONObject();
         obj.put("title","Server says hello!");
         obj.put("body","This is the body");

        System.out.println(obj.toJSONString());
        subscriptions.forEach(subscription -> {
            sendNotification(subscription, obj.toJSONString());
        });
    }
}
