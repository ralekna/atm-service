# AtmService
A mock implementation of ATM service

## API documentation
http://localhost:8080/swagger-ui/

## Usage

Compile and install BankCore client to local Maven repo:
```bash
cd bank-core-java-client
mvn clean install
```

Run tests:
```bash
cd atm-service
mvn clean test
```


Run a server with mock BankCore API:
```bash
cd atm-service
mvn clean spring-boot:run
```

**NOTE: WireMock server is not running when application is launched so your requests will fail**

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
