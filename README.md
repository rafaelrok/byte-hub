# ![Projeto Comunidade Byte Hub](https://res.cloudinary.com/dkar9uu7g/image/upload/v1725651195/Developer/ByteHub/aykq2cmnid5ultwowebn.png)

## Introdução

O ByteHub é um projeto de código aberto que implementa um aplicativo fullstack completo utilizando tecnologias modernas como Spring Boot, Vue.js e várias outras ferramentas e bibliotecas. Este projeto serve como um exemplo real de aplicação que abrange operações CRUD, autenticação, roteamento, paginação e muito mais.

## Demonstração

[Link para a Demonstração](http://localhost:8080)

## Funcionalidades

Aqui estão as principais funcionalidades da aplicação:

- **Autenticação de Usuário via JWT**: Permite que os usuários façam login com segurança usando JSON Web Tokens (JWT).
- **Gerenciamento de Usuários**: Operações para criar, ler e atualizar perfis de usuário.
- **Gerenciamento de Artigos**: Permite que os usuários criem, leiam e excluam artigos.
- **Gerenciamento de Comentários**: Permite que os usuários criem, leiam e excluam comentários em artigos.
- **Listagem de Artigos**: Oferece listas paginadas de artigos com parâmetros `offset` e `limit`.
- **Favoritar Artigos**: Permite que os usuários marquem artigos como favoritos.
- **Seguimento de Usuários**: Permite que os usuários sigam e deixem de seguir perfis de outros usuários.
- **Feed de Artigos**: Oferece um endpoint de feed para ver artigos de usuários seguidos.

## Tecnologias Utilizadas

### Backend

- **Spring Boot 3**
- **Java 21**
- **Spring Data JPA**: Para gerenciamento eficiente de dados.
- **Spring Security**: Com tokens JWT para autenticação.
- **JUnit5 e Mockito**: Para testes.
- **MySQL**: Para o banco de dados.
- **Flyway**: Para migrações de banco de dados.
- **RabbitMQ**: Para gerenciar eventos de notificação.
- **ModelMapper**: Para mapeamento de DTOs.
- **Spring Arg Resolver**: Para filtragem dinâmica de parâmetros.

### Frontend

- **Vue 3**
- **Vite**: Ferramenta de construção rápida.
- **Vue Router**: Gerenciamento de rotas.
- **Pinia**: Gerenciamento de estado.
- **TypeScript**: Tipagem estática.
- **ESLint**: Linting e estilização.
- **Vitest**: Testes unitários.
- **Cypress**: Testes E2E.
- **GitHub Actions**: CI/CD.

## Como Executar

### Backend

1. Clone este repositório.
2. Navegue até o diretório do backend.
3. Execute `docker-compose up`.

### Frontend

1. Navegue até o diretório do frontend.
2. Instale as dependências:
   ```shell
   pnpm install
   ```
3. Execute o servidor de desenvolvimento:
  ```shell
  pnpm dev
  ```
4. Para construir o projeto:
  ```shell
  pnpm build
  ```
5. Para executar os testes:
  ```shell
  pnpm test:unit
  pnpm test:e2e
  ```

## Contribuindo
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests para melhorias ou correções.

## Desenvolvedor
<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/8467131?v=4" width="100px;" alt="Foto do Rafael Vieira no GitHub"/><br>
        <sub>
          <b>Rafael Vieira</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
<table>
  <tr>
    <a href="https://www.linkedin.com/in/rafaelvieira-s/">
      <img alt="linkedin" src="https://img.shields.io/twitter/url?label=Linkedin&logo=linkedin&style=social&url=https%3A%2F%2Fwww.linkedin.com%2Fin%2Frafaelvieira-s%2F">
    </a>
    <a href="https://medium.com/@rafael">
      <img alt="medium" src="https://img.shields.io/twitter/url?label=Medium&logo=medium&style=social&url=https%3A%2F%2Fmedium.com%2F%40rafael.">
    </a>
    <a href = "mailto:rafaelrok25@gmail.com">
      <img alt="gmail" src="https://img.shields.io/twitter/url?label=gmail&logo=gmail&style=social&url=https%3A%2F%2Fmail.google.com%2F">
    </a>
  </tr>
</table>


## 📝 Licença

Esse projeto está sob licença. Veja o arquivo [LICENÇA](LICENSE.md) para mais detalhes.

##  Versões do README

[⬆ Voltar ao topo](#Introdução)<br>
