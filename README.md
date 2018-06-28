**Software Developer Assessment**
-

*Considerations*

  - Spring Boot, Spring MVC and Spring Data were used to provide the solution.
  - Payment model has been reduced from http://mockbin.org/bin/41ca3269-d8c4-4063-9fd5-f306814ff03f in order to simplify the model but taking the key atributes to show the flow of the API.

*Run*

Run the jar at first, from the console:

- java -jar payments-1.0.jar

*Services - API*

- http://localhost:8080/payments/{paymentID}

  - GET: Retrieves a JSON for the payment requested
  - DELETE: Deletes an existing payment.

- http://localhost:8080/payments

  - GET: Retrieves a JSON containing all the payments
  - POST: Creates/Persist a new payment.
  - PUT: Updates an existing payment.
