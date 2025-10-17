# 🏢 Societário Insight

> Sistema completo (frontend + backend) para consulta e análise de sócios e CNAEs de empresas a partir do CNPJ, exibindo dados detalhados, participações societárias e informações cadastrais de forma visual e interativa.

---

## 📚 Sumário
- [Visão Geral](#-visão-geral)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura do Projeto](#-arquitetura-do-projeto)
- [Estrutura de Pastas](#-estrutura-de-pastas)
- [Instalação e Execução](#-instalação-e-execução)
- [Endpoints da API](#-endpoints-da-api)
- [Fluxo da Aplicação](#-fluxo-da-aplicação)
- [Formatadores e Boas Práticas](#-formatadores-e-boas-práticas)
- [Contribuição](#-contribuição)
- [Licença](#-licença)

---

## 🌐 Visão Geral

O **Societário Insight** é uma aplicação fullstack projetada para realizar **consultas de dados empresariais e societários**, permitindo:
- Buscar empresas pelo **CNPJ** ou por **percentual mínimo de participação**.
- Exibir informações cadastrais e participações.
- Visualizar **CNAEs** através de um **acordeon interativo**.
- Mostrar a **localização do estabelecimento no mapa**.
- Integrar com API backend (Spring Boot) de forma reativa e cacheada via Zustand.

---

## 🧰 Tecnologias Utilizadas

### 🔹 **Frontend**
- [React 18](https://react.dev/)
- [TypeScript](https://www.typescriptlang.org/)
- [Vite](https://vitejs.dev/)
- [Material UI (MUI DataGrid)](https://mui.com/)
- [Zustand](https://zustand-demo.pmnd.rs/)
- [Axios](https://axios-http.com/)

### 🔹 **Backend**
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [JUnit + Mockito](https://junit.org/junit5/)
- [Maven](https://maven.apache.org/)

---

## 🏗 Arquitetura do Projeto

O sistema é dividido em **dois módulos independentes**:

## ⚙️ Instalação e Execução

### 🖥️ **Backend**

```
cd backend
./mvnw spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

### 💻 Frontend


```
cd frontend
npm install
npm run dev
```

A aplicação estará disponível em:

```
http://localhost:5173
```
---

## 🔗 Endpoints da API

### 🔹 Sócios (`/socios`)

| Método | Rota | Descrição |
|--------|-------|-----------|
| `GET` | `/socios?participacaoMin=10` | Lista sócios com participação acima de X% |
| `GET` | `/socios/{cnpj}` | Retorna detalhes de um sócio pelo CNPJ |

---

## 🔄 Fluxo da Aplicação

1. Usuário digita **participação mínima (%)** e clica em “Pesquisar”.
2. Frontend chama `GET /socios?participacaoMin=X` → exibe tabela (`ClickableDataGrid`).
3. Usuário clica em um **CNPJ** → dispara `GET /socios/{cnpj}`.
4. Exibe detalhes (`InsightData`) e **CNAEs** em um **acordeon (`CnaeAccordeon`)**.
5. Mapa embutido (`iframe`) mostra a **localização do estabelecimento pelo CEP**.
