# Sunbase-Customer-Assignment Backend

## Description

The Customer Application provides endpoints to perform CRUD operations on customer data. It also includes functionalities for searching customers, syncing data with an external API, and managing user authentication using JWT.

## Tech Stack Used
1. Spring Boot
2. Java
3. Jwt Authentication
4. Mysql Database

## Installation

To run the Customer Application locally, follow these steps:

Clone the repository:
   git clone https:
   ```bash
   https://github.com/faizkhan1318/Sunbase-Assingment.git
   ```
## Usage

Once the application is running, you can access the following endpoints:

- **POST /customer/add**: Create a new customer.

- **PUT /customer/update/{emailId}**: Update an existing customer with the specified email ID.

- **GET /customer/getCustomerWithEmail/{email}**: Get customer details by email ID.

- **DELETE /customer/delete**: Delete a customer by email ID.

- **GET /customer/getCustomers**: Get a paginated list of all customers.

- **GET /customer/sync**: Sync data with an external API.

- **GET /customer/searchByType**: Search customers by specific type (e.g., name, email).
