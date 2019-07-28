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
