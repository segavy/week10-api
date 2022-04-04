package com.godeltech;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Util {

    public static int getStatusCodeForGetRequest(String url) throws IOException, URISyntaxException {
        HttpResponse response = Util.sendGetRequest(url);
        return response.getStatusLine().getStatusCode();
    }



    public static HttpResponse sendGetRequest(String url) throws IOException, URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.custom().build();  //создание клиента, который будет совершать все действия
        URI uri = new URI(url);
        HttpGet httpGet = new HttpGet(uri);

//        HttpClientContext httpClientContext = HttpClientContext.create(); // если сервис не требует контекст, то создаем по умолчанию.
//        System.out.println(httpGet.getURI());

        System.out.println(httpGet.getRequestLine());
        HttpResponse response = httpClient.execute(httpGet);

        //        HttpResponse response = httpClient.execute(httpGet, httpClientContext);
//        System.out.println(response.getStatusLine());
//        System.out.println(returnBodyOfGetRequest(url));
        return response;
    }

    public static String returnBodyOfPostRequest(String url) throws IOException, URISyntaxException {
        URI uri = new URI(url);
        CloseableHttpClient httpClient = HttpClients.custom().build();
        HttpPost httpPost = new HttpPost(uri);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        return responseString;
    }

    public static String removeXAmznTraceIdParameter(String income) {
        return income.replaceAll("\"X-Amzn-Trace-Id\": \"Root=.*\"", "");
    }

    public static int authenticate(String url, String userName, String password) throws IOException {

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password);
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, credentials);

        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet httpGet = new HttpGet(url);

        System.out.println(httpGet.getConfig());

        System.out.println(httpGet.getRequestLine());
        Header[] h = httpGet.getAllHeaders();
        System.out.println(h.length);
        System.out.println("array is  = " + Arrays.toString(h));

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();

        return statusCode;
    }

//    @Deprecated
//    public Map getRequestHeaderValues()
//    {
//        private HttpServletRequest request;
//        Map map = new HashMap();
//        // get header values from request object
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            map.put(key, value);
//        }
//        return map;
//    }



    public static HttpResponse sendPostRequest(String url) throws IOException, URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.custom().build();
        URI uri = new URI(url);
        HttpPost httpPost = new HttpPost(uri);
        HttpClientContext context = HttpClientContext.create();
        HttpResponse response = httpClient.execute(httpPost, context);
        return response;
    }

    public static String returnBodyOfResponseOfGetRequest(String url) throws IOException, URISyntaxException {

        CloseableHttpClient httpClient = HttpClients.custom().build();
        URI uri = new URI(url);
        HttpGet httpGet = new HttpGet(uri);
//        System.out.println(httpGet.toString());

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        return responseString;
    }

    public static String getParameterFromJson(String input, String parameter) throws ParseException {
        JSONParser parser = new JSONParser();
        Object o = parser.parse(input);
        JSONObject obj = (JSONObject) o;

        String value = (String) obj.get(parameter);

        return value;
    }

    public static String getInnerParameterFromJson(String input, String parameter, String innerParam) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(input);

        JSONObject inner = (JSONObject) obj.get(parameter);
        String value = (String)inner.get(innerParam);

        return value;
    }

    public static long getRowCountOfResponse(String url) throws IOException, URISyntaxException {
        String response = returnBodyOfResponseOfGetRequest(url);
        return response.lines().count();
    }


}
