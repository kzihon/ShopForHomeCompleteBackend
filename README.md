# Shop for Home - Spring Boot E-commerce API

Welcome to Shop for Home, a Spring Boot API E-commerce application. This API provides essential functionalities eg. Security - user authentication with JWT, image upload and CRUD operations. The application utilizes a MySQL database to store and retrieve data efficiently.

## Table of Contents

1. [Features](#features)
2. [Technologies Used](#technologies-used)
3. [Getting Started](#getting-started)
   - [Prerequisites](#prerequisites)
   - [Installation](#installation)
4. [Configuration](#configuration)
   - [Database Configuration](#database-configuration)
   - [JWT Configuration](#jwt-configuration)
5. [Usage](#usage)
   - [Authentication](#authentication)
   - [Product Management](#product-management)
6. [API Endpoints](#api-endpoints)
7. [Contributors](#contributors)
8. [License](#license)

## Features

- **JWT Authentication:** Secure your API with JSON Web Token-based authentication.
- **Product Management:** Perform CRUD operations on products, including uploading product images.
- **MySQL Database:** Utilize a reliable and scalable MySQL database for data storage.
- and more...

## Technologies Used

- Spring Boot
- MySQL
- JSON Web Token (JWT)
- Maven
- Lombok

## Getting Started

### Prerequisites

Make sure you have the following installed on your machine:

- Java 8 or later
- MySQL
- Maven

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/kzihon/ShopForHomeCompleteBackend.git
   cd ShopForHomeCompleteBackend
   ```

2. Build the project:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   java -jar target/shop-for-home-1.0.jar
   ```

## Configuration

### Database Configuration

Configure your MySQL database connection in the `application.properties` file:

```properties
spring.datasource.url=your-database
spring.datasource.username=your-username
spring.datasource.password=your-password
```

### JWT Configuration

Set your JWT secret key in the `application.properties` file:

```properties
jwt.secret=your-secret-key
jwt.expiration=86400000 # 24 hours in milliseconds
```

## API Endpoints:

![image](https://github.com/kzihon/ShopForHomeCompleteBackend/assets/30321279/d1d74f40-a607-4f0a-96b0-1b93d838970f)


### Contributors

BEST Java programmers:

- [Kzihon](https://github.com/kzihon)
- [Sami](https://github.com/9samioh)
- [Gerard](https://github.com/gerardsegismundo)


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
