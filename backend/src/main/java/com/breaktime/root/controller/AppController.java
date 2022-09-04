package com.breaktime.root.controller;
import com.breaktime.root.service.MessageService;
import nl.martijndwars.webpush.Subscription;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
public class AppController {

    @Autowired
    MessageService MessageService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/getnotif",method = RequestMethod.GET)
    public void fireNotif(){
        MessageService.sendNotifications();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/subscribe" ,method = RequestMethod.POST)
    public void postBody(@RequestBody Subscription userSubscription) {

        System.out.println("Inside post handler method");
        System.out.println("User subscription body-");

        System.out.println(userSubscription.toString());
        MessageService.subscribe(userSubscription);


    }
    @GetMapping("/hello")
	public String sayHello() {
		return "Hello World!!";
	}



}
