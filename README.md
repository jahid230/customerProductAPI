# customerProductAPI
It's Spring Boot 2 project with protected JWT authentication enable for mutable REST API endpoints.

## Running The Application

./mvnw clean install -DskipTests=true

### If port binding is failed for Postgres then run:

 sudo ss -lptn 'sport = :5432' 
 sudo kill<pid>
  

The application can be run in any system having docker and Postman installed. The application shoud start via the following command:

**docker-compose up**

*ALL the Important Links*

The all the public API documentation for the customer and Product is available on:

http://localhost:8081/api/v1/swagger-ui/index.html

All Publicly accesses APIs are:
## [Get Mapping]
http://localhost:8081/api/v1/customers 
http://localhost:8081/api/v1/customers/{CustomerId}
http://localhost:8081/api/v1/products

All other mutable methods can be accessed via JWT bearer token Authentication.

To create a Jwt Bearer Token the link:

http://localhost:8081/api/v1/login

## The Steps for creating JWT Bearer Token:

### Step 01:Enter the default username and passowrd as "admin" in the following post request:
http://localhost:8081/api/v1/login
### Step 02: Copy the Bearer JWT token from the response and paste the JWT token as "Authorization" Header in Postman Header tab in the following way:
"Bearer <JWT_Token>"
### Step 03: Access all the mutable methods for authenticated by JWT authentication

