# AtmService
A mock implementation of ATM service

## API documentation
http://localhost:8080/swagger-ui/

## Usage

Run a server with mock BankCore API:
`mvn mockserver:run spring-boot:run`

POST a request to /issues/money with body

```json
{
  "cardNumber": "4731059982346342",
  "amount": 1000
}
```

Keep in mind that card number is being validated as card number so use these pre-generated CNs or create at https://ccardgen.com/

Valid card numbers
- 4731055520998487
- 4731059400856500
- 4731059982346342
- 4731053770046701
Invalid card number
- 4731051429700223