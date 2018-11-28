Herramientas necesarias:

	- GIT     ( Version homologada: 2.11.0 )
	- Maven   ( Version homologada: 3.3.9 )
	- Java    ( Version homologada: 1.8.0_181, vendor: Oracle Corporation )
	- Eclipse ( Version homologada: Photon Release (4.8.0) )
	- curl    ( Version homologada: 7.52.1 )


Descargando el repositorio:

	- git clone git@gitlab.tecso.coop:tecso-public/test-spring-boot-level-1.git


Para ejecutar la aplicacion utilizando Maven:

	mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=dev"
	
	
Probando controllers:

	Obteniendo version de build:
	curl -X GET "http://localhost:8080/api/version/number"
	
	Realizando POST echo test:
	curl -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' 
	-d '{"mensaje":"mensaje de prueba"}' localhost:8080/api/echo
	
	Obtener suma de numeros:
	curl -X GET "http://localhost:8080/api/math/sum?valores=1.1&valores=2.22&valores=3.33"
	
	Obtener todos los paises:
	curl -X GET "http://localhost:8080/api/country/findAll"
	
	
Workflow:

	- Descargar repositorio.
	
	- Validar funcionamiento del proyecto descargado ejecutando la aplicacion via Maven y utilizando los requests de prueba
	mediante la utilizacion de "curl".
	
	- Desde el Eclipse, importar el proyecto (importar como proyecto Maven existente).
	
	- Resolver los ejercios indicados.  Ver enuciados en ** "EJERCICIOS.md" **.
	
	- Agregar un archivo llamado "RESPUESTAS.md" que contenga todas las "curl requests" que permitan
	probar las funcionalidades agregadas. 
	
	- Crear nuevo proyecto GIT (en gitlab o github), pushear en este nuevo espacio el
	proyecto con todos los ejercicios resueltos y enviar la url del repositorio
	para revision de las soluciones implementadas.  
	
	- Asegurarse que los permisos de acceso al repositorio sean adecuados para que quien reciba la url pueda realizar el clonado.
	