## Ejercicio 1:

Desarollar todos los artefactos necesarios (controller, servicio, repositorio, modelo, etc) para implementar un CRUD + listado de **titulares de cuentas corrientes** según las siguientes consideraciones:


- Un titular puede ser tanto una persona física como jurídica.

	
- Si el titular es una persona física, los atributos requeridos
	serán los siguientes: nombre (maximo 80 caracteres), apellido (maximo 250 caracteres)
	y dni.
	
	
- Si el titular es una persona jurídica, los atributos requeridos
	serán los siguientes: razon social (maximo 100 caracteres) y año de fundación.
	
	
- Tanto para las personas fisicas como jurídicas, se requiere CUIT.

	
- No pueden existir 2 titulares con el mismo CUIT. 



Las funcionalidades requeridas (creacion, lectura, modificacion, borrado y listado) deberán exponerse en la API Rest.  Ademas de la implementacion, se requieren tambien las request que permitan probar los endpoints utilizando la herramienta curl

### Bonus

- Escribir pruebas unitarias (Junit) de los distintos componentes.


- Desplegar la aplicacion en algun servidor publico y pasar por mail las URLs.


- Agregar proyecto front end (angular o react) con las pantallas necesarias para utilizar las funcionalidades



---


## Ejercicio 2:
Implementar funcionalidades de alta, consulta, baja y listado de cuentas corrientes y movimientos.  Tener en cuenta
las siguientes consideraciones:

- Una cuenta corriente puede tener n movimientos.


- Los movimientos no pueden eliminarse ni modificarse.


- Las cuentas solo pueden eliminarse si no tienen movimientos asociados.


- Las cuentas poseen un numero de cuenta (valor requerido y único), una moneda (peso, dolar, euro) y un saldo (valor numérico de 2 decimales)


- Los movimientos poseen fecha (tomar horario UTC), tipo de movimiento (debito o credito), descripcion (200 caracteres) e importe (numerico de 2 decimales).


- Cada vez que se incorpora un nuevo movimiento, y dependiendo de su tipo, se debe actualizar el saldo de la cuenta asociada.


- Si un movimiento genera un descubierto mayor a 1000 (para cuentas en pesos), 300 (para cuentas en dolares) o 150 (para cuentas en euros) se deberá rechazar.



En la capa REST, los endpoints requeridos son los siguientes:


   1. *crear cuenta*
   2. *eliminar cuenta*
   3. *listar cuentas*
   4. *agregar movimiento*
   5. *listar movimientos x cuenta* (ordenados de forma decreciente, por fecha)



Se deberán aplicar todas las validaciones funcionales que se consideren oportunas, informando de los errores de manera conveniente.


### Bonus

- Escribir pruebas unitarias (Junit) de los distintos componentes.


- Desplegar la aplicacion en algun servidor publico y pasar por mail las URLs.


- Agregar proyecto front end (angular o react) con las pantallas necesarias para utilizar las funcionalidades

---


