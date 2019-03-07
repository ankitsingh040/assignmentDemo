angular.module('SimpleApp')
.factory('FareService', ['$resource', function($resource) {
            var baseResourceUrl = "../travel/";
            return {
                GetFare: $resource(baseResourceUrl + "fares/:origin/:destination",
                    {origin : '@origin', destination : '@destination'},
                    {get: {method: 'GET', isArray: false}
                }),
                GetAirports: $resource(baseResourceUrl + "airports",
                    {},
                    {get: {method: 'GET', isArray: false}
                }),
                SearchAirports: $resource(baseResourceUrl + "airports/:name",
                    {name: '@name'},
                    {get: {method: 'GET', isArray: false}
                })
            }

        }
 ]);