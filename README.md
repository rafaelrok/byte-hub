# ![Projeto Comunidade Byte Hub](spring.png)

> ### Spring Boot 3, base de código Java 21 contendo exemplos do mundo real (CRUD, autenticação, padrões avançados, etc.) que aderem às especificações.

### [Demonstração](http:localhost:8080)

Este projeto foi criada para demonstrar um aplicativo fullstack completo construído com Spring Boot e ReactJS, incluindo operações CRUD, autenticação, roteamento, paginação e muito mais.

Aqui com todo meu esforço para demonstrar o que é que isso pode fazer, envolvendo as melhores praticas da comunidade do 
spring boot e ReactJS.
# Como funciona

> A aplicação utiliza Spring Boot 3 e Java 21 e ReactJS + Hooks em toda a sua arquitetura.

### O que é que isso pode fazer?
Aqui estão as funcionalidades da API:

* __Autenticação de usuário via JWT:__ Permite que os usuários façam login com segurança usando JSON Web Tokens (JWT).
* __Users:__ Fornece operações para criar, ler e atualizar perfis de usuário.
* __Artigos:__ Permite que os usuários criem, leiam e excluam artigos.
* __Comentários:__ Permite que os usuários criem, leiam e excluam comentários em artigos.
* __Article Listings:__ Oferece listas paginadas de artigos com parâmetros ``offset`` e ``limit``.
* __Favorito de artigos:__ Permite que os usuários marquem artigos como favoritos.
* __User Following:__ Permite que os usuários sigam e deixem de seguir perfis de outros usuários.
* __Article Feed:__ Oferece um endpoint de feed para ver artigos de usuários seguidos.

### O que está por baixo do capô?

Aqui estão as tecnologias usadas para a implementação:

* __Spring Data JPA__ para gerenciamento eficiente de dados.
* __Spring Security__ com __tokens JWT__ para autenticação.
* __JUnit5__ e __Mockito__ para teste.
* __MySql__ para o banco de dados.
* __Flyway__ para as migrações de banco de dados.
* __[modelmapper](https://modelmapper.org/)__ para os DTOs.
* __[spring-arg-resolver](https://github.com/tkaczmarzyk/specification-arg-resolver)__, para filtragem dinâmica de parâmetros.

#Como faço para executá-lo?

Aqui estão as etapas necessárias para fazer o projeto funcionar:

Você precisará do docker instalado.

* Clone este repositório.
* Execute ``docker-compose up``.

É isso :)