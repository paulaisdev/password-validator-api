# 🚀 Password Validator API

Esta é uma API escalável e extensível projetada para garantir a segurança de senhas através de regras de validação. 
---

<div align="center">
	<img width="50" src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/rest.png" alt="REST" title="REST"/>
	<img width="50" src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/swagger.png" alt="Swagger" title="Swagger"/>
	<img width="50" src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/java.png" alt="Java" title="Java"/>
	<img width="50" src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/spring_boot.png" alt="Spring Boot" title="Spring Boot"/>
	<img width="50" src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/maven.png" alt="Maven" title="Maven"/>
	<img width="50" src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/junit.png" alt="JUnit" title="JUnit"/>
  <img width="50" src="https://img.shields.io/badge/Mockito-FF8000?style=for-the-badge&logo=mockito&logoColor=white" alt="Mockito" title="Mockito"/>
  <img width="50" src="https://img.shields.io/badge/Apache%20JMeter-D22128?style=for-the-badge&logo=apache&logoColor=white" alt="JMeter" title="JMeter"/>
  <img width="50" src="https://www.vectorlogo.zone/logos/redis/redis-icon.svg" alt="Caffeine" title="Caffeine"/>
</div>



---
**Principais Características:**

- Valida senhas com base em regras de negócio
- Arquitetura extensível para adição de novas regras
- Desenvolvida seguindo os princípios **SOLID** e **Clean Code**
- Testada com testes unitários e de integração
- Documentação com **Swagger UI**.

---

## **🔑 Requisitos Funcionais**

**Nove ou mais caracteres**  
   - A senha deve ter pelo menos **9 caracteres** para ser considerada válida.

**Ao menos 1 número**  
   - Deve conter pelo menos **1 número** (`0-9`).

**Ao menos 1 letra minúscula**  
   - Pelo menos **1 caractere alfabético em minúsculo** (`a-z`).

**Ao menos 1 letra maiúscula**  
   - Pelo menos **1 caractere alfabético em maiúsculo** (`A-Z`).

**Ao menos 1 caractere especial**  
   - Deve conter pelo menos **1 caractere especial** dos seguintes: `!@#$%^&*()-+`.

**Sem caracteres repetidos**  
   - **Nenhum caractere pode se repetir** dentro da mesma senha.

---

## **🗺️ Mapa da Documentação**

