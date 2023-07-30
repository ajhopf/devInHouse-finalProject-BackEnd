# M3-Projeto-2-DevinHouse

Essa API é um sistema de gestão hospitalar. Este software tem como objetivo otimizar o processo de gerenciamento

- [Tecnologias](#tech)
- [Como Iniciar](#start)
- [Melhorias](#melhorias)
- [Como usar](#usar)
- [Endpoints](#endpoints)
  - [Logs](#logs)
  - [Identidade Visual](#identidade-visual)
  - [Exercícios](#exercícios)
  - [Prontuários](#prontuários)
  - [Pacientes](#paciente)
  - [Dieta](#dieta)
  - [Consultas](#consultas)
  - [Autenticação](#autenticação)
  - [Usuário](#usuário)
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

<a id="endpoints"></a>
## Descrição dos endpoints

### Logs

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

### Identidade Visual

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

### Exercícios

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

### Prontuários

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

### Paciente

```
Descrição: Este endpoint serve para cadastrar um novo paciente.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/pacientes
Método HTTP: POST
Autenticado: Sim
Roles: any
Corpo:
{
  "name": "string - mínimo 8 / máximo 64 caracteres - obrigatório",
  "gender": "string - obrigatório",
  "dob": "data válida - obrigatório",
  "cpf": "string - formato: 999.999.999-99 - obrigatório",
  "rg": "string - máximo 20 caracteres - obrigatório",
  "civilStatus": "CIVIL_STATUS_SINGLE, CIVIL_STATUS_MARRIED, CIVIL_STATUS_DIVORCED, CIVIL_STATUS_WIDOWED - obrigatório",
  "phone": "string - formato (99) 9 9999-99999 - obrigatório",
  "email": "email válido - obrigatório",
  "nationality": "string - mínimo 8 / máximo 64 caracteres - obrigatório",
  "emergencyContact": "string - formato (99) 9 9999-99999 - obrigatório",
  "alergies": [
    "array de string - não obrigatório"
  ],
  "specialCare": [
    "array de string - não obrigatório"
  ],
  "healthInsurance": "string - não obrigatório",
  "healthInsuranceNumber": "string - não obrigatório",
  "healthInsuranceExpirationDate": "data válida - obrigatório",
  "address": {
    "cep": "string - formato 12345-123 - obrigatório",
    "city": "string - obrigatório",
    "state": "string - mínimo 2 / máximo 2  caracteres - obrigatório",
    "street": "string - obrigatório",
    "houseNumber": "string - obrigatório",
    "complement": "string - não obrigatório",
    "district": "string - obrigatório",
    "referencePoint": "string - não obrigatório"
  }
}

Exemplo de resposta:

Status: 201
Corpo:
{
  "id": 23,
  "name": "stringst",
  "gender": "string",
  "dob": "2023-07-28",
  "cpf": "525.864.786-84",
  "rg": "string",
  "civilStatus": "CIVIL_STATUS_SINGLE",
  "phone": "(89) 4 7685-17855",
  "email": "andre@hotmail.com",
  "nationality": "stringst",
  "emergencyContact": "(85) 8 5481-99322",
  "alergies": [
    "string"
  ],
  "specialCare": [
    "string"
  ],
  "healthInsurance": "string",
  "healthInsuranceNumber": "string",
  "healthInsuranceExpirationDate": "2023-07-28",
  "address": {
    "id": 23,
    "cep": "53366-028",
    "city": "string",
    "state": "string",
    "street": "string",
    "houseNumber": "string",
    "complement": "string",
    "district": "string",
    "referencePoint": "string"
  },
  "role": "ROLE_PACIENT",
  "isActive": true
}

```

```
Descrição: Este endpoint serve para buscar todos os pacientes.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/pacientes
Método HTTP: GET
Autenticado: Sim
Roles: any

Exemplo de resposta:

Status: 200
Corpo:
[
  {
    "id": 1,
    "name": "Rachel Lunardi",
    "gender": "feminino",
    "dob": "1993-03-29",
    "cpf": "413.029.515-31",
    "rg": "8098963167",
    "civilStatus": "CIVIL_STATUS_SINGLE",
    "phone": "(45) 1 0762-83670",
    "email": "rachel@gmail.com",
    "nationality": "brasileira",
    "emergencyContact": "(48) 9 9903-09596",
    "alergies": [
      "glúten",
      "formiga"
    ],
    "specialCare": [
      "paciente com sensibilidade ao toque"
    ],
    "healthInsurance": null,
    "healthInsuranceNumber": null,
    "healthInsuranceExpirationDate": null,
    "address": {
      "id": 1,
      "cep": "88063-431",
      "city": "Florianópolis",
      "state": "SC",
      "street": "Servidão Arara Azul",
      "houseNumber": "25",
      "complement": "casa 04",
      "district": "Campeche",
      "referencePoint": "Perpendicular a rua catavento"
    },
    "role": "ROLE_PACIENT",
    "isActive": true
  },
  {
    "id": 2,
    "name": "Luis Carlos Silveira",
    "gender": "masculino",
    "dob": "1963-07-24",
    "cpf": "123.456.789-88",
    "rg": "4848715484",
    "civilStatus": "CIVIL_STATUS_MARRIED",
    "phone": "(48) 9 9999-99911",
    "email": "luiz@hotmail.com",
    "nationality": "brasileiro",
    "emergencyContact": "(48) 9 9999-99911",
    "alergies": [
      "chocolate"
    ],
    "specialCare": [],
    "healthInsurance": null,
    "healthInsuranceNumber": null,
    "healthInsuranceExpirationDate": null,
    "address": {
      "id": 21,
      "cep": "91920-010",
      "city": "Porto Alegre",
      "state": "RS",
      "street": "Rua Álvaro Guterres",
      "houseNumber": "335",
      "complement": null,
      "district": "Tristeza",
      "referencePoint": null
    },
    "role": "ROLE_PACIENT",
    "isActive": true
  }
]
```

```
Descrição: Este endpoint serve para buscar um paciente pelo id.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/pacientes/{id do paciente}
Método HTTP: GET
Autenticado: Sim
Roles: any
Parametro: id do paciente

Exemplo de resposta:

Status: 200
Corpo:
[
  {
    "id": 1,
    "name": "Raquel Contreiras",
    "gender": "feminino",
    "dob": "1993-03-29",
    "cpf": "413.029.515-31",
    "rg": "8098963167",
    "civilStatus": "CIVIL_STATUS_SINGLE",
    "phone": "(45) 1 0762-83670",
    "email": "raquel@gmail.com",
    "nationality": "brasileira",
    "emergencyContact": "(48) 9 9903-09596",
    "alergies": [
      "glúten",
      "formiga"
    ],
    "specialCare": [
      "paciente com sensibilidade ao toque"
    ],
    "healthInsurance": null,
    "healthInsuranceNumber": null,
    "healthInsuranceExpirationDate": null,
    "address": {
      "id": 1,
      "cep": "88063-431",
      "city": "Florianópolis",
      "state": "SC",
      "street": "Servidão Arara Azul",
      "houseNumber": "300",
      "complement": "casa b",
      "district": "Campeche",
      "referencePoint": null
    },
    "role": "ROLE_PACIENT",
    "isActive": true
  }
]
```

```
Descrição: Este endpoint serve para atualizar um paciente.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/pacientes/{id do paciente}
Método HTTP: PUT
Autenticado: Sim
Roles: any
Parametro: id do paciente
Corpo:

{
  "name": "string - mínimo 8 / máximo 64 caracteres - obrigatório",
  "gender": "string - obrigatório",
  "dob": "data válida - obrigatório",
  "civilStatus": "CIVIL_STATUS_SINGLE, CIVIL_STATUS_MARRIED, CIVIL_STATUS_DIVORCED, CIVIL_STATUS_WIDOWED - obrigatório",
  "phone": "string - formato (99) 9 9999-99999 - obrigatório",
  "email": "email válido - obrigatório",
  "nationality": "string - mínimo 8 / máximo 64 caracteres - obrigatório",
  "emergencyContact": "string - formato (99) 9 9999-99999 - obrigatório",
  "alergies": [
    "array de string - não obrigatório"
  ],
  "specialCare": [
    "array de string - não obrigatório"
  ],
  "healthInsurance": "string - não obrigatório",
  "healthInsuranceNumber": "string - não obrigatório",
  "healthInsuranceExpirationDate": "data válida - obrigatório",
  "address": {
    "cep": "string - formato 12345-123 - obrigatório",
    "city": "string - obrigatório",
    "state": "string - mínimo 2 / máximo 2  caracteres - obrigatório",
    "street": "string - obrigatório",
    "houseNumber": "string - obrigatório",
    "complement": "string - não obrigatório",
    "district": "string - obrigatório",
    "referencePoint": "string - não obrigatório"
  }
}

Exemplo de resposta:

Status: 200
[
  {
    "id": 1,
    "name": "Raquel Contreiras",
    "gender": "feminino",
    "dob": "1993-03-29",
    "cpf": "413.029.515-31",
    "rg": "8098963167",
    "civilStatus": "CIVIL_STATUS_SINGLE",
    "phone": "(45) 1 0762-83670",
    "email": "raquel@gmail.com",
    "nationality": "brasileira",
    "emergencyContact": "(48) 9 9903-09596",
    "alergies": [
      "glúten",
      "formiga"
    ],
    "specialCare": [
      "paciente com sensibilidade ao toque"
    ],
    "healthInsurance": null,
    "healthInsuranceNumber": null,
    "healthInsuranceExpirationDate": null,
    "address": {
      "id": 1,
      "cep": "88063-431",
      "city": "Florianópolis",
      "state": "SC",
      "street": "Servidão Arara Azul",
      "houseNumber": "300",
      "complement": "casa b",
      "district": "Campeche",
      "referencePoint": null
    },
    "role": "ROLE_PACIENT",
    "isActive": true
  }
]
```

```
Descrição: Este endpoint serve para deletar um paciente.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/pacientes/{id do paciente}
Método HTTP: DELETE
Autenticado: Sim
Roles: any
Parametro: id do paciente

Exemplo de resposta:

Status: 202

```

### Dieta

```
Descrição: Este endpoint serve para cadastrar uma nova dieta.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/dietas
Método HTTP: POST
Autenticado: Sim
Roles: any
Corpo:
{
  "dietName": "string - minimo 5 / máximo 100 caracteres - obrigatório",
  "dietDate": "data válida - obrigatório",
  "time": "horário válido - formato "HH:mm:ss - obrigatório",
  "dietType": "LOW_CARB, DASH, PALEO, KETO, DUKAN, MEDITERRANEAN, OTHER - obrigatório",
  "description": "string - não obrigatório",
  "status": boolean,
  "pacientId": id do paciente
}

Exemplo de resposta:

Status: 201
Corpo:
{
  "id": 1,
  "dietName": "string",
  "dietDate": "2023-07-28",
  "time": "11:11:11",
  "dietType": "LOW_CARB",
  "description": "string",
  "status": true,
  "pacientId": 1
}
```

```
Descrição: Este endpoint serve para listar todas as dietas ou dietas pelo nome do paciente.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/dietas - busca de todas dietas
URL: http://localhost:8080/api/dietas?pacientName=andre - busca de dietas de pacientes que tenham 'andre' no nome
Método HTTP: GET
Autenticado: Sim
Roles: any
Query Param: nome do paciente - opcional

Exemplo de resposta:

Status: 200
Corpo:
[
  {
    "id": 1,
    "dietName": "string",
    "dietDate": "2023-07-28",
    "time": "06:10:11",
    "dietType": "PALEO",
    "description": "string",
    "status": true,
    "pacientId": 1
  },
  {
    "id": 2,
    "dietName": "string",
    "dietDate": "2023-07-28",
    "time": "11:11:11",
    "dietType": "LOW_CARB",
    "description": "string",
    "status": true,
    "pacientId": 22
  }
]
```

```
Descrição: Este endpoint serve para atualizar uma dieta pelo id.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/dietas/{id da dieta}
Método HTTP: PUT
Autenticado: Sim
Roles: any
Parametro: id da dieta
Corpo:
{
  "dietName": "string - minimo 5 / máximo 100 caracteres - obrigatório",
  "dietDate": "data válida - obrigatório",
  "time": "string - horário válido formato 'HH:mm:ss' - obrigatório",
  "dietType": "LOW_CARB, DASH, PALEO, KETO, DUKAN, MEDITERRANEAN, OTHER - obrigatório",
  "description": "string - não obrigatório"
}

Exemplo de resposta:

Status: 200
Corpo:
{
  "id": 1,
  "dietName": "string",
  "dietDate": "2023-07-28",
  "time": "18:33:00",
  "dietType": "LOW_CARB",
  "description": "string",
  "status": true,
  "pacientId": 1
}
```

```
Descrição: Este endpoint serve para deletar uma dieta pelo id.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/dietas/{id da dieta}
Método HTTP: DELETE
Autenticado: Sim
Roles: any
Parametro: id da dieta

Exemplo de resposta:

Status: 202
Corpo:
```

### Consultas

```
Descrição: Este endpoint serve para cadastrar uma nova consulta.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/consultas/cadastrar
Método HTTP: POST
Autenticado: Sim
Roles: ADMIN, DOCTOR
Corpo:
{
  "appointmentReason": "strings - min 8 / máximo 64 caracteres - obrigatório",
  "appointmentDate": "data válida - obrigatório",
  "time": "string - formato 'HH:mm:ss' - obrigatório",
  "problemDescription": "string - min 16 / máximo 1024 caracteres - obrigatório",
  "dosageAndPrecautions": "string - min 16 / máximo 256 caracteres - obrigatório",
  "isActive": boolean - obrigatório,
  "pacientId": "number - obrigatório"
  "medicineId": "number - obrigatório"
}

Exemplo de resposta:

Status: 201
Corpo:
{
  "id": 64,
  "appointmentReason": "stringst",
  "appointmentDate": "2023-07-28",
  "time": "11:11:11",
  "problemDescription": "stringstringstri",
  "dosageAndPrecautions": "stringstringstri",
  "isActive": true,
  "pacientId": 1,
  "medicineId": 1
}
```

```
Descrição: Este endpoint serve para listar todas as consultas ou consultas pelo id do paciente.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/consultas - busca de todas consultas
URL: http://localhost:8080/api/consultas?pacientId=1' - busca de consultas do paciente com id 1
Método HTTP: GET
Autenticado: Sim
Roles: ADMIN, DOCTOR
Param: id do paciente - opcional

Exemplo de resposta:

Status: 200
Corpo:
[
  {
    "id": 61,
    "appointmentReason": "dor no quadril",
    "appointmentDate": "2023-07-28",
    "time": "09:14:00",
    "problemDescription": "stringststringststring",
    "dosageAndPrecautions": "stringststringststring",
    "isActive": true,
    "pacientId": 1,
    "medicineId": null
  },
  {
    "id": 63,
    "appointmentReason": "dor de joelho",
    "appointmentDate": "2023-07-28",
    "time": "14:28:00",
    "problemDescription": "stringststringststring",
    "dosageAndPrecautions": "stringststringststring",
    "isActive": true,
    "pacientId": 1,
    "medicineId": 21
  }
]  
```

```
Descrição: Este endpoint serve para atualizar uma consulta pelo id da consulta.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/consultas/{id da consulta}
Método HTTP: PUT
Autenticado: Sim
Roles: ADMIN, DOCTOR
Param: id da consulta - obrigatório
Corpo:
{
  "appointmentReason": "strings - min 8 / máximo 64 caracteres - obrigatório",
  "appointmentDate": "data válida - obrigatório",
  "time": "string - formato 'HH:mm:ss' - obrigatório",
  "problemDescription": "string - min 16 / máximo 1024 caracteres - obrigatório",
  "dosageAndPrecautions": "string - min 16 / máximo 256 caracteres - obrigatório",
  "isActive": boolean - obrigatório,
  "pacientId": "number - obrigatório"
  "medicineId": "number - obrigatório"
}

Exemplo de resposta:

Status: 200
Corpo:
[
  {
  "id": 61,
  "appointmentReason": "stringst",
  "appointmentDate": "2023-07-28",
  "time": "12:12:12",
  "problemDescription": "stringstringstri",
  "dosageAndPrecautions": "stringstringstri",
  "isActive": true,
  "pacientId": 1,
  "medicineId": 1
 }
]  
```

```
Descrição: Este endpoint serve para deletar uma consulta pelo id da consulta.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/consultas/{id da consulta}
Método HTTP: DELETE
Autenticado: Sim
Roles: ADMIN, DOCTOR
Param: id da consulta - obrigatório

Exemplo de resposta:

Status: 202
 
```

### Autenticação

```
Descrição: Este endpoint serve para fazer o login na aplicação.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/usuarios/login
Método HTTP: DELETE
Autenticado: Sim
Roles: any
Corpo: 
{
  "email": "string - email válido - obrigatório",
  "password": "string - obrigatório"
}

Exemplo de resposta:

Status: 200

{
  "name": "Admin",
  "role": "ROLE_ADMIN",
  "photoUrl": null,
  "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpYXQiOjE2OTA1ODE3MjEsImV4cCI6MTY5MTE4MTcyMX0.osFtYm5rwO7YoHdKeRIrlP1DwSg6qqNi4aTx_V8aH8c"
}

```

### Usuário

```
Descrição: Este endpoint serve para fazer o reset da senha do usuário.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/usuarios/resetarsenha
Método HTTP: put
Autenticado: Não
Corpo: 
{
  "id": numero - id do usuário - obrigatório,
  "email": "string - email valido - obrigatório",
  "password": "string - obrigatório"
}

Exemplo de resposta:

Status: 200

```


```
Descrição: Este endpoint serve para receber o id do usuário pelo email.

Autor: André Hopf

Exemplo de requisição:
URL: http://localhost:8080/api/usuarios/{ email }
Método HTTP: GET
Autenticado: Não
Parametro: email do usuário

Exemplo de resposta:

Status: 200

{
  "id": 1,
  "email": "string@email.com"
}

```
