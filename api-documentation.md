# API Documentation for Auto Service Application

## Overview

This documentation covers the
API endpoints available for managing cars, 
parts, and repair jobs in the Auto Service Application. 
Each endpoint includes the HTTP method, URL, request body, 
and sample responses.

## Cars API

### Create a New Car

**Method**: `POST`  
**Endpoint**: `/api/cars`  
**Headers**:  
`Content-Type: application/json`

**Request Body:**

```json
{
    "serialNumber": "123456",
    "enterpriseNumber": "ENT-001",
    "licensePlate": "ABC-123",
    "make": "Toyota",
    "model": "Camry",
    "vin": "VIN1111111",
    "engineType": "Petrol",
    "engineNumber": "ENG111111",
    "transmissionType": "Automatic",
    "transmissionNumber": "TRN111111",
    "yearOfManufacture": 2020,
    "mileage": 10000,
    "carType": "PASSENGER_CAR"
}

```
**Response:**
```json
{
    "id": 1,
    "serialNumber": "123456",
    "enterpriseNumber": "ENT-001",
    "licensePlate": "ABC-123",
    "make": "Toyota",
    "model": "Camry",
    "vin": "VIN1111111",
    "engineType": "Petrol",
    "engineNumber": "ENG111111",
    "transmissionType": "Automatic",
    "transmissionNumber": "TRN111111",
    "yearOfManufacture": 2020,
    "mileage": 10000,
    "carType": "PASSENGER_CAR"
}
```
**GET CAR BY VIN**

**Method**: `GET`  
**Endpoint**: `/api/cars/{vin}`  
**Headers:**
`Content-Type: application/json`

**RESPONSE:**

```json
{
    "id": 1,
    "serialNumber": "123456",
    "enterpriseNumber": "ENT-001",
    "licensePlate": "ABC-123",
    "make": "Toyota",
    "model": "Camry",
    "vin": "VIN1111111",
    "engineType": "Petrol",
    "engineNumber": "ENG111111",
    "transmissionType": "Automatic",
    "transmissionNumber": "TRN111111",
    "yearOfManufacture": 2020,
    "mileage": 10000,
    "carType": "PASSENGER_CAR"
}
```
**UPDATE A CAR**

**Method**: `PUT`  
**Endpoint**: `/api/cars/{id}`  
**Headers**:
`Content-Type: application/json`

**Request Body:**
```json
{
    "serialNumber": "123456",
    "enterpriseNumber": "ENT-001",
    "licensePlate": "ABC-123",
    "make": "Toyota",
    "model": "Camry",
    "vin": "VIN1111111",
    "engineType": "Petrol",
    "engineNumber": "ENG111111",
    "transmissionType": "Automatic",
    "transmissionNumber": "TRN111111",
    "yearOfManufacture": 2020,
    "mileage": 15000,
    "carType": "PASSENGER_CAR"
}
```
**RESPONSE:**
```json
{
    "id": 1,
    "serialNumber": "123456",
    "enterpriseNumber": "ENT-001",
    "licensePlate": "ABC-123",
    "make": "Toyota",
    "model": "Camry",
    "vin": "VIN1111111",
    "engineType": "Petrol",
    "engineNumber": "ENG111111",
    "transmissionType": "Automatic",
    "transmissionNumber": "TRN111111",
    "yearOfManufacture": 2020,
    "mileage": 15000,
    "carType": "PASSENGER_CAR"
}
```
**DELETE A CAR:**

**Method:** `DELETE`  
**Endpoint:** `/api/cars/{id}`  
**Headers:**
`Content-Type: application/json`

**RESPONSE:**

```json

{
"message": "Car with ID 1 has been deleted."
}
```
## Parts API 
### Create a New Part

**Method:** `POST`  
**Endpoint:** `/api/parts`  
**Headers:** 
`Content-Type: application/json`

**Request Body:**

```json

{
"name": "Oil Filter",
"manufacturer": "Bosch",
"partNumber": "OF123",
"quantity": 50,
"price": 10.99,
"vin": "VIN1111111"
}
```

**Response:**

```json

{
"id": 1,
"name": "Oil Filter",
"manufacturer": "Bosch",
"partNumber": "OF123",
"quantity": 50,
"price": 10.99,
"vin": "VIN1111111"
}
```
**Get a Part by ID**

