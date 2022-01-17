# BankCoreDemoApiApi

All URIs are relative to *http://localhost:8080*

| Method                                                                             | HTTP request                                    | Description |
|------------------------------------------------------------------------------------|-------------------------------------------------|-------------|
| [**commitReservationOnAccount**](BankCoreDemoApiApi.md#commitReservationOnAccount) | **POST** /v1/reservation/commit/{reservationId} |             |
| [**getAccountBalance**](BankCoreDemoApiApi.md#getAccountBalance)                   | **GET** /v1/account/balance/{accountNumber}     |             |
| [**getAccountNumberByCard**](BankCoreDemoApiApi.md#getAccountNumberByCard)         | **GET** /v1/account/{cardNumber}                |             |
| [**reserveMoneyOnAccount**](BankCoreDemoApiApi.md#reserveMoneyOnAccount)           | **POST** /v1/reservation                        |             |
| [**reserveMoneyOnAccount1**](BankCoreDemoApiApi.md#reserveMoneyOnAccount1)         | **DELETE** /v1/reservation/{reservationId}      |             |

<a name="commitReservationOnAccount"></a>
# **commitReservationOnAccount**
> String commitReservationOnAccount(reservationId)



Commit withdrawal

### Example
```java
// Import classes:
import io.swagger.client.ApiException;
import io.swagger.client.api.BankCoreDemoApiApi;


BankCoreDemoApiApi apiInstance = new BankCoreDemoApiApi();
String reservationId = "reservationId_example"; // String | 
try {
    String result = apiInstance.commitReservationOnAccount(reservationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BankCoreDemoApiApi#commitReservationOnAccount");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **reservationId** | **String**|  |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAccountBalance"></a>
# **getAccountBalance**
> BalanceResponse getAccountBalance(accountNumber)



Get Bank Account balance

### Example
```java
// Import classes:
import io.swagger.client.ApiException;
import io.swagger.client.api.BankCoreDemoApiApi;

BankCoreDemoApiApi apiInstance = new BankCoreDemoApiApi();
String accountNumber = "accountNumber_example"; // String | 
try {
    BalanceResponse result = apiInstance.getAccountBalance(accountNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BankCoreDemoApiApi#getAccountBalance");
    e.printStackTrace();
}
```

### Parameters

| Name              | Type       | Description | Notes |
|-------------------|------------|-------------|-------|
| **accountNumber** | **String** |             |       |

### Return type

[**BalanceResponse**](BalanceResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAccountNumberByCard"></a>
# **getAccountNumberByCard**
> CardResponse getAccountNumberByCard(cardNumber)



Get Bank Account number by Credit Card number

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.BankCoreDemoApiApi;


BankCoreDemoApiApi apiInstance = new BankCoreDemoApiApi();
String cardNumber = "cardNumber_example"; // String | 
try {
    CardResponse result = apiInstance.getAccountNumberByCard(cardNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BankCoreDemoApiApi#getAccountNumberByCard");
    e.printStackTrace();
}
```

### Parameters

| Name           | Type       | Description | Notes |
|----------------|------------|-------------|-------|
| **cardNumber** | **String** |             |       |

### Return type

[**CardResponse**](CardResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="reserveMoneyOnAccount"></a>
# **reserveMoneyOnAccount**
> ReservationResponse reserveMoneyOnAccount(body)



Reserve money on a Bank Account

### Example
```java
// Import classes:
import io.swagger.client.ApiException;
import io.swagger.client.api.BankCoreDemoApiApi;


BankCoreDemoApiApi apiInstance = new BankCoreDemoApiApi();
ReservationRequest body = new ReservationRequest(); // ReservationRequest | 
try {
    ReservationResponse result = apiInstance.reserveMoneyOnAccount(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BankCoreDemoApiApi#reserveMoneyOnAccount");
    e.printStackTrace();
}
```

### Parameters

| Name     | Type                                            | Description | Notes |
|----------|-------------------------------------------------|-------------|-------|
| **body** | [**ReservationRequest**](ReservationRequest.md) |             |       |

### Return type

[**ReservationResponse**](ReservationResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="reserveMoneyOnAccount1"></a>
# **reserveMoneyOnAccount1**
> String reserveMoneyOnAccount1(reservationId)



Cancel reservation

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.BankCoreDemoApiApi;


BankCoreDemoApiApi apiInstance = new BankCoreDemoApiApi();
String reservationId = "reservationId_example"; // String | 
try {
    String result = apiInstance.reserveMoneyOnAccount1(reservationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BankCoreDemoApiApi#reserveMoneyOnAccount1");
    e.printStackTrace();
}
```

### Parameters

| Name              | Type       | Description | Notes |
|-------------------|------------|-------------|-------|
| **reservationId** | **String** |             |       |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

