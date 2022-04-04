package com.godeltech;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class UtilTest {

    @Test
    public void checkCodeForGetRequest() throws IOException, URISyntaxException, ParseException {
        String url = "http://www.httpbin.org/get";
        int expectedCode = 200;
        int actualCode = Util.getStatusCodeForGetRequest(url);
        String actualUrl = Util.getParameterFromJson(Util.returnBodyOfResponseOfGetRequest(url), "url");
//        System.out.println(Util.getParameterFromJson(Util.returnBodyOfResponseOfGetRequest(url), "url"));

        Assert.assertEquals(url, actualUrl);
        Assert.assertEquals(expectedCode, actualCode);
    }

    @Test
    public void checkResponseForPostRequest() throws URISyntaxException, IOException, ParseException {

        String url = "http://www.httpbin.org";
        String urlPost = "http://www.httpbin.org/post";
        String expectedHost = "www.httpbin.org";
        String expectedAcceptParam = "application/json";
        String expectedAcceptEncodingParam = "gzip,deflate";

        String response = Util.returnBodyOfPostRequest(urlPost);
        String actualURL = Util.getParameterFromJson(response, "url");
        String actualHostParam = Util.getInnerParameterFromJson(response, "headers", "Host");
        String actualAcceptEncodingParam = Util.getInnerParameterFromJson(response, "headers", "Accept-Encoding");
//        String actualOrigin = Util.getParameterFromJson(response, "origin");

        Assert.assertEquals(urlPost, actualURL);
        Assert.assertEquals(expectedHost, actualHostParam);
        Assert.assertEquals(expectedAcceptEncodingParam, actualAcceptEncodingParam);
    }

    @Test
    public void successfulAuthentication() throws IOException {
        String login = "user";
        String password = "pass";
        String url = "http://www.httpbin.org/basic-auth/" + login + "/" + password;
        int expectedResponse = 200;

        int actualResponse = Util.authenticate(url, login, password);

        Assert.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void unSuccessfulAuthentication() throws IOException {
        String login = "user";
        String password = "pass";
        String url = "http://www.httpbin.org/basic-auth/" + login + "/" + password + "1";
        int expectedResponse = 401;

        int actualResponse = Util.authenticate(url, login, password);

        Assert.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void checkNumberOfStreamsSent() throws IOException, URISyntaxException {
        int countStreams = 4;
        String url = "http://www.httpbin.org/stream/" + countStreams;

        long actualCount = Util.getRowCountOfResponse(url);

        Assert.assertEquals(countStreams, actualCount);
    }
}
