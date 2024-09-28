# E-commerce API Documentation

## Overview
This document describes the endpoints available in the e-commerce application API. The API allows users to manage their accounts, products, orders, carts, comments, and baskets.

### Base URL
All endpoints are prefixed with `/api/`.

---

## 1. AuthController

### Endpoints: `/auth`

#### 1. Login
- **POST /auth/login**
    - Logs in a user and returns a JWT token.
    - **Request Body:** `LoginRequest` (email, password).
    - **Response:** `200 OK` with JWT token.

#### 2. Register
- **POST /auth/register**
    - Registers a new user.
    - **Request Body:** `User` (user details).
    - **Response:** `200 OK` with registered user details.

#### 3. Change Password
- **POST /auth/change-password**
    - Changes the password of an authenticated user.
    - **Request Body:** `ChangePasswordRequest` (oldPassword, newPassword).
    - **Response:** `200 OK` - Password changed.

#### 4. Confirm Email
- **POST /auth/confirm-email**
    - Confirms a user's email with a confirmation code.
    - **Request Body:** `EmailConfirmationRequest` (email, confirmationCode).
    - **Response:** `200 OK` or `400 Bad Request` if the code is invalid.

---

## 2. ProductController

### Endpoints: `/products`

#### 1. Create Product
- **POST /products**
    - Creates a new product (Admin only).
    - **Request Body:** `ProductDTO` and optional `MultipartFile` for image.
    - **Response:** `200 OK` with created product details.

#### 2. Update Product
- **PUT /products/{id}**
    - Updates an existing product (Admin only).
    - **Request Parameters:** `id` (product ID).
    - **Request Body:** `ProductDTO` and optional `MultipartFile` for image.
    - **Response:** `200 OK` with updated product details.

#### 3. Delete Product
- **DELETE /products/{id}**
    - Deletes a product (Admin only).
    - **Request Parameters:** `id` (product ID).
    - **Response:** `204 No Content`.

#### 4. Get Product by ID
- **GET /products/{id}**
    - Retrieves a product by ID.
    - **Request Parameters:** `id` (product ID).
    - **Response:** `200 OK` with product details.

#### 5. Get All Products
- **GET /products**
    - Retrieves a paginated list of all products.
    - **Request Parameters:** Pagination settings.
    - **Response:** `200 OK` with a list of products.

---

## 3. CartController

### Endpoints: `/cart`

#### 1. Add to Cart
- **POST /cart/add**
    - Adds a product to the authenticated user's cart.
    - **Request Parameters:** `productId`, `quantity`.
    - **Response:** `200 OK` with updated cart details.

#### 2. Get Cart
- **GET /cart**
    - Retrieves the authenticated user's cart.
    - **Response:** `200 OK` with cart details.

#### 3. Clear Cart
- **DELETE /cart**
    - Clears the authenticated user's cart.
    - **Response:** `204 No Content`.

---

## 4. BasketController

### Endpoints: `/basket`

#### 1. Add to Basket
- **POST /basket/add**
    - Adds a product to the basket.
    - **Request Parameters:** `productId`, `quantity`.
    - **Response:** `200 OK` with updated basket details.

#### 2. Update Basket
- **PUT /basket/{basketId}/update**
    - Updates a product's quantity in the basket.
    - **Request Parameters:** `basketId`, `productId`, `quantity`.
    - **Response:** `200 OK` with updated basket details.

#### 3. Get Products in Basket
- **GET /basket/{basketId}/products**
    - Retrieves products in a specific basket.
    - **Request Parameters:** `basketId`.
    - **Response:** `200 OK` with a list of products.

#### 4. Share Basket
- **POST /basket/{basketId}/share**
    - Shares a basket with another user.
    - **Request Parameters:** `basketId`, `userId`.
    - **Response:** `200 OK` with shared basket details.

#### 5. Get Shared Basket Products
- **GET /basket/{basketId}/shared/{userId}/products**
    - Retrieves products from a shared basket.
    - **Request Parameters:** `basketId`, `userId`.
    - **Response:** `200 OK` with a list of products.

#### 6. Update Shared Basket
- **PUT /basket/{basketId}/shared/{userId}/update**
    - Updates a shared basket by the shared user.
    - **Request Parameters:** `basketId`, `userId`, `productId`, `quantity`.
    - **Response:** `200 OK` with updated basket details.

#### 7. Add Product by Shared User
- **POST /basket/{basketId}/shared/{userId}/add**
    - Adds a product to a shared basket.
    - **Request Parameters:** `basketId`, `userId`, `productId`, `quantity`.
    - **Response:** `200 OK` with updated basket details.

---

## 5. CommentController

### Endpoints: `/comments`

#### 1. Add Comment to Product
- **POST /comments/product/{productId}**
    - Adds a comment to a product by an authenticated user.
    - **Request Parameters:** `productId`.
    - **Request Body:** `CommentDTO`.
    - **Response:** `200 OK` with added comment details.

#### 2. Get Comments by Product
- **GET /comments/product/{productId}**
    - Retrieves comments for a specific product.
    - **Request Parameters:** `productId`.
    - **Response:** `200 OK` with a list of comments.

---

## 6. OrderController

### Endpoints: `/orders`

#### 1. Create Order
- **POST /orders**
    - Creates an order for the authenticated user.
    - **Request Parameters:** `address`, `phoneNumber`.
    - **Response:** `200 OK` with order details.

#### 2. Get All Orders (Admin Only)
- **GET /orders**
    - Retrieves all orders.
    - **Response:** `200 OK` with a list of all orders.

#### 3. Get User Orders
- **GET /orders/user**
    - Retrieves orders for the authenticated user.
    - **Response:** `200 OK` with a list of user's orders.

#### 4. Place Order from Basket
- **POST /orders/basket/{basketId}**
    - Places an order from a specified basket.
    - **Request Parameters:** `basketId`.
    - **Response:** `200 OK` with order details.

#### 5. Update Order Status (Admin Only)
- **PUT /orders/{orderId}/status**
    - Updates the status of an order.
    - **Request Parameters:** `orderId`, `status`.
    - **Response:** `200 OK` with updated order details.

---

## Summary

- **Authentication**: Handles user login, registration, password changes, and email confirmation.
- **Product Management**: Allows admins to create, update, delete, and view products.
- **Cart Management**: Allows users to manage their shopping cart.
- **Basket Management**: A feature that enables multiple users to collaborate on a shared basket.
- **Comments**: Users can add and view comments for products.
- **Order Management**: Allows users to create orders and admins to manage them.

Ensure that proper authentication and authorization are used for endpoints that require user roles.
