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

- **Autenticação de usuário via JWT:** Permite que os usuários façam login com segurança usando JSON Web Tokens (JWT).
- **Users:** Fornece operações para criar, ler e atualizar perfis de usuário.
- **Artigos:** Permite que os usuários criem, leiam e excluam artigos.
- **Comentários:** Permite que os usuários criem, leiam e excluam comentários em artigos.
- **Article Listings:** Oferece listas paginadas de artigos com parâmetros `offset` e `limit`.
- **Favorito de artigos:** Permite que os usuários marquem artigos como favoritos.
- **User Following:** Permite que os usuários sigam e deixem de seguir perfis de outros usuários.
- **Article Feed:** Oferece um endpoint de feed para ver artigos de usuários seguidos.

### O que está por baixo do capô?

Aqui estão as tecnologias usadas para a implementação:

- **Spring Data JPA** para gerenciamento eficiente de dados.
- **Spring Security** com **tokens JWT** para autenticação.
- **JUnit5** e **Mockito** para teste.
- **MySql** para o banco de dados.
- **Flyway** para as migrações de banco de dados.
- **[modelmapper](https://modelmapper.org/)** para os DTOs.
- **[spring-arg-resolver](https://github.com/tkaczmarzyk/specification-arg-resolver)**, para filtragem dinâmica de parâmetros.

#Como faço para executá-lo?

Aqui estão as etapas necessárias para fazer o projeto funcionar:

Você precisará do docker instalado.

- Clone este repositório.
- Execute `docker-compose up`.

É isso :)
