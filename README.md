Travel API Client 
=================

**After starting the application**:

To view the assignment go to:

[http://localhost:9000/travel/index.html](http://localhost:9000/travel/index.html)

To search fares for origin/destination go to:

[http://localhost:9000/travel/fares.html](http://localhost:9000/travel/fares.html)

Resource endpoints:
-------------------

**Retrieve a list of airports**:

`http://localhost:9000/airports`

 Query params:

- term: A search term that searches through code, name and description.

**Retrieve a specific airport**:

`http://localhost:9000/airports/{code}`

 params:

- code: the code  of Origin/Destination

**Retrieve a fare offer**:

`http://localhost:9000/fares/{origin_code}/{destination_code}`

 params:

- origin_code: the code of Origin
- destination_code: the code of Destination
 

