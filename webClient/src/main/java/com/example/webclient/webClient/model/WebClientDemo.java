package com.example.webclient.webClient.model;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientDemo implements CommandLineRunner {
    @Override
    public void run(String... args) {

        WebClient webClient = WebClient.create("https://reqres.in/");

        UserDTO user = webClient
                .get()
                .uri("api/users/2")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();

        System.out.println(user);
    }
}
