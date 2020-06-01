# Real Estate Statistics API

This program parses the provided CSV file containing information about real estate sales in California and creates a easy to use API to consume the information. The API was created using Spring Boot and listens on localhost port 8080. The endpoint */housing-statistics* provides access to the data.

----
## Query Parameters
**statistic (required)**  
- min  
- max  
- average  
- sum  
- range

**field (required)**
- price  
- squareFootage

**startDate (optional)**  
**endDate (optional)**  
**zipCode (optional)**

---
## Example Request
*localhost:8080/housing-statistics?statistic=average&field=price&zipCode=12345*
