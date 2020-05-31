# clickleitos

### Ferramenta usadas:

1. *Sprint boot*
2. _Spring Jpa_
3. *Spring web*
4. *maven*
5. *H2*

## Usuario routes

### findAll "GET"

```/usuario```
```/unidade```
```/unidade/leito```

### findById **GET**

```/usuario/{id}```
```/unidade/{id}```
```/unidade/{id}/leito```

### insert **POST**

```/usuario```
```/unidade```

**Body Usuario:**
```
{
    "nome": "admin03",
    "senha": "789",
    "email": "test03@gmail.com"
} 
```
**Body Undidae:**
```
{
    "cnpj": "000000000",
    "nome": "UPA - Salgado ",
    "longitude": "-84345788",
    "latitude": "-123245345",
    "leito": {
        "total": 100,
        "disponiveis": 20
    }
}
```
### delete "DELETE"

```/usuario/{id}```
```/unidade/{id}```

### update "PUT"

```/usuario/{id}```
```/unidade/{id}```
```/unidade/{id}/leito```

**Body Usuario:**
``` 
{
  "nome": "admin03",
  "senha": "789",
  "email": "test03@gmail.com"
}
```
**Body Unidade:**
``` 
{
    "cnpj": "000000000",
    "nome": "UPA - Salgado ",
    "longitude": "-84345788",
    "latitude": "-123245345",
    "leito": {
        "total": 100,
        "disponiveis": 20
    }
}
```
**Body Leito:**
``` 
{
    "total": 100,
    "disponiveis": 20
}
```

