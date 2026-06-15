# Cadê? — App de Achados e Perdidos

## Descrição da Proposta

O **Cadê?** é um aplicativo Android desenvolvido para registrar objetos perdidos e encontrados em um sistema simples de achados e perdidos.

O usuário pode cadastrar um objeto informando nome, categoria, status, local e descrição. A partir dessas informações, o sistema gera uma recomendação personalizada para orientar o usuário sobre o que fazer com o item perdido ou encontrado.

O projeto foi desenvolvido como trabalho da **Avaliação Parcial 2 (AP2)** da disciplina de **Desenvolvimento Mobile**, com integração entre aplicativo Android, API REST e banco de dados relacional.

---

## Funcionalidades do Aplicativo

* Tela inicial com logo e acesso ao sistema;
* Listagem de objetos cadastrados;
* Cadastro de objetos perdidos ou encontrados;
* Edição de objetos cadastrados;
* Exclusão de objetos;
* Tela de contato com intents implícitas para e-mail e telefone;
* Geração de recomendação personalizada;
* Integração com API REST;
* Persistência dos dados em banco SQLite.

---

## Tecnologias Utilizadas

### Aplicativo Android

* Kotlin;
* Android Studio;
* XML para construção das interfaces;
* Activities;
* Fragment;
* RecyclerView;
* CardView;
* Retrofit;
* Gson Converter;
* Intents explícitas;
* Intents implícitas.

### Backend / API

* Python;
* FastAPI;
* Uvicorn;
* SQLAlchemy;
* SQLite;
* Swagger/OpenAPI.

---

# Aplicativo Android

## Telas do Aplicativo

### 1. Tela Inicial — MainActivity

A `MainActivity` é a tela inicial do aplicativo. Ela apresenta a logo do app, uma breve descrição da proposta e um botão para acessar o sistema.

Essa tela utiliza:

* `ImageView` para exibir a logo;
* `TextView` para título e descrição;
* `Button` para navegar até a tela principal.

Ao clicar no botão **Ver Objetos**, o usuário é direcionado para a `ListagemActivity` por meio de uma **Intent explícita**.

---

### 2. Tela de Listagem — ListagemActivity

A `ListagemActivity` funciona como a tela principal do sistema. Ela exibe todos os objetos cadastrados no banco de dados, buscando essas informações através da API REST.

Essa tela utiliza:

* `RecyclerView` para listar os objetos;
* `CardView` para organizar visualmente cada item;
* `ProgressBar` para indicar carregamento;
* `Button` para cadastrar novo objeto;
* `Button` para acessar a tela de contato.

Cada objeto listado apresenta suas principais informações, como:

* nome;
* categoria;
* status;
* local;
* descrição;
* recomendação personalizada.

Além disso, cada item possui botões para:

* editar o objeto cadastrado;
* excluir o objeto do sistema.

Essa tela realiza integração com os endpoints:

```text
GET /objetos
DELETE /objetos/{id}
```

---

### 3. Tela de Cadastro — CadastroActivity com FormularioFragment

A tela de cadastro é formada por uma `CadastroActivity`, que funciona como uma tela base, e por um `FormularioFragment`, responsável por exibir e controlar o formulário de cadastro.

Essa estrutura foi utilizada para atender ao requisito obrigatório de uso de pelo menos **1 Fragment funcional**.

O formulário permite que o usuário informe:

* nome do objeto;
* categoria;
* status, indicando se perdeu ou encontrou;
* local;
* descrição.

Essa tela utiliza:

* `EditText`;
* `Spinner`;
* `RadioGroup`;
* `RadioButton`;
* `Button`;
* `Toast` para feedback ao usuário.

Ao clicar em **Salvar**, os dados são enviados para a API em formato JSON. A API processa as informações e gera uma recomendação personalizada de acordo com a categoria e o status do objeto.

Essa tela realiza integração com o endpoint:

```text
POST /objetos
```

---

### 4. Tela de Edição — EditarActivity

A `EditarActivity` permite alterar informações de um objeto já cadastrado.

Ao clicar no botão **Editar** em um item da lista, o aplicativo envia o ID do objeto para a tela de edição usando uma **Intent explícita com extra**.

