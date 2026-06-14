# Cadê? — App de Achados e Perdidos

## Descrição da Proposta

O **Cadê?** é um aplicativo Android desenvolvido para registrar objetos perdidos e encontrados em um sistema simples de achados e perdidos.

O usuário pode cadastrar um objeto informando nome, categoria, status, local e descrição. A partir dessas informações, o sistema gera uma recomendação personalizada para orientar o usuário sobre o que fazer com o item perdido ou encontrado.

O projeto foi desenvolvido como trabalho da **Avaliação Parcial 2 (AP2)** da disciplina de **Desenvolvimento Mobile**, com integração entre aplicativo Android, API REST e banco de dados relacional.

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

## Telas do Aplicativo

O aplicativo possui 5 telas principais:

1. **MainActivity**
   Tela inicial do aplicativo, com logo, descrição e botão para acessar o sistema.

2. **ListagemActivity**
   Tela principal do sistema, responsável por exibir os objetos cadastrados em um RecyclerView.

3. **CadastroActivity com FormularioFragment**
   Tela de cadastro que utiliza um Fragment funcional para coletar os dados do objeto.

4. **EditarActivity**
   Tela responsável por buscar um objeto pelo ID e permitir a alteração dos seus dados.

5. **ContatoActivity**
   Tela com intents implícitas para envio de e-mail e abertura do discador telefônico.

## Tecnologias Utilizadas

### Aplicativo Android

* Kotlin
* Android Studio
* XML para construção das interfaces
* Activities
* Fragment
* RecyclerView
* CardView
* Retrofit
* Gson Converter
* Intents explícitas
* Intents implícitas

### Backend / API

* Python
* FastAPI
* Uvicorn
* SQLAlchemy
* SQLite
* Swagger/OpenAPI

## Componentes Visuais Utilizados

O aplicativo utiliza mais de 6 tipos diferentes de componentes gráficos, incluindo:

* TextView
* EditText
* Button
* ImageView
* Spinner
* RadioButton
* RadioGroup
* RecyclerView
* ProgressBar
* CardView

## Estrutura do Projeto

```text
cade-achados-perdidos
│
├── backend
│   ├── main.py
│   ├── database.py
│   ├── models.py
│   ├── schemas.py
│   ├── crud.py
│   ├── requirements.txt
│   └── cade.db
│
└── android
    └── CadeAchadosPerdidos
```

## Instruções de Execução

### 1. Executar o Backend

Acesse a pasta do backend:

```bash
cd backend
```

Crie o ambiente virtual:

```bash
python -m venv .venv
```

Ative o ambiente virtual:

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

### 2. Executar o Aplicativo Android

1. Abra o Android Studio;
2. Abra a pasta do projeto Android;
3. Aguarde o Gradle sincronizar;
4. Execute o aplicativo em um emulador ou dispositivo físico.

Para o emulador Android acessar a API local, a URL base usada no Retrofit deve ser:

```kotlin
http://10.0.2.2:8000/
```

Para dispositivo físico, utilize o IP do computador na mesma rede Wi-Fi, por exemplo:

```kotlin
http://192.168.0.10:8000/
```

Nesse caso, execute a API com:

```bash
python -m uvicorn main:app --host 0.0.0.0 --reload
```

## Descrição da API

A API REST foi desenvolvida com FastAPI e permite realizar operações de cadastro, consulta, edição e remoção de objetos.

Os dados são trafegados em formato JSON e armazenados em um banco de dados relacional SQLite.

### Entidade Objeto

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

### Endpoints da API

| Método | Rota            | Descrição                          |
| ------ | --------------- | ---------------------------------- |
| GET    | `/`             | Verifica se a API está funcionando |
| GET    | `/objetos`      | Lista todos os objetos cadastrados |
| GET    | `/objetos/{id}` | Busca um objeto específico pelo ID |
| POST   | `/objetos`      | Cadastra um novo objeto            |
| PUT    | `/objetos/{id}` | Atualiza os dados de um objeto     |
| DELETE | `/objetos/{id}` | Remove um objeto do sistema        |

## Link do Swagger

Com a API em execução, o Swagger pode ser acessado em:

```text
http://127.0.0.1:8000/docs
```

## Prints / Screenshots do Aplicativo

Adicione abaixo os prints das telas do aplicativo:

### Tela Inicial

```text
Inserir print da tela inicial aqui
```

### Tela de Listagem

```text
Inserir print da tela de listagem aqui
```

### Tela de Cadastro

```text
Inserir print da tela de cadastro aqui
```

### Tela de Edição

```text
Inserir print da tela de edição aqui
```

### Tela de Contato

```text
Inserir print da tela de contato aqui
```

## Requisitos Atendidos

* Aplicativo Android autoral;
* Mínimo de 5 telas;
* Uso de pelo menos 1 Fragment funcional;
* Navegação entre telas com Intents explícitas;
* Uso de Intents implícitas;
* Interface com múltiplos componentes visuais;
* API REST documentada com Swagger/OpenAPI;
* Comunicação em JSON;
* Banco de dados relacional SQLite;
* Operações de cadastro, consulta, edição e exclusão;
* Código organizado em pacotes e classes;
* README com informações do projeto.

## Autor

Projeto desenvolvido por **Hugo Farias Watzl Barreto** para a disciplina de **Desenvolvimento Mobile**.
