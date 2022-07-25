# jwt-SpringBoot
Proyecto base de implementacion de jwt
1-Crear la bd con el nombre api_security
2-Para testear en postman: los datos los generan en la prueba
                          localhost:8080/security/register  rol usuario metodo post "username" : "user", "email" : "user@email.com","password" : "1234"
                          localhost:8080/security/register  rol admin metodo post, es igual pero se agrega "roles" : ["admin"]
                          localhost:8080/security/login  metodo post, "username" : "user", "password" : "1234"  y retorna un token
3-Generar una api basica que retorne algo y sobre el metodo se agrega @PreAuthorize("hasRole('ADMIN')") 
4-Para testear en postman: Authorization, type: bearer token, muestra un campo donde pegar el token antes generado en el login (sin las comillas) si son admin les da     acceso