A tela então busca os dados atuais do objeto na API, preenche automaticamente os campos do formulário e permite que o usuário altere as informações.

Essa tela utiliza:

* `EditText`;
* `Spinner`;
* `RadioGroup`;
* `RadioButton`;
* `Button`;
* `Toast` para mensagens de retorno.

Essa tela realiza integração com os endpoints:

```text
GET /objetos/{id}
PUT /objetos/{id}
```

---

### 5. Tela de Contato — ContatoActivity

A `ContatoActivity` permite que o usuário entre em contato com a central de achados e perdidos.

Essa tela utiliza **Intents implícitas**, pois solicita que outros aplicativos do Android executem determinadas ações.

Foram implementadas duas ações:

* envio de e-mail;
* abertura do discador telefônico.

Essa tela atende ao requisito de uso de pelo menos **1 Intent implícita**, utilizando:

```text
Intent.ACTION_SEND
Intent.ACTION_DIAL
```

Também há um botão de voltar, permitindo retornar para a tela principal do sistema.

---

## Componentes Visuais Utilizados

O aplicativo utiliza mais de 6 tipos diferentes de componentes gráficos, incluindo:

* `TextView`;
* `EditText`;
* `Button`;
* `ImageView`;
* `Spinner`;
* `RadioButton`;
* `RadioGroup`;
* `RecyclerView`;
* `ProgressBar`;
* `CardView`.

---

## Integração Android com API

A comunicação entre o aplicativo Android e o backend é feita usando **Retrofit**.

O app envia e recebe dados em formato JSON, consumindo os endpoints da API FastAPI.

A classe de modelo utilizada no app representa a entidade `Objeto`, contendo os campos:

```text
id
nome
categoria
status
local
descricao
recomendacao
```

Para o emulador Android acessar a API local, a URL base usada no Retrofit deve ser:

```kotlin
http://10.0.2.2:8000/
```

Para dispositivo físico, deve ser utilizado o IP do computador na mesma rede Wi-Fi, por exemplo:

```kotlin
http://192.168.0.10:8000/
```

Nesse caso, a API deve ser executada com:

```bash
python -m uvicorn main:app --host 0.0.0.0 --reload
```

---

# Backend / API

O backend foi desenvolvido com **FastAPI**, utilizando **SQLite** como banco de dados relacional e **SQLAlchemy** para comunicação com o banco.

A API possui documentação automática pelo Swagger/OpenAPI, disponível na rota `/docs`.

---

## Estrutura do Backend

```text
backend
│
├── main.py
├── database.py
├── models.py
├── schemas.py
├── crud.py
├── requirements.txt
└── cade.db
```

### Descrição dos Arquivos

| Arquivo            | Função                                                                    |
| ------------------ | ------------------------------------------------------------------------- |
| `main.py`          | Arquivo principal da API, onde ficam as rotas/endpoints                   |
| `database.py`      | Configuração da conexão com o banco SQLite                                |
| `models.py`        | Modelo da tabela `objetos` no banco de dados                              |
| `schemas.py`       | Schemas Pydantic usados para entrada e saída de dados JSON                |
| `crud.py`          | Funções de cadastro, consulta, edição, exclusão e geração de recomendação |
| `requirements.txt` | Lista de dependências necessárias para executar o backend                 |
| `cade.db`          | Arquivo do banco de dados SQLite                                          |

---

## Entidade Objeto

A entidade principal do sistema é o `Objeto`.

Exemplo de objeto salvo no sistema:

```json
{
  "id": 1,
  "nome": "Carteira",
  "categoria": "Documentos",
  "status": "Perdi",
  "local": "Cantina",
  "descricao": "Carteira preta com zíper",
  "recomendacao": "Procure a administração e considere bloquear documentos ou cartões importantes."
}
```

---

## Processamento dos Dados

Além de cadastrar os dados informados pelo usuário, a API também gera uma **recomendação personalizada** com base na categoria e no status do objeto.

Exemplo:

Se o usuário cadastrar:

```text
Categoria: Documentos
Status: Perdi
```