1. [🛠 Instalação e Configuração](#-instalação-e-configuração)
2. [📜 Documentação da API](#-documentação-da-api)
3. [📐 Design da API](#-design-da-api)
4. [🧪 Testes](#-testes)
5. [📊 Análise de Testes de Carga](#-análise-de-testes-de-carga)
6. [📈 Métricas](#-métricas)
7. [🔒 Segurança na API](#-segurança-na-api)
8. [🚀 Informações Adicionais](#-informações-adicionais)

---

## **🛠 Instalação e Configuração**

### **Requisitos:**

- **Java 11+**
- **Maven 3.8+**
- **IDE** (de sua preferência, para o projeto foi utilizao o Intellij)

### **Passos para Executar:**

Clone o repositório:

   ```bash
   git clone https://github.com/paulaisdev/password-validator-api.git
   cd password-validator-api
   ```

Execute o projeto com o comando abaixo:

   ```bash
   ./mvnw spring-boot:run
   ```

Acesse a API:

   - **URL**: `http://localhost:8080`
   - **Swagger UI**: `http://localhost:8080/swagger-ui.html`

---

## **📜 Documentação da API**

A documentação da API foi gerada automaticamente usando **Swagger UI** e está disponível para consulta e testes.

### **Acessando a Documentação**

Após iniciar o projeto, acesse:

- [Swagger UI - Documentação](http://localhost:8080/swagger-ui.html)

### **Endpoints Disponíveis:**

| Método HTTP | Endpoint                 | Descrição                            |
| ----------- | ------------------------ | ------------------------------------ |
| `POST`      | `/api/password/validate` | Valida uma senha baseada nas regras. |

#### **Exemplo de Requisição:**

```http
POST /api/password/validate HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "password": "AbTp9!fok"
}
```

#### **Exemplo de Respostas:**

- **Senha Válida:**
  ```json
  true
  ```
- **Senha Inválida:**
  ```json
  false
  ```
- **Erro (Bad Request):**
  ```json
  {
    "error": "Password contains invalid characters"
  }
  ```

---
## **🔎 Teste local**

Para testar localmente, além de utilizar a interface do swagger, está disponível a collection do postman que disponibilizo <a href="./assets/PasswordValidatorAPI.postman_collection.json" target="_blank">aqui</a>

---

## **📐 Design da API**

A API segue a seguinte estrutura de pacotes (com seus respectivos arquivos):

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── passwordvalidator/
│   │           ├── api/
│   │           │   ├── controller/
│   │           │   │   └── PasswordController.java
│   │           │   ├── dto/
│   │           │   │   └── PasswordRequest.java
│   │           │   ├── exception/
│   │           │   │   └── PasswordValidationException.java
│   │           │   ├── metrics/
│   │           │   │   └── PasswordMetrics.java
│   │           │   ├── service/
│   │           │   │   ├── CachedPasswordValidatorService.java
│   │           │   │   └── validator/
│   │           │   │       ├── PasswordValidator.java
│   │           │   │       ├── LengthRule.java
│   │           │   │       ├── SpecialCharacterRule.java
│   │           │   │       └── UniqueCharacterRule.java
│   │           │   └── util/
│   │           │       └── InputSanitizer.java
│   │           └── config/
│   │               ├── TestSecurityConfig.java
│   │               └── PasswordRulesConfig.java
│   └── resources/
│       ├── application.yml
│       ├── messages.properties
│       └── static/
├── test/
│   └── java/
│       └── com/
│           └── passwordvalidator/
│               ├── api/
│               │   ├── controller/
│               │   │   └── PasswordControllerTest.java
│               │   └── service/
│               │       └── CachedPasswordValidatorServiceTest.java
│               └── metrics/
│                   └── PasswordMetricsTest.java
└── pom.xml
```


### **Princípios que guiaram a implementação:**

**Arquitetura RESTful**:

   - Endpoints definidos e uso de HTTP status codes

**Estrutura Modular**:

   - Controladores localizados em `com.passwordvalidator.api.controller`
   - Regras de validação centralizadas em `com.passwordvalidator.api.service.validator`
   - Configuração de regras em `PasswordRulesConfig`

**Extensibilidade e Manutenção**:

   - Regras encapsuladas em classes independentes, como `LengthRule` e `SpecialCharacterRule`
   - Injeção de dependências facilita a troca e adição de regras sem impacto no código existente

**Tratamento de Erros**:

   - Centralizado para respostas consistentes, por exemplo: `PasswordValidationException` para erros customizados

---

## **🧪 Testes**

### **Execução de Testes:**

Execute todos os testes com:

```bash
./mvnw test
```

### **Cobertura dos Testes:**

- **Testes Unitários**: Validam cada regra individualmente
- **Testes de Integração**: Garantem que os endpoints funcionam como esperado
- **Mocking**: Dependências são simuladas para isolar componentes

### **📊 Iteração de Melhorias**

Após a primeira versão do projeto ser criada, foram criados dois planos de análise de teste de carga, para observar a performance da API. Este plano visava compreender quais melhorias poderiam ser aplicadas. Para isso, uma consulta foi realizada no Copilot, para definir uma base dos valores a serem testados:

<img src="./assets/copilot-analise-carga.png" alt="Conversa com o copilot, que sugere o número de 2.2285 senhas a serem criadas por minuto, em serviços bancários" width="500" style="display: block; margin: auto;"/>

### **📊 Primeiro resultado da Análise de Testes de Carga (com JMeter)**

**Gargalos Identificados:**

1. Escalabilidade limitada sob alta carga
2. Necessidade de cache para resultados repetidos
3. Short-circuit nas validações para melhorar a performance

**Acionáveis:**

- Implementação de cache com Caffeine, armazenando validações de senha repetidas, reduzindo a carga de processament
- Melhoria na validação dos valores de entrada antes da validação nas regras de negócio e uso do short-circuit para melhoria do processo

Com isso, as mudanças foram aplicadas e novos relatórioos gerados, gerando o seguinte resultado:


## Comparativo de Desempenho

| Métrica                     | Antes das Melhorias       | Após as Melhorias        | Diferença                       |
|-----------------------------|---------------------------|---------------------------|---------------------------------|
| **Tempo Médio de Resposta** | 45.61 ms (2500 RPM)       | 1.62 ms                   | **Redução significativa**      |
| **Latência Média**          | 45.56 ms (2500 RPM)       | 1.0 ms                    | **Redução drástica**           |
| **Máximo Tempo de Resposta**| 17,965 ms (2500 RPM)      | 405 ms                    | **Redução extrema**            |
| **Falhas**                  | 0%                        | 0%                        | Sem alterações                 |
| **Throughput**              | ~2500 RPM (~41 RPS)       | 21,939 RPS (~1316 RPM)    | **Grande aumento**             |


### **Melhorias Implementadas:**

**Cache:** Uso do Caffeine para reduzir validações repetidas
**Short-Circuit:** Validação interrompida na primeira falha
**Monitoramento:** Integração com Spring Boot Actuator para métricas detalhadas

---

## **📈 Métricas**

### **Acessando as Métricas via Postman**

As métricas do projeto são expostas pelo **Spring Boot Actuator** e podem ser acessadas pelo Postman.

### **Como Consultar:**

**Listar Todas as Métricas:**
   - Método: `GET`
   - Endpoint:
     ```
     http://localhost:8080/actuator/metrics
     ```
   - Resposta:
     ```json
     {
       "names": [
         "http.server.requests",
         "jvm.memory.used",
         "password.validations"
       ]
     }
     ```

 **Consultar Métrica Específica:**
   - Método: `GET`
   - Endpoint:
     ```
     http://localhost:8080/actuator/metrics/password.validations
     ```
   - Exemplo de Resposta:
     ```json
     {
       "name": "password.validations",
       "measurements": [
         {
           "statistic": "COUNT",
           "value": 123
         }
       ],
       "availableTags": []
     }
     ```

### **Legenda das Métricas**

| Nome                    | Descrição                                        |
|-------------------------|--------------------------------------------------|
| `http.server.requests`  | Métricas relacionadas às requisições HTTP.      |
| `jvm.memory.used`       | Uso de memória da JVM.                          |
| `password.validations`  | Total de senhas validadas pela API.             |

### **Configuração de Segurança**
Se o Actuator estiver protegido, configure o Postman com autenticação (**Basic Auth**) para acessar os endpoints.

---
## **🔒 Segurança na API**

Sobre práticas para garantir a segurança dos dados e do serviço, podemos citar:

**Sanitização de Entradas**: 
   - Todas as entradas passam por sanitização para remover caracteres perigosos, como `<script>` e outras possíveis injeções de código.

**Validação Estrita**: 
   - Senhas são validadas conforme regras específicas, rejeitando entradas inválidas ou malformadas.

**Rate Limiting**: 
   - Configuração de limites de requisições para prevenir ataques de força bruta e sobrecarga no serviço.

**Métricas e Monitoramento**: 
   - Endpoints do Actuator monitoram métricas de uso, permitindo a detecção de atividades incomuns ou suspeitas.

**Configurações de Content-Type**: 
   - Apenas requisições com `Content-Type: application/json` são aceitas, reduzindo o risco de exploração de formatos não intencionais.

---

## **🚀 Informações Adicionais**

Este projeto foi construído em poucos dias com foco em implementar as regras de negócio da API. A ideia foi implementar parte de boas práticas de desenvolvimento, ainda que de maneira mais simples, conforme a complexidade do projeto. 
Segurança, métricas, monitoramento, testes, boa execução do código, seguindo práticas do Clean Code, SOLID e outras práticas sugeridas pela comunidade. Caso você tenha sugestões ou ideias, são sempre bem-vindas! 

Obrigada por ter chegado até aqui e vamos juntos! 🚀

---