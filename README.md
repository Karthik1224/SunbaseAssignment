# SunbaseAssignment

Backend Architecture: Java, Spring Boot, Spring Data JPA, Spring Security, MySql, Postman(testing apis), Eclipse(IDE for development)

Modal             : It contains java objects
Controller        : It contains API end points
Dao/Repository    : It contains database related logic and other queries 
Service           : It contains bussiness logic and everything regarding functionality
Configuration     : It contains some configurations which needed to consume remote api , other stuff related to security and allowing frontend to access those api end points
DTOs              : It contains request and response dtos, which we send as request payload or get as response payload, to protect our objects

APIs:

add customer
update customer
fetch single customer based on id
delete customer based on phone number
fetch all customers with sorted order limited data at time (utilised pagination)
consume customer data from external api and save it to database
sign up (saving login details email and password in database)
sign in (verifying requested user details with the details exist in database, followed by returning jwt token)


Authentication & Authorization: JWT Token

When user sign in with credentails (email,password) , will verify those credentails with credentails exist in database, if those exist, will return a jwt token as response else 
Have to provide correct credentails or register. 
User will send that jwt token in each request to access resource of the application, if there is no token, user cannot access resources of the application.


Frontend: Html5, CSS3, JavaScript, VS Code(Development)

Login Page 
CustomerList Page
Adding customer/editing customer uses same form
Utilised Event Listeners of JavaScript to perform action when click on button
Utilised fetch api to make requests to the backend
JwtToken is stored in local storage