A API pode gerar uma recomendação como:

```text
Procure a administração e considere bloquear documentos ou cartões importantes.
```

Esse processamento atende ao requisito do projeto de gerar um resultado personalizado a partir das informações fornecidas pelo usuário.

---

## Endpoints da API

| Método | Rota            | Descrição                          |
| ------ | --------------- | ---------------------------------- |
| GET    | `/`             | Verifica se a API está funcionando |
| GET    | `/objetos`      | Lista todos os objetos cadastrados |
| GET    | `/objetos/{id}` | Busca um objeto específico pelo ID |
| POST   | `/objetos`      | Cadastra um novo objeto            |
| PUT    | `/objetos/{id}` | Atualiza os dados de um objeto     |
| DELETE | `/objetos/{id}` | Remove um objeto do sistema        |

---

## Link do Swagger

Com a API em execução, o Swagger pode ser acessado em:

```text
http://127.0.0.1:8000/docs
```
---

# Instruções de Execução

## 1. Executar o Backend

Acesse a pasta do backend:

```bash
cd backend
```

Crie o ambiente virtual:

```bash
python -m venv .venv
```

Ative o ambiente virtual.

No Windows:

```bash
.venv\Scripts\activate
```

Instale as dependências:

```bash
pip install -r requirements.txt
```

Execute a API:

```bash
python -m uvicorn main:app --reload
```

A API ficará disponível em:

```text
http://127.0.0.1:8000
```

A documentação Swagger estará disponível em:

```text
http://127.0.0.1:8000/docs
```

---

## 2. Executar o Aplicativo Android

1. Abra o Android Studio;
2. Selecione a opção **Open**;
3. Abra a pasta do projeto Android;
4. Aguarde a sincronização do Gradle;
5. Confirme se a API está rodando;
6. Execute o aplicativo em um emulador ou dispositivo físico.

Se estiver usando emulador, mantenha a URL base no Retrofit como:

```kotlin
http://10.0.2.2:8000/
```

Se estiver usando celular ou tablet físico, altere a URL base para o IP do computador na rede local, por exemplo:

```kotlin
http://192.168.0.10:8000/
```

E rode a API com:

```bash
python -m uvicorn main:app --host 0.0.0.0 --reload
```
---

# Requisitos Atendidos

| Requisito                        | Implementação no Projeto                                                                          |
| -------------------------------- | ------------------------------------------------------------------------------------------------- |
| Aplicativo Android autoral       | App Cadê? — Achados e Perdidos                                                                    |
| Mínimo de 5 telas                | MainActivity, ListagemActivity, CadastroActivity, EditarActivity e ContatoActivity                |
| Uso de Fragment                  | FormularioFragment na tela de cadastro                                                            |
| Intents explícitas               | Navegação entre as telas do aplicativo                                                            |
| Intent implícita                 | Envio de e-mail e abertura do discador                                                            |
| Coleta de informações            | Formulário de cadastro de objetos                                                                 |
| Processamento dos dados          | Geração de recomendação personalizada                                                             |
| Resultado personalizado          | Recomendação baseada em categoria e status                                                        |
| Integração com API REST          | Retrofit consumindo API FastAPI                                                                   |
| Persistência em banco relacional | Dados salvos em SQLite através da API                                                             |
| Swagger/OpenAPI                  | Documentação automática em `/docs`                                                                |
| JSON                             | Comunicação entre app e API em formato JSON                                                       |
| CRUD completo                    | Cadastro, listagem, edição e exclusão de objetos                                                  |
| Componentes visuais variados     | TextView, EditText, Button, ImageView, Spinner, RadioButton, RecyclerView, ProgressBar e CardView |
| Organização do código            | Pacotes separados para API, model, adapter, Activities e backend                                  |

---

## Banco de Dados Utilizado

O banco de dados utilizado no projeto foi o **SQLite**, um banco relacional baseado em arquivo.

A escolha do SQLite foi feita por ser uma opção simples e leve para projetos acadêmicos, não exigindo instalação de servidor externo como MySQL ou PostgreSQL.

No projeto, o banco é representado pelo arquivo:

```text
cade.db
