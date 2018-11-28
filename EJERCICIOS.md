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
Implementar un CRUD + listado de
