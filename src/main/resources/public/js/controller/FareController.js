'use strict';

angular.module('SimpleApp.controllers', []).
controller('FareController', ['FareService', function(FareService) {
    this.error = null;
    this.requestFare = {
        origin: '',
        destination: ''
    };
    this.result = '';
    this.originSearchResults = [];
    this.destinationSearchResults = [];

     
    this.filterByName = function (airports, typedValue) {
        if (airports == null) {
            return false;
        }

        return airports.filter(function(airport) {

            var matches_name = airport.name.indexOf(typedValue) != -1;
            var matches_code = airport.code.indexOf(typedValue) != -1;
            var matches_desc = airport.description.indexOf(typedValue) != -1;
            return matches_name || matches_code || matches_desc;
        });
    }

    
    this.searchAirports = function(name, type){
        if (name == null || name.length == 0) {
            return;
        }
        var promise = FareService.SearchAirports.get({name : name});
        promise.$promise.then(function (response) {
            if (response._embedded != null && response._embedded.locations != null) {
                if (type == 'Origin') {
                    this.originSearchResults = response._embedded.locations;
                } else {
                    this.destinationSearchResults = response._embedded.locations;
                }
            }
        }.bind(this), function(error) {

        });
    }

    
    this.getFare = function(){
        this.reset();
        if (this.requestFare.origin == this.requestFare.destination) {
            this.error = 'Please select a different Destination';
            return;
        }

        var promise = FareService.GetFare.get(
            {origin: this.requestFare.origin,
            destination: this.requestFare.destination});
        promise.$promise.then(function (response) {
            this.result = response;
        }.bind(this), function(error) {
            this.error = 'Please try again later!';
        }.bind(this));
    }
    
     this.reset = function(){
        this.error = '';
        this.result = '';
    }

}]);
