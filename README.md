# Image-shop

Repository was created for Software Design course in ITMO (CT) university by Skroba Dmitriy.

### Purpose:

Gain practical experience in using reactive patterns.

### How to launch:

1. Clone repository: ```git clone https://github.com/Sdmitrioul/Calculator.git```.
2. Go to project root directory.
3. Create ```.env``` file using as example [.env.template](.env.template).
4. Be sure that you have running MongoDB (not later than 5.0).
    - There's an example how to download it on mac.
      - ```brew tap mongodb/brew```
      - ```brew update```
      - ```brew install mongodb-community@5.0```
      - ```brew services start mongodb-community@5.0```
5. Run: ```./gradlew run```.

### Example

You can find examples of requests [here](./docs/img).

#### List of supported requests:
1. Create a user:
   -  `http://localhost:<YOUR_PORT>/user/create?userName=<USER_NAME>&currency=<CURRENCY_NAME>`
2. Create  a product:
    - `http://localhost:<YOUR_PORT>/product/add?name=<PRODUCT_NAME>&currency=<CURRENCY_NAME>&price=<PRICE>`
3. Get all currencies:
   - `http://localhost:<YOUR_PORT>/`
4. Get all users:
    - `http://localhost:<YOUR_PORT>/users`
5. Get all products for user:
   - `http://localhost:<YOUR_PORT>/user/products?userId=<USER_ID>`
6. Add currency rate:
   - `http://localhost:<YOUR_PORT>/currency/upsert?sold=<CURRENCY_NAME>&bought=<CURRENCY_NAME>&rate=<CURRENCY_RATE>`
