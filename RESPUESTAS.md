# Heroku
La Aplicación se encuentra deployada en heroku, la url a la aplicación es -> https://tecso-test.herokuapp.com

# Pruebas

* **Crear Cuenta:** `curl -X POST \
                       https://tecso-test.herokuapp.com/api/account \
                       -H 'Content-Type: application/json' \
                       -d '{
                     	"accountNumber":1234,
                     	"currency": "PESO"
                     }'`
 
* **Eliminar Cuenta:** `curl -X DELETE \
                          https://tecso-test.herokuapp.com/api/account/1 \
                          -H 'Content-Type: application/json'`
                          
* **Listar Cuentas:** `curl -X GET \
                         https://tecso-test.herokuapp.com/api/account \
                         -H 'Content-Type: application/json'`
                       
* **Agregar Movimiento:** `curl -X POST \
                             https://tecso-test.herokuapp.com/api/account/2/order \
                             -H 'Content-Type: application/json' \
                             -d '{
                           	"type": "DEBIT",
                           	"description": "Saraza debit",
                           	"amount": 10
                           }'`
                         
* **Listar Movimientos:** `curl -X GET \
                             https://tecso-test.herokuapp.com/api/account/2/order \
                             -H 'Content-Type: application/json'`  
