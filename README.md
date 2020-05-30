# clickleitos

### Ferramenta usadas:

1. *Sprint boot*
2. _Spring Jpa_
3. *Spring web*
4. *maven*
5. *H2*

### Usuario routes

##### findAll "GET"

´´´ localhost:8080/usuario ´´´

##### findById "GET"

´´´ localhost:8080/usuario/{id} ´´´

##### insert "POST"

´´´ localhost:8080/usuario ´´´
Body: ´´´ {
	            "nome": "admin03",
              "senha": "789",
              "email": "test03@gmail.com"
           } 
      ´´´ 
##### delete "DELETE"

´´´ localhost:8080/usuario/{id} ´´´

##### update "PUT"

´´´ localhost:8080/usuario/{id} ´´´

Body: ´´´ {
	            "nome": "admin03",
              "senha": "789",
              "email": "test03@gmail.com"
           } 
      ´´´ 

