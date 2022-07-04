## Exercise project for crypto recommendations


### API
The API for the recommendation service is under  /recommendation/   
You may visit the swagger page at api/recommendation/swagger-ui/index.html#

#### Brief summary of endpoints
crypto/stats/{symbol} - Return the statistics(min/max/newest/oldest) for a given symbol.\
If the symbol is not supported, then 406 is returned

crypto/normalized - Return a list of symbols with their respective normalized ranges in descending order\

crypto/normalized - Return the normalized ranges for a given symbol\
If the symbol is not supported, then 406 is returned

crypto/normalized/max/date - Return
the symbol along with the maximum normalized range for a given day\
If no symbol is found for the given date, then 404 is returned

### Rate limiting
The API has a rate limit of 5 req / 10 seconds per IP









