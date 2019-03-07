package com.afkl.cases.df.rest;

import com.afkl.cases.df.oauth.OAuth2Client;
import com.afkl.cases.df.utils.GeneralConstants;
import com.afkl.cases.df.utils.PropertiesLoader;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/airports/{code}")
public class LocationController {
    private String url;

    @RequestMapping(method=GET)
    public Object getAirports(@PathVariable("code") String code) {
        //Load the properties file
        Properties config = PropertiesLoader.getClientConfigProps(GeneralConstants.CONFIG_URL);
        url = config.getProperty(GeneralConstants.BASE_URL);

        HttpHeaders headers = new HttpHeaders();
       
        
        try {
            // Retrieve token
            String token = new OAuth2Client().getToken();

            headers.set("Authorization", "Bearer "+token);
          //  headers.set("token_type", "bearer");
            
            HttpEntity entity = new HttpEntity(headers);
            
            if (token != null) {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.exchange(
                	    url+ "/airports/"
                                + "?term=" + code, HttpMethod.GET, entity, String.class);
                
                Object airports = restTemplate.getForObject(url
                        + "/airports/"
                        + "?term=" + code
                        //+ "&access_token=" + token
                        , Object.class);

                return airports;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
