# starbux

https://starbux-test-app.herokuapp.com/api/starbux/swagger-ui/

# build

For building application you have to create empty postgres database with parameters:

| Parameter       | Value                 |
| ------------    | ----------------      |
| name            | starbux               |     
| user            | postgres              |     
| password        | postgres              |     

Liquibase changesets will automatically create all tables needed
(I am sorry for this extra configuration)
