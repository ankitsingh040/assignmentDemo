package com.afkl.cases.df.rest;

import com.afkl.cases.df.dto.FareResultDTO;
import com.afkl.cases.df.fares.Fare;
import com.afkl.cases.df.fares.Location;
import com.afkl.cases.df.oauth.OAuth2Client;
import com.afkl.cases.df.utils.GeneralConstants;
import com.afkl.cases.df.utils.PropertiesLoader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/fares/{origin}/{destination}")
public class FareRestController {
    private String url;

    @RequestMapping(method=GET)
    public FareResultDTO getFare(@PathVariable("origin") String origin,
                                 @PathVariable("destination") String destination) throws Exception {

        //Load the properties file
        Properties config = PropertiesLoader.getClientConfigProps(GeneralConstants.CONFIG_URL);
        url = config.getProperty(GeneralConstants.BASE_URL);

        // Retrieve token
        String token = new OAuth2Client().getToken();

        if (token != null) {
            RestTemplate restTemplate = new RestTemplate();
            Fare fare = restTemplate.getForObject(url
                    + "fares/"
                    + origin + "/"
                    + destination
                    + "?access_token=" + token
                    , Fare.class);
            
            FareResultDTO result = new FareResultDTO();
            result.setFare(fare);

            return result;
        }
        return null;
    }

    private Fare getFare(String origin, String destination, String token) {
        RestTemplate restTemplate = new RestTemplate();
        Fare fare = restTemplate.getForObject(url
                + "fares/"
                + origin + "/"
                + destination
                + "?access_token=" + token
                , Fare.class);
        return fare;
    }

    private Location getLocation(String locationCode, String token) {
        RestTemplate restTemplate = new RestTemplate();
        Location location = restTemplate.getForObject(url
                + "airports/"
                + locationCode
                + "?access_token=" + token
                , Location.class);
        return location;
    }
}
