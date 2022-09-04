package com.breaktime.root;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;


@Component
public class Sender {

    public void sendPushMessage(Subscription sub, byte[] payload) throws GeneralSecurityException, JoseException, IOException, ExecutionException, InterruptedException {

        Notification notification;
        PushService pushService;

            // Create a notification with the endpoint, userPublicKey from the subscription and a custom payload
            notification = new Notification(
                    sub.getEndpoint(),
                    sub.getUserPublicKey(),
                    sub.getAuthAsBytes(),
                    payload
            );

            // Instantiate the push service, no need to use an API key for Push API
            pushService = new PushService();


        // Send the notification
        pushService.send(notification);
    }

}
