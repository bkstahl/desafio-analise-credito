# credit-analyze-challenge

## To run the project:
For the environment, the database "Mysql" is needed, which can be started using docker:

`` shell
docker-compose up -d
``

To properly start the project:

```shell
mvn clean install spring-boot: run
```

## Swagger documentation: 
http://localhost:8080/swagger-ui/index.html

You will need to authenticate:
- Capture User: login 'bruno' and password '123'
- Analyst User: login 'joao' and password '123'

Steps to authenticate:
1) Get and copy a token on session 'User Authentication'
2) Click on 'Authorize' and paste the token

## Architectural decisions:

The following technologies were chosen for this project:
- In the database: "Mysql" for its robustness and performance, withstanding large data flows perfectly.
- Security was performed using Spring Security and JSON Web Token (JWT).
- Code versioning: "GitLab" for tracking changes, among others.
- When creating a data class: the "lombok" for its clean code provided.
- In the documentation: the "Swagger" to generate a documentation of the code.
- In the unit test: "Junit", "Mock", "Mockito" to ensure the operation of the code in accordance with the business rules.
.

### Selection Conductor Challenge
Hi, we want to invite you to participate in our selection challenge. Ready to participate? Your work will be seen by our team and you will eventually receive feedback on what we think of your work. Is not cool?

### About the opportunity
The vacancy is for Java Developer, we have vacancies with different levels of seniority and for each of them we will use specific criteria considering this aspect, combined?
If you pass this step, you will be invited for a final interview with our technical team.

### Technical Challenge
  Develop a credit analysis system for new cardholders, where we must have users with two types of permission, namely:
    * User for capturing proposals for new cards: this user will be responsible for registering new cardholders. After registering the holders, this user will only be allowed to check the results of the analysis carried out by the analysts.
    * Credit analyst user: this user will be responsible for verifying the registered proposals, being able to approve or deny the granting of credit. The result of the analysis will be made available to the proposal capture user.
    
  - Dictionary:
    ```
    * Cardholder: every person who has their credit analysis approved.
    * Credit review: the process by which the analyst checks documents, spc and any data available for granting credit.
    ```
    
  - Prerequisites:
    ```
    * Using DBMS Oracle, MySQL or Postgres.
    * JDK 1.8+
    * Maven 3+ (or gradle, or the like)
    * JUnit 4+
    * Web Framework at discretion (Servlets, JSF, Spring MVC or similar, preferably Angular)
    * Creation of a DUMP with data mass.
    ```

  - What we expect as a scope:
    ```
    * Add and maintain credit proposals;
    * Consult credit proposals, by data filters;
    * Credit analysis panel, containing all registered customers who have not yet been analyzed;
    * Different profiles for the user who captures the proposal and the user who performs the analysis;
    ```
    
- What we don't expect as a scope:
    ```
    * Card generation flow
    * Any flow made after the proposal has been approved or disapproved
    ```
  
  - What are we going to evaluate:
    ```
    * Your code;
    * Organization;
    * Good habits;
    ```

### Instructions
        1. Fork the challenge and create a 'challenge_analise_credit_name_candidate' branch;
        2. Develop. You will have 3 (three) days from the date of submission of the challenge;
        3. After completing your work do a push;
        4. Create a text file named README.MD explaining how we should run the
        project and with a description of what was done;
        5. Request the Merge request to the original repository and may the force be with you.
