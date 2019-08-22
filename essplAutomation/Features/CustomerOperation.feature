Feature: Customer Operation
  
  Background: Below are the common steps for each below scenario
    #Given User launch Chrome browser
    When User open URL "http://admin-demo.nopcommerce.com/login"
    And User Enter email as "admin@yourstore.com" and password as "admin"
    And Click on Login
    Then user should land on the Dashboard page

  Scenario: Add a new customer in customer page
    Then user should click on customer which is on the left sidebar
    Then Click on Customer submenu of Customer menubar
    Then Click on Add New button
    Then User should fill all the details and save it
    Then User can see the confirmation message as "The new customer has been added successfully"
    Then click on logout

  Scenario: Search Customer By emailID
    Then user should click on customer which is on the left sidebar
    Then Click on Customer submenu of Customer menubar
    And Enter Customer EmailID
    When Click on Search button
    Then User should found Email on the search table

    
#Scenario: Search Customer By First and last Name
    #Then user should click on customer which is on the left sidebar
    #Then Click on Customer submenu of Customer menubar
    #And Enter Customer FirstName during search
    #And Enter Customer LastName during search
    #When Click on Search button
    #Then User should found Name on the search table
    #Then click on logout
    