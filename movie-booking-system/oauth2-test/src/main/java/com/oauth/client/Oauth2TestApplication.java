package com.oauth.client;

import java.util.Arrays;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.oauth.model.TokenResponse;

@SpringBootApplication
public class Oauth2TestApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Oauth2TestApplication.class);
    static final String KEYCLOAK_TOKEN_URL = "http://localhost:8080/realms/demo/protocol/openid-connect/token";
    static final String GET_MOVIE_LIST = "http://localhost:8092/movies/api/movies";
    static final String GET_THEATER_LIST = "http://localhost:8092/theaters/api/theatres";

    public static void main(String[] args) {
        SpringApplication.run(Oauth2TestApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        CloseableHttpClient httpClient = HttpClients.custom()
                
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }


    @Override
    public void run(String... args) throws Exception {
        try {
            RestTemplate restTemplate = restTemplate(new RestTemplateBuilder());

            // Request token from Keycloak
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("username", "bhupendra");
            requestBody.add("password", "bhupendra");
            requestBody.add("client_id", "api-gateway");
            requestBody.add("client_secret", "wd54LFM3MiMTQFFdNfNqNKzGk7I2y48q");
            requestBody.add("grant_type", "password");

            log.info("Calling Token API");
            HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<TokenResponse> keycloakResponse = restTemplate.exchange(KEYCLOAK_TOKEN_URL, HttpMethod.POST, formEntity, TokenResponse.class);
            String token = keycloakResponse.getBody().getAccess_token();
            log.info("Access Token =: " + token);

            // Call Get Movie List API
         //   callApi(restTemplate, GET_MOVIE_LIST, token, "Movie List");

            // Call Get Theater List API
            callApi(restTemplate, GET_THEATER_LIST, token, "Theater List");

        } catch (Exception e) {
            log.error("Error occurred while calling API: ", e);
        } finally {
            System.exit(0);
        }
    }

    private void callApi(RestTemplate restTemplate, String url, String token, String apiName) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            header.setContentType(MediaType.APPLICATION_JSON);
            header.set("Authorization", "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>(header);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            String result = response.getBody();
            log.info("Get {} API RESPONSE:: {}", apiName, result);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("HTTP Status Code: {}", e.getStatusCode());
            log.error("Response Body: {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }
}
