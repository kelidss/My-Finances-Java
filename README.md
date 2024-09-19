# My Finances

Projeto da faculdade para trabalho de Java.

## Como rodar o projeto

### Docker

1. Configure um arquivo `.env` seguindo o `.env-example`.
2. Rodar o comando `docker compose up --build`.

### Wrapper do Maven (Linux/Mac)

1. Configure um arquivo `.env` seguindo o `.env-example`.
2. Instalar dependencias com `./mvnw clean install`
3. Rodar o projeto `./mvnw spring-boot:run`.

### Wrapper do Maven (Windows)

1. Configure um arquivo `.env` seguindo o `.env-example`.
2. Instalar dependencias com `mvnw.cmd clean install`
3. Rodar o projeto `mvnw.cmd spring-boot:run`.

#### OBS: Lembre-se que o Wrapper do Maven não rodará o banco, isso é possível somente com o Docker Compose

## Como usar o projeto

1. Para acessar, use o endereço `localhost:8080`.
2. Para conectar ao DB, use as variáveis do `.env` criado.

## Contribuidores

Keliane dos Santos Soares [`kelidss`](https://github.com/kelidss/)  
Marcos Vinicius Morais [`moraisz`](https://github.com/moraisz/)
