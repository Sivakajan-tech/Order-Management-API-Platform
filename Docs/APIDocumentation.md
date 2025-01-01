
## Endpoints

### Public Endpoints
1. **Sign Up**
   - `POST /api/v1/auth/signup`
   - Request Body:
     ```json
     {
       "email": "user@example.com",
       "password": "password123",
       "firstName": "John",
       "lastName": "Doe"
     }
     ```
   - Response:
     ```json
     {
       "message": "User registered successfully."
     }
     ```

2. **Sign In**
   - `POST /api/v1/auth/signin`
   - Request Body:
     ```json
     {
       "email": "user@example.com",
       "password": "password123"
     }
     ```
   - Response:
     ```json
     "JWT-TOKEN"
     ```

### Secured Endpoints (Require Authorization Header)
3. **Place Order**
   - `POST /api/v1/orders/order`
   - Request Body:
     ```json
     {
       "itemName": "Laptop",
       "quantity": 1,
       "shippingAddress": "123 Main St, Springfield"
     }
     ```
   - Response:
     ```json
     {
        "orderId": "62b7df73-8c24-4c5e-a316-f832c17f0384",
        "itemName": "Laptop",
        "quantity": 1,
        "shippingAddress": "123 Main St, Springfield",
        "createdAt": "2025-01-01T12:00:00Z",
     }
     ```

4. **Cancel Order**
   - `POST /api/v1/orders/cancel/{id}`
   - Id: Order ID -> Example => "62b7df73-8c24-4c5e-a316-f832c17f0384"
   - Response:
     ```json
     {
       "message": "Order cancelled successfully."
     }
     ```

5. **Fetch Order History**
   - `GET /api/v1/orders/history?page={pageNo}&size={pageSize}`
   - Response:
     ```json
     {
       "orders": [
         {
           "orderReference": "ORDER12345",
           "itemName": "Laptop",
           "quantity": 1,
           "shippingAddress": "123 Main St, Springfield",
           "status": "NEW",
           "timestamp": "2025-01-01T12:00:00Z"
         }
       ],
       "totalPages": 10
     }
     ```
