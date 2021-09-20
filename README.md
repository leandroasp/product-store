To run the application you must have Docker installed. 

In the **/docs** directory you have the front-end compiled.

In the application directory run the command:

```
docker build -t springio:netprecision . 
docker-compose up
```

You can also run the application with an in-memory database without the docker. In application.properties change to **test** and use the command:

```
./mvnw spring-boot:run
```

##### End points:


###### To add a new order, do a POST by sending a description to:
```
/purchase
```

###### To add a product to an order, do a POST by sending a product details to:
```
/purchase/add
```

###### To delete a product from an order, do a DELETE by sending a product details to:
```
/purchase/del
```

###### To list the order with your products, do a GET to:
```
/purchase/{ID}
```

###### To receive the total order price, do a GET to:
```
/purchase/{ID}/total
```

###### To close the order, do a GET to:
```
/purchase/{ID}/close?payment={PAYMENT_VALUE}
```

###### To calculate a custom order, do a GET by sending a list of products to:
```
/purchase/{ID}/calc
```

###### Example of purchase description POST:
```
{"description": "Order of client"}
```

###### Example of product details POST and DELETE:
```
{"product_id": 1154, "purchase_id": 1, "quantity": 2}
```

###### Example of product list:
```
[{"id": 1154, "quantity": 2},{"id": 1164, "quantity": 1}]
```
