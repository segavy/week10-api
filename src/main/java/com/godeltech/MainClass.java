package com.godeltech;

//import org.apache.http.HttpRequest;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class MainClass {



    public static void main(String[] args) throws IOException, InterruptedException {
//        HttpResponse getResponse = Util.sendGetRequest("http://www.httpbin.org/get");
//        HttpResponse postResponse = Util.sendPostRequest("http://www.httpbin.org/post");

//        System.out.println(getResponse.getStatusLine());
//        System.out.println();
//        System.out.println(postResponse.getStatusLine());
//        System.out.println();
//        System.out.println(Util.returnBodyOfPostRequest("http://www.httpbin.org/post"));

        // Customer ID
        final String customerKey = "Your customer ID";
        // Customer secret
        final String customerSecret = "Your customer secret";

        // Concatenate customer key and customer secret and use base64 to encode the concatenated string
        String plainCredentials = customerKey + ":" + customerSecret;
        String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
        System.out.println("base64 credentials : " + base64Credentials);
        // Create authorization header
        String authorizationHeader = "Basic " + base64Credentials;

        HttpClient client = HttpClient.newHttpClient();

        // Create HTTP request object
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://www.httpbin.org/basic-auth/user/pass"))           // ("https://api.agora.io/dev/v1/projects"))
                .GET()
                .header("Authorization", authorizationHeader)
                .header("Content-Type", "application/json")
                .build();
        // Send HTTP request
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println("Res:" + response.statusCode());
        System.out.println("body: " + response.body());
    }

}
