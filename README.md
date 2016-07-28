# Tabla de contenidos
-  [Introducción](#introducción)
-  [API](#api-de-la-aplicación-artwork)
  - [Recurso Client](#recurso-client)
    - [GET /clients](#GET-/clients)
    - [GET /clients/{id}](#GET-/clients/{id})
    - [POST /clients](#POST-/clients)
    - [PUT /clients/{id}](#PUT-/clients/{id})
    - [DELETE /clients/{id}](#DELETE-/clients/{id})
  - [Recurso Artist](#recurso-artist)
    - [GET /artists](#GET-/artists)
    - [GET /artists/{id}](#GET-/artists/{id})
    - [POST /artists](#POST-/artists)
    - [PUT /artists/{id}](#PUT-/artists/{id})
    - [DELETE /artists/{id}](#DELETE-/artists/{id})
  - [Recurso Product](#recurso-product)
    - [GET /products](#GET-/products)
    - [GET /products/{id}](#GET-/products/{id})
    - [POST /products](#POST-/products)
    - [PUT /products/{id}](#PUT-/products/{id})
    - [DELETE /products/{id}](#DELETE-/products/{id})
  - [Recurso Category](#recurso-category)
    - [GET /categorys](#GET-/categorys)
    - [GET /categorys/{id}](#GET-/categorys/{id})
    - [POST /categorys](#POST-/categorys)
    - [PUT /categorys/{id}](#PUT-/categorys/{id})
    - [DELETE /categorys/{id}](#DELETE-/categorys/{id})

# API Rest
## Introducción
La comunicación entre cliente y servidor se realiza intercambiando objetos JSON. Para cada entidad se hace un mapeo a JSON, donde cada uno de sus atributos se transforma en una propiedad de un objeto JSON. Todos los servicios se generan en la URL /Artwork.api/api/. Por defecto, todas las entidades tienen un atributo `id`, con el cual se identifica cada registro:

```javascript
{
    id: '',
    attribute_1: '',
    attribute_2: '',
    ...
    attribute_n: ''
}
```

Cuando se transmite información sobre un registro específico, se realiza enviando un objeto con la estructura mencionada en la sección anterior.
La única excepción se presenta al solicitar al servidor una lista de los registros en la base de datos, que incluye información adicional para manejar paginación de lado del servidor en el header `X-Total-Count` y los registros se envían en el cuerpo del mensaje como un arreglo.

La respuesta del servidor al solicitar una colección presenta el siguiente formato:

```javascript
[{}, {}, {}, {}, {}, {}]
```

## API de la aplicación Artwork
### Recurso Client
El objeto Client tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/
}
```


#### Representación Full
```javascript
{
    // todo lo de la representación Detail más la collección de los objetos de relaciones composite.
    wishList: [{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/,
    qty: '' /*Tipo Long*/    }]
}
```


#### GET /clients

Retorna una colección de objetos Client en representación Detail.

#### Parámetros

#### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de [representaciones Detail](#recurso-client)
409|Un objeto relacionado no existe|Mensaje de error
500|Error interno|Mensaje de error

#### GET /clients/{id}

Retorna una colección de objetos Client en representación Full.
Cada Client en la colección tiene embebidos los siguientes objetos con relaciones composite: Item.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Client a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Client en [representaciones Full](#recurso-client)
404|No existe un objeto Client con el ID solicitado|Mensaje de error
500|Error interno|Mensaje de error

#### POST /clients

Es el encargado de crear objetos Client.
Dado que Item es una clase hija de Client a través de una relación composite, este servicio también permite la creación de wishList asociados con un Client.
.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto Client que será creado|Sí|[Representación Full](#recurso-client)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Client ha sido creado|[Representación Full](#recurso-client)
409|Un objeto relacionado no existe|Mensaje de error
500|No se pudo crear el objeto Client|Mensaje de error

#### PUT /clients/{id}

Es el encargado de actualizar objetos Client.
Dado que Item es una clase hija de Client a través de una relación composite, este servicio también permite la actualización de wishList asociados con un Client.
.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Client a actualizar|Sí|Integer
body|body|Objeto Client nuevo|Sí|[Representación Full](#recurso-client)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Client actualizado|[Representación Full](#recurso-client)
409|Un objeto relacionado no existe|Mensaje de error
500|No se pudo actualizar el objeto Client|Mensaje de error

#### DELETE /clients/{id}

Elimina un objeto Client.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Client a eliminar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error


[Volver arriba](#tabla-de-contenidos)
### Recurso Artist
El objeto Artist tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/
}
```


#### Representación Full
```javascript
{
    // todo lo de la representación Detail más la collección de los objetos de relaciones composite.
    artworks: [{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/,
    image: '' /*Tipo String*/,
    price: '' /*Tipo Long*/    }]
}
```


#### GET /artists

Retorna una colección de objetos Artist en representación Detail.

#### Parámetros

#### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de [representaciones Detail](#recurso-artist)
409|Un objeto relacionado no existe|Mensaje de error
500|Error interno|Mensaje de error

#### GET /artists/{id}

Retorna una colección de objetos Artist en representación Full.
Cada Artist en la colección tiene embebidos los siguientes objetos con relaciones composite: Artwork.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Artist a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Artist en [representaciones Full](#recurso-artist)
404|No existe un objeto Artist con el ID solicitado|Mensaje de error
500|Error interno|Mensaje de error

#### POST /artists

Es el encargado de crear objetos Artist.
Dado que Artwork es una clase hija de Artist a través de una relación composite, este servicio también permite la creación de artworks asociados con un Artist.
.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto Artist que será creado|Sí|[Representación Full](#recurso-artist)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Artist ha sido creado|[Representación Full](#recurso-artist)
409|Un objeto relacionado no existe|Mensaje de error
500|No se pudo crear el objeto Artist|Mensaje de error

#### PUT /artists/{id}

Es el encargado de actualizar objetos Artist.
Dado que Artwork es una clase hija de Artist a través de una relación composite, este servicio también permite la actualización de artworks asociados con un Artist.
.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Artist a actualizar|Sí|Integer
body|body|Objeto Artist nuevo|Sí|[Representación Full](#recurso-artist)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Artist actualizado|[Representación Full](#recurso-artist)
409|Un objeto relacionado no existe|Mensaje de error
500|No se pudo actualizar el objeto Artist|Mensaje de error

#### DELETE /artists/{id}

Elimina un objeto Artist.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Artist a eliminar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error


[Volver arriba](#tabla-de-contenidos)
### Recurso Product

#### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/,
    price: '' /*Tipo Long*/
}
```




#### GET /products

Retorna una colección de objetos Product en representación Detail.

#### Parámetros

#### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de [representaciones Detail](#recurso-product)
409|Un objeto relacionado no existe|Mensaje de error
500|Error interno|Mensaje de error

#### GET /products/{id}

Retorna una colección de objetos Product en representación Full.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Product a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Product en [representaciones Full](#recurso-product)
404|No existe un objeto Product con el ID solicitado|Mensaje de error
500|Error interno|Mensaje de error

#### POST /products

Es el encargado de crear objetos Product.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto Product que será creado|Sí|[Representación Full](#recurso-product)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Product ha sido creado|[Representación Full](#recurso-product)
409|Un objeto relacionado no existe|Mensaje de error
500|No se pudo crear el objeto Product|Mensaje de error

#### PUT /products/{id}

Es el encargado de actualizar objetos Product.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Product a actualizar|Sí|Integer
body|body|Objeto Product nuevo|Sí|[Representación Full](#recurso-product)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Product actualizado|[Representación Full](#recurso-product)
409|Un objeto relacionado no existe|Mensaje de error
500|No se pudo actualizar el objeto Product|Mensaje de error

#### DELETE /products/{id}

Elimina un objeto Product.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Product a eliminar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error


[Volver arriba](#tabla-de-contenidos)
### Recurso Category
El objeto Category tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/
}
```

#### Representación Detail
```javascript
{
    // todo lo de la representación Minimum más los objetos Minimum con relación simple.
    parentCategory: {
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/    }
}
```



#### GET /categorys

Retorna una colección de objetos Category en representación Detail.
Cada Category en la colección tiene embebidos los siguientes objetos: Category.

#### Parámetros

#### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de [representaciones Detail](#recurso-category)
409|Un objeto relacionado no existe|Mensaje de error
500|Error interno|Mensaje de error

#### GET /categorys/{id}

Retorna una colección de objetos Category en representación Full.
Cada Category en la colección tiene los siguientes objetos: Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Category a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Category en [representaciones Full](#recurso-category)
404|No existe un objeto Category con el ID solicitado|Mensaje de error
500|Error interno|Mensaje de error

#### POST /categorys

Es el encargado de crear objetos Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto Category que será creado|Sí|[Representación Full](#recurso-category)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Category ha sido creado|[Representación Full](#recurso-category)
409|Un objeto relacionado no existe|Mensaje de error
500|No se pudo crear el objeto Category|Mensaje de error

#### PUT /categorys/{id}

Es el encargado de actualizar objetos Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Category a actualizar|Sí|Integer
body|body|Objeto Category nuevo|Sí|[Representación Full](#recurso-category)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Category actualizado|[Representación Full](#recurso-category)
409|Un objeto relacionado no existe|Mensaje de error
500|No se pudo actualizar el objeto Category|Mensaje de error

#### DELETE /categorys/{id}

Elimina un objeto Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Category a eliminar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error


[Volver arriba](#tabla-de-contenidos)
