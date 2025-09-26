# 📱 ListWise

O **ListWise** é um aplicativo Android para gerenciamento de listas de compras de supermercado.  
Ele foi desenvolvido em **Kotlin**, com suporte a banco de dados local e integração com **Firebase**.

---

## 🚀 Principais Funcionalidades

### 🛒 Gerenciamento de Listas de Compras
- Adicionar, criar, editar e excluir itens;
- Itens com nome, marca (opcional), quantidade e preço estimado;
- Listas com status: `desejada`, `em_compra`, `finalizada`, `faltantes`;
- Botão **Iniciar compra** para alterar status;
- **Finalizar compra**: move itens comprados para histórico e cria lista de "itens faltantes";
- Reabrir listas finalizadas (nova versão com mesmo conteúdo);
- Atualização automática de preços pelo último valor inserido;
- Cálculo da estimativa total da compra;
- Itens não comprados permanecem como desejados.

### 📖 Histórico de Compras
- Armazenamento de listas finalizadas com data, local e total gasto;
- Visualização e reutilização de listas antigas;
- Frequência de itens mais comprados.

### 🤝 Compartilhamento de Listas
- Compartilhar via link, QR Code ou dentro do app;
- Permissões de visualização ou edição;
- Sincronização básica entre usuários.

### 💡 Sugestões de Itens
- Sugestões com base no histórico do usuário;
- Autocompletar ao digitar itens já usados;
- Destaque para **itens da estação**.

### 📍 Localização de Supermercados
- Exibir supermercados próximos via **Google Maps API**;
- Mostrar nome, endereço e distância.

### ⚙️ Preferências e Configurações
- Alterar idioma, tema e notificações;
- **Modo Offline** para listas locais;
- Gerenciar permissões do app.

---

## 🛠️ Tecnologias Utilizadas
- **Frontend:** Kotlin + AndroidX + Material Design 3
- **Backend:** Kotlin
- **Banco de Dados Local:** Room (SQLite)
- **Banco de Dados Remoto:** Firebase Realtime Database
- **Autenticação:** Firebase Auth
- **Geolocalização:** Google Maps API

---

## 📂 Estrutura do Projeto
app/
├─ java/com/example/listwise # Código-fonte em Kotlin
├─ res/ # Recursos (layouts, drawables, valores)
│ ├─ layout/ # XMLs das telas
│ ├─ drawable/ # Imagens e backgrounds
│ ├─ values/ # Cores, strings, temas
└─ AndroidManifest.xml # Configurações principais

---

## 🚀 Arquitetura
- **Padrão**: MVVM (Model-View-ViewModel)
- **Principais camadas**:
    - `ui/` → Activities, Fragments e Views
    - `data/` → Repositórios, DAOs, Room, Firebase
    - `model/` → Modelos de dados (Kotlin data classes)

---

## 📱 Fluxo inicial
1. **IntroActivity** → Tela de introdução com logo animado.
2. **AuthActivity** (Cadastro/Login com Firebase Auth).
3. **MainActivity** → Tela principal com navegação para tarefas e funcionalidades extras.

---

## 🛠️ Dependências principais
- **Firebase Auth & Database**
- **Room (SQLite)**
- **Retrofit / OkHttp**
- **Glide (imagens)**
- **ZXing (QR Code)**
- **Google Maps / Location Services**

---

## 🎨 Design
- **Tema base**: Material 3 (`Theme.Material3.DayNight.NoActionBar`)
- **Cores**: Definidas em `res/values/colors.xml`
- Suporte planejado para **modo claro/escuro**.

---

## ⚙️ Instalação e Execução Local

### ✅ Pré-requisitos
- **Android Studio** (versão mais recente recomendada)
- **JDK 17** ou superior
- **Gradle** (embutido no Android Studio já resolve)
- Conta no **Firebase Console** (para autenticação e banco remoto)
- **Google Maps API Key** (para geolocalização)

### 📥 Passos para rodar o projeto
1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/listwise.git
   cd listwise

2. Abra o projeto no Android Studio:
    File > Open > listwise

3. Configure o Firebase:
    Crie um novo projeto no Firebase Console
    Adicione o aplicativo Android (com.example.listwise)
    Baixe o arquivo google-services.json
    Coloque o arquivo em app/google-services.json

4. Configure o Google Maps:
    Vá para o Google Cloud Console
    Ative a Maps SDK for Android
    Gere uma API Key e adicione em:
    <meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="SUA_API_KEY_AQUI"/>
    dentro de <application> no AndroidManifest.xml.

5. Sincronize as dependências:
    No Android Studio, clique em Sync Project with Gradle Files

6. Rode o app:
    Clique em Run ▶️ no Android Studio
    Selecione um Emulador ou dispositivo físico conectado

📌 Roadmap

- Tela de introdução (splash screen)
- Tela de cadastro/login com Firebase Auth
- CRUD de listas e itens (SQLite + Firebase)
- Histórico de compras
- Sugestões de itens
- Geolocalização com Google Maps
- Configurações do app (idioma, tema, notificações)

👨‍💻 Equipe

Projeto desenvolvido como MVP para organização de compras, incluindo testes, correções e primeira versão estável.

