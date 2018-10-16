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
	
	
	
