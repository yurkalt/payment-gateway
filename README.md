# payment-gateway

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

## Running the application locally

- Create the file `/tmp/audit.json`   for storing payments audit or change `audit.path property if to use existing file

- Execute the `com.example.PaymentGatewayApplication` class from your IDE. 

- API call examples ae in `src/main/resources/requests.http`

- To view db results go to -> http://localhost:8080/h2 with sa/password credentials  
