Feature: Login into nopCommerce

    Scenario Outline: Login with valid credentials using data driven
    #When User open URL "<RequiredURL>"
    And User Enter email as "<Uname>" and password as "<pw>"
    And Click on Login
    Then Page title should be "<title>"
    Then Close The Browser
    
    Examples:
    |RequiredURL																|Uname									|pw						|title																			|
    |http://admin-demo.nopcommerce.com/login		|admin@yourstore.com		|admin  			|Dashboard / nopCommerce administration			|
    |http://admin-demo.nopcommerce.com/login		|admin@yourstore.com		|admin1	  		|Your store. Login				  								|