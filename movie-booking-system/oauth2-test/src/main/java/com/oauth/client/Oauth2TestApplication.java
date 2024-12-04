package com.oauth.client;

import java.util.Arrays;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
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
import org.springframework.web.client.RestTemplate;

import com.oauth.model.TokenResponse;

@SpringBootApplication
public class Oauth2TestApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(Oauth2TestApplication.class);
	static final String KEYCLOAK_TOKEN_URL = "http://localhost:8080/realms/demo/protocol/openid-connect/token";
	//static final String KEYCLOAK_TOKEN_URL = "http://localhost:8080/auth/realms/My-Realm/protocol/openid-connect/token";
	static final String GET_MOVIE_LIST = "http://localhost:8082/movie/getMovie";
	static final String GET_THEATER_LIST = "http://localhost:8082/theaters/api/theatres";

	public static void main(String[] args) {
		SpringApplication.run(Oauth2TestApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Override
	public void run(String... args) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_FORM_URLENCODED }));
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("username", "bhupendra");
		requestBody.add("password", "admin");
		requestBody.add("client_id", "api-gateway");
		requestBody.add("client_secret", "2WRlLniLnkasSGfIPeBF0yaFCFoAq8ty");
		requestBody.add("grant_type", "password");

		log.info("Calling Token API");
		HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
		ResponseEntity<TokenResponse> keycloakResponse = restTemplate.exchange(KEYCLOAK_TOKEN_URL, HttpMethod.POST,formEntity, TokenResponse.class);
		String token = keycloakResponse.getBody().getAccess_token();
		String refreshToken = keycloakResponse.getBody().getRefresh_token();
		log.info("Access Token =:" + token);
		//MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		
		log.info("Calling Get Theater List API");
		HttpHeaders theaterHeader = new HttpHeaders();
		theaterHeader.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		theaterHeader.setContentType(MediaType.APPLICATION_JSON); //
		theaterHeader.set("Authorization", "Bearer " + token);
		HttpEntity<String> httpEntity = new HttpEntity<String>(theaterHeader);
		ResponseEntity<String> theaterListresponse = restTemplate.exchange(GET_THEATER_LIST, HttpMethod.GET, httpEntity, String.class);
		String theaterList = theaterListresponse.getBody();
		log.info("Get Theater List API RESPONSE:::" + theaterList);
/*
		log.info("Calling Get Movie List API");
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		header.setContentType(MediaType.APPLICATION_JSON); //
		header.set("Authorization", "Bearer " + token);
		HttpEntity<String> entity = new HttpEntity<String>(header);
		ResponseEntity<String> movieListresponse = restTemplate.exchange(GET_MOVIE_LIST, HttpMethod.GET, entity, String.class);
		String result = movieListresponse.getBody();
		log.info("Get Movie List API RESPONSE:::" + result);
		
		log.info("Calling refresh  Token API");
		request.add("client_id", "spring-gateway-client");
		request.add("client_secret", "3RhEF8pqKTANrQ6BhfxaYVmcjTXsDK0u");
		request.add("refresh_token", refreshToken);
		request.add("grant_type", "refresh_token");

	
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(request, headers);
		ResponseEntity<TokenResponse> response = restTemplate.exchange(KEYCLOAK_TOKEN_URL, HttpMethod.POST, requestEntity, TokenResponse.class);
		String newToken = response.getBody().getAccess_token();
		log.info("New Access Token =:" + newToken);


		
		*/
		System.exit(0);
		

	}
}