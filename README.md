# Surittec_Test_Backend

## 1) Instalação e Execução

### 1.1) Clonar repositório do projeto e instalar suas dependências

```
$ git clone https://github.com/brunnooliver7/Surittec_Test_Backend
$ cd Surittec_Test_Backend
$ mvn clean install
```
### 1.2) Iniciar o servidor

```
$ mvn spring-boot:run
```

### 1.3) Docker

Também é possível executar a API através de containers. Para isso, certifique-se que o arquivo executável .jar foi gerado na pasta <i>target</i> e que a porta 3306 esteja disponível na máquina.

```
$ docker-compose up
```

## 2) Consumo da API

Com a API em funcionamento, o primeiro passo é registrar um ou mais clientes. Você pode fazer isso através de um software de API client (e.g., Postman, Insomnia), do serviço Swagger disponível nesta API ou através da aplicação frontend deste projeto (https://github.com/brunnooliver7/Surittec_Test_Frontend). Para conferir os detalhes dos endpoints e models pelo serviço Swagger, basta acessar:

```
http://localhost:8080/swagger-ui/index.html
```
