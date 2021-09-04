# ExchangeRates
Exchange rates on Spring Boot

# Settings
The settings are made in the file: application.properties
- server.port => Server port
- rates.app_id => App ID of https://docs.openexchangerates.org
- gifs.api_key => API KEY of https://developers.giphy.com

# Request
- http://host:port/api/rate?currency=XXX
- XXX //Rate ISO Code

# Response 
- Header => application/json
- Body => {
           "rate":String // ="Rich"/"Broke",
           "message":String // message from server,
           "url":String // url of gif
          }
           
           
# Tests
- Tests locate in class ExchangeratesApplicationTests