**Method:** `GET`  
**Endpoint:** `/api/parts/{id}`  
**Headers:**
`Content-Type: application/json`

**Response:**

```json

{
"id": 1,
"name": "Oil Filter",
"manufacturer": "Bosch",
"partNumber": "OF123",
"quantity": 50,
"price": 10.99,
"vin": "VIN1111111"
}
```
**Update a Part**

**Method:** `PUT`  
**Endpoint:** `/api/parts/{id}`  
**Headers:**
`Content-Type: application/json`

**Request Body:**
```
json

{
"name": "Oil Filter",
"manufacturer": "Bosch",
"partNumber": "OF123",
"quantity": 60,
"price": 10.99,
"vin": "VIN1111111"
}
```

**Response:**

```json

{
"id": 1,
"name": "Oil Filter",
"manufacturer": "Bosch",
"partNumber": "OF123",
"quantity": 60,
"price": 10.99,
"vin": "VIN1111111"
}
```
**Delete a Part**

**Method:** `DELETE`   
**Endpoint:** `/api/parts/{id}`  
**Headers:**
`Content-Type: application/json`

**Response:**

```json

{
"message": "Part with ID 1 has been deleted."
}
```
## Repair Jobs API
### Create a New Repair Job

**Method:** `POST`
**Endpoint:** `/api/repair-jobs`
**Headers:**
`Content-Type: application/json`

**Request Body:**

```json

{
"jobName": "Oil Change",
"intervalInMileage": 10000,
"intervalInHours": 200,
"intervalInDays": 365,
"requiredParts": [
{
"name": "Oil Filter",
"manufacturer": "Bosch",
"partNumber": "OF123",
"quantity": 1,
"vin": "VIN1111111"
}
],
"lastMileage": 9000,
"lastJobDate": "2024-07-01"
}
```
**Response:**

```json

{
"id": 1,
"jobName": "Oil Change",
"intervalInMileage": 10000,
"intervalInHours": 200,
"intervalInDays": 365,
"requiredParts": [
{
"id": 1,
"name": "Oil Filter",
"manufacturer": "Bosch",
"partNumber": "OF123",
"quantity": 1,
"vin": "VIN1111111"
}
],
"lastMileage": 9000,
"lastJobDate": "2024-07-01"
}
```
**Get a Repair Job by ID**

**Method:** `GET`
**Endpoint:** `/api/repair-jobs/{id}`
**Headers:**
`Content-Type: application/json`

**Response:**

```json

{
"id": 1,
"jobName": "Oil Change",
"intervalInMileage": 10000,
"intervalInHours": 200,
"intervalInDays": 365,
"requiredParts": [
{
"id": 1,
"name": "Oil Filter",
"manufacturer": "Bosch",
"partNumber": "OF123",
"quantity": 1,
"vin": "VIN1111111"
}
],
"lastMileage": 9000,
"lastJobDate": "2024-07-01"
}
```
**Update a Repair Job**

**Method:** `PUT`  
**Endpoint:** `/api/repair-jobs/{id}`  
**Headers:**
`Content-Type: application/json`  

**Request Body:**

```json

{
"jobName": "Oil Change",
"intervalInMileage": 12000,
"intervalInHours": 250,
"intervalInDays": 365,
"requiredParts": [
{
"id": 1,
"name": "Oil Filter",
"manufacturer": "Bosch",
"partNumber": "OF123",
"quantity": 1,
"vin": "VIN1111111"
}
],
"lastMileage": 11000,
"lastJobDate": "2024-08-01"
}
```
**Response:**

```json

{
"id": 1,
"jobName": "Oil Change",
"intervalInMileage": 12000,
"intervalInHours": 250,
"intervalInDays": 365,
"requiredParts": [
{
"id": 1,
"name": "Oil Filter",
"manufacturer": "Bosch",
"partNumber": "OF123",
"quantity": 1,
"vin": "VIN1111111"
}
],
"lastMileage": 11000,
"lastJobDate": "2024-08-01"
}
```
**Delete a Repair Job**

**Method:** `DELETE`  
**Endpoint:** `/api/repair-jobs/{id}`  
**Headers:**
`Content-Type: application/json`

**Response:**

```json

{
"message": "Repair Job with ID 1 has been deleted."
}
```
## Conclusion

This documentation provides the details for interacting with the Auto Service API for managing cars, parts, and repair jobs. Make sure to update the API endpoints as necessary when your application evolves.