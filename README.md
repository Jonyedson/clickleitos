# clickleitos

### Ferramenta usadas:

1. *Sprint boot*
2. _Spring Jpa_
3. *Spring web*
4. *maven*
5. *H2*

### Usuario routes

##### findAll "GET"

```/usuario ```

##### findById **GET**

```/usuario/{id} ```

##### insert **POST**

``` /usuario ```

**Body**: 
```
{
    "nome": "admin03",
    "senha": "789",
    "email": "test03@gmail.com"
} 
```

##### delete "DELETE"

``` /usuario/{id} ```

##### update "PUT"

``` /usuario/{id} ```

**Body**:
``` 
{
  "nome": "admin03",
  "senha": "789",
  "email": "test03@gmail.com"
}
```

