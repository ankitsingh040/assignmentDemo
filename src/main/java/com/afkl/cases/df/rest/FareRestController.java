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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
            // Perform parallel requests to the mock service
            List<Location> locations = new ArrayList<>();
            List<Fare> fares = new ArrayList<>();

            ExecutorService pool = Executors.newFixedThreadPool(3);

            List<Callable<Fare>> fareTasks = new ArrayList<>();
            List<Callable<Location>> locationTasks = new ArrayList<>();

            fareTasks.add(() -> getFare(origin, destination, token));
            locationTasks.add(() -> getLocation(origin, token));
            locationTasks.add(() -> getLocation(destination, token));

            List<Future<Fare>> fareResults = pool.invokeAll(fareTasks);
            List<Future<Location>> locationResults = pool.invokeAll(locationTasks);

            for (Future<Fare> future : fareResults) {
                fares.add(future.get());
            }
            for (Future<Location> future : locationResults) {
                locations.add(future.get());
            }
            pool.shutdown();

            // populate the result DTO object
            FareResultDTO result = new FareResultDTO();
            result.setFare(fares.get(0));
            result.setOrigin(locations.get(0));
            result.setDestination(locations.get(1));

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
