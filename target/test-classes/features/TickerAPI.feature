Feature: Validating Ticker API and verifying it's response  
@e2e @count
Scenario: Verify Count is above zero for every JSON object where Symbol contains XRP and firstId is above zero

    Given user calls "tickerAPI" with "GET" http request
    Then API call is success with status 200
    When symbol contains "XHR" and firstId is above 0 then count should be above 0 
    
@e2e @symbolName   
Scenario: Verify if Symbol Name is a concatenation of baseAsset and quoteAsset
    
     Given user calls "tickerAPI" with "GET" http request
     Then API call is success with status 200
     When user hits "exchangeAPI" with "GET" http request
     And verify "symbolName" is a concatination of "baseAsset" and "quoteAsset" value
    
 