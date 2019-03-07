package com.afkl.cases.df.dto;

import com.afkl.cases.df.fares.Fare;
import com.afkl.cases.df.fares.Location;

public class FareResultDTO {
    private Location origin;
    private Location destination;
    private Fare fare;

    public FareResultDTO() {}

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }
}
