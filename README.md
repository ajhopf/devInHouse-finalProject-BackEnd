# M3-Projeto-2-DevinHouse

Essa API é um sistema de gestão hospitalar. Este software tem como objetivo otimizar o processo de gerenciamento

- [Tecnologias](#tech)
- [Como Iniciar](#start)
- [Melhorias](#melhorias)
- [Como usar](#usar)
  <a id="tech"></a>

Este projeto faz parte dos projetos de avaliação do curso DEVInHouse.


# Tecnologias

O projeto desenvolvido utiliza as seguintes tecnologias:
- [Java](https://www.java.com/en/)
- [Maven](https://maven.apache.org/)
- [Spring](https://spring.io/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring WEB](https://spring.io/guides/gs/serving-web-content/)
- [MapStruct](https://github.com/mapstruct/mapstruct)
- [Lombok](https://projectlombok.org/)
- [SpringDoc](https://springdoc.org/)
- [Git e GitHub](https://github.com/)
- [Oracle-db](https://www.oracle.com/br/database/)

<a id="start"></a>

# Como Iniciar

#### **Pré-requisitos**

- Possuir o JDK17 e Maven instalados na sua máquina.

```bash
# Clone o Repositório
$ git clone https://github.com/Leandro-Michail-Krikis/M3-Projeto-1-DevinHouse
```

```bash
# Entre na pasta do projeto
$ cd M3-Projeto-1-DevinHouse
```

```bash
# Na pasta raiz execute esse comando para comilar o projeto.
$ mvn clean install
```

```bash
# Entre na pasta target
$ cd target
```

```bash
# Execute o seguinte comando para iniciar o projeto localmente
$ java -jar .\m3p1-0.0.1-SNAPSHOT.jar
```
- Usando Intellij IDEA.
```bash
# Instala lombok no intelliJ
# Abre a pasta como projeto com o intelliJ
# Depois de alguns segundos vai parecer de carregar como projeto Maven. Aceita
# Agora so clicar no canto direito superior no botao de iniciar
```

<a id="melhorias"></a>
# Melhorias Futuras
- Adicionar RefreshToken para oferecer uma melhor segurança
- Adicionar bcrypt e uuid dos token para uma melhor privacidade dos dados dos usuarios
- Proteção contra ataques como injeção de SQL ou XSS, e garantindo que as dependências do projeto estejam atualizadas para mitigar vulnerabilidades conhecidas.

<a id="usar"></a>
# Como usar
#### Poderia utilizar o swagger acessando esse link.
http://localhost:8080/api/docs

## Descrição dos endpoints


```
Descrição: Este endpoint permite realizar a busca de todos os logs.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/logs/listar
  Método HTTP: GET
  Auteticado: Sim
  Roles: Admin
  Parâmetros:
    Headers:
      `page` (opcional): Esse parametro define qual pagina vai ser retornada.
      `page-size` (opcional): Esse parametro define quantos registros vão ser retornados por pagina.


Exemplo de resposta:
  {
  "totalElements": 0,
  "totalPages": 0,
  "size": 0,
  "content": [
    {
      "id": 0,
      "description": "string",
      "createdDate": "2023-07-22T19:49:48.521Z",
      "logType": "SUCCESS",
      "logOrigin": "FRONT_END"
    }
  ],
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": true,
    "unsorted": true
  },
  "first": true,
  "last": true,
  "numberOfElements": 0,
  "pageable": {
    "offset": 0,
    "sort": {
      "empty": true,
      "sorted": true,
      "unsorted": true
    },
    "pageNumber": 0,
    "pageSize": 0,
    "unpaged": true,
    "paged": true
  },
  "empty": true
}
```

```
Descrição: Este endpoint permite realizar cadastro de log.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/logs/cadastrar
  Método HTTP: GET
  Auteticado: Sim
  Roles: qualquer
  Corpo:
    {
      "description": "Descrição do log",
      "logType": "SUCCESS, INFO, ERROR"
    }

```
```
Descrição: Este endpoint permite atualizar a identidade visual do sistema.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/config/sistema
  Método HTTP: POST
  Auteticado: Sim
  Roles: Admin
  Corpo:
    {
      "companyName": "Nome da empresa",
      "logoUrl": "Url sendo na web ou no formato base64",
      "primaryColor": "Cor primaria",
      "secondaryColor": "Cor segundaria",
      "fontColor": "Cor da fonte"
    }

```
```
Descrição: Este endpoint permite buscar identidade visual do sistema.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/config/sistema
  Método HTTP: GET
  Auteticado: não
  Roles: qualquer
  
Exemplo reposta:
  {
    "companyName": "Nome da empresa",
    "logoUrl": "Url sendo na web ou no formato base64",
    "primaryColor": "Cor primaria",
    "secondaryColor": "Cor segundaria",
    "fontColor": "Cor da fonte"
  }
```
```
Descrição: Este endpoint permite resetar a identidade visual do sistema.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/config/sistema/resetar
  Método HTTP: POST
  Auteticado: Sim
  Roles: Admin
 
```
```
Descrição: Este endpoint serve para criar um exercicio para o paciente.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/exercicios
  Método HTTP: POST
  Auteticado: Sim
  Roles: Admin, DOCTOR, NURSE
  Corpo:
    {
      "description": "string",
      "dateCreated": "2023-07-25",
      "timeCreated": "3:17:51",
      "exerciseType": "AEROBIC_ENDURANCE",
      "timesPerWeek": 1.99,
      "exerciseSeriesName": "stringstri",
      "patientId": 1,
      "status": true
    }
  
Exemplo resposta:
  Url que possa buscar o exercicio
 
```
```
Descrição: Este endpoint serve para buscar todos os exercicios.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/exercicios
  Método HTTP: GET
  Auteticado: Sim
  Roles: Admin, DOCTOR, NURSE
  
Exemplo resposta:
  [
    {
      "id": 0,
      "description": "string",
      "dateCreated": "2023-07-25",
      "timeCreated": {
        "hour": 0,
        "minute": 0,
        "second": 0,
        "nano": 0
      },
      "exerciseType": "AEROBIC_ENDURANCE",
      "timesPerWeek": 0,
      "exerciseSeriesName": "string",
      "patientId": 0,
      "status": true
    }
  ]
```
```
Descrição: Este endpoint serve para buscar os exercicios pelo nome do paciente.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/exercicios/{nome do paciente}
  Método HTTP: GET
  Auteticado: Sim
  Roles: Admin, DOCTOR, NURSE
  Parametros: Nome do paciente
  
Exemplo resposta:
  [
    {
      "id": 0,
      "description": "string",
      "dateCreated": "2023-07-25",
      "timeCreated": {
        "hour": 0,
        "minute": 0,
        "second": 0,
        "nano": 0
      },
      "exerciseType": "AEROBIC_ENDURANCE",
      "timesPerWeek": 0,
      "exerciseSeriesName": "string",
      "patientId": 0,
      "status": true
    }
  ]
```
```
Descrição: Este endpoint serve para buscar os exercicios pelo id do paciente.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/exercicios/patientId/{id do paciente}
  Método HTTP: GET
  Auteticado: Sim
  Roles: Admin, DOCTOR, NURSE
  Parametros: id do paciente
  
Exemplo resposta:
  [
    {
      "id": 0,
      "description": "string",
      "dateCreated": "2023-07-25",
      "timeCreated": {
        "hour": 0,
        "minute": 0,
        "second": 0,
        "nano": 0
      },
      "exerciseType": "AEROBIC_ENDURANCE",
      "timesPerWeek": 0,
      "exerciseSeriesName": "string",
      "patientId": 0,
      "status": true
    }
  ]
```
```
Descrição: Este endpoint serve deletar um exercicio pelo id.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/exercicios/{id do exercicio}
  Método HTTP: DELETE
  Auteticado: Sim
  Roles: Admin, DOCTOR, NURSE
  Parametros: id do exercicio
  
Exemplo resposta:
  status: 202
```
```
Descrição: Este endpoint serve para atualizar um exercicio para o paciente.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/exercicios/{id do exercicio}
  Método HTTP: PUT
  Auteticado: Sim
  Roles: Admin, DOCTOR, NURSE
  Parametros: id do exercicio
  Corpo:
    {
      "description": "string",
      "dateCreated": "2023-07-25",
      "timeCreated": "3:17:51",
      "exerciseType": "AEROBIC_ENDURANCE",
      "timesPerWeek": 1.99,
      "exerciseSeriesName": "stringstri",
      "patientId": 1,
      "status": true
    }
  
Exemplo resposta:
  status: 200
 
```
```
Descrição: Este endpoint serve para buscar todos os prontuarios pelo id do paciente.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/prontuario/{id do paciente}
  Método HTTP: GET
  Auteticado: Sim
  Roles: Admin, DOCTOR, NURSE
  Parametros: id do paciente
  
Exemplo resposta:
  {
    "name": "stringst",
    "healthInsurance": "string",
    "healthInsuranceNumber": "string",
    "alergies": [
      "string"
    ],
    "specialCare": [
      "string"
    ]
  } 
```
```
Descrição: Este endpoint serve para buscar todos os prontuarios.

Autor: Leandro da Silva

Exemplo de requisição:
  Url: http://localhost:8080/api/prontuario
  Método HTTP: GET
  Auteticado: Sim
  Roles: Admin, DOCTOR, NURSE
  
Exemplo resposta:
  [
    {
      "name": "stringst",
      "healthInsurance": "string",
      "healthInsuranceNumber": "string",
      "alergies": [
        "string"
      ],
      "specialCare": [
        "string"
      ]
    },
    {
      "name": "stringst",
      "healthInsurance": "string",
      "healthInsuranceNumber": "string",
      "alergies": [
        "string"
      ],
      "specialCare": [
        "string"
      ]
    }
  ] 
```