# RestAssured

Com RestAssured podemos testar serviços Rest em Java de uma forma mais prática, ele nos permite criar chamadas HTTP como se fosse um cliente acessando a API. 

O RestAssured suporta os métodos POST, GET, PUT, DELETE, OPTIONS, PATCH e HEAD e pode servir para avaliar o retorno dessas requisições.

## Execução

Para executar testes com RestAssured, basta realizar a importação da dependência:

```
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>4.3.0</version>
    <scope>test</scope>
</dependency>
```

> Há diversas outras dependências que podemos acrescentar em um projeto restassured, tudo depende da necessidade, porém para esse caso utilizaremos apenas uma. Recomendo visualizar outros projetos aqui do repositório com uma arquitetura mais robusta, para melhor esclarecimento.


Pelo Maven, podemos executar os testes através do comando:
> Mvn test
