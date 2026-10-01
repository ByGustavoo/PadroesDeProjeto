<div align="center"> <br>
  <img align="center" alt="padroes-java" height="150" width="150" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg" />
</div>

<br>

<div align="center">
  Exemplos práticos e executáveis dos principais padrões de projeto em Java 25 puro, sem framework e sem dependências. Cada padrão fica em seu próprio pacote e resolve um problema de um e-commerce (frete, pedidos, notificações, cotações, relatórios, aprovação de compra e cadastro de produtos), mostrando não só a estrutura, mas o motivo de usá-lo.
</div>

<br> <br>

## 🚀 Ferramentas Utilizadas

* ☕️ Java 25

* 🧰 javac e java (JDK)

<br>

## ⚙️ Pré-requisitos

* JDK 25 instalada (`java -version` deve mostrar a versão 25)

* Nenhuma ferramenta de build: não há Maven nem Gradle, o projeto compila e roda direto com `javac` e `java`

<br>

## ▶️ Como Executar

🔹 Bash, Git Bash, Linux e macOS
```bash
# Compila todo o código-fonte para a pasta out
javac --release 25 -encoding UTF-8 -d out $(find src -name "*.java")

# Executa o exemplo de cada padrão, em sequência
java -cp out br.com.padroes.Aplicacao
```

🔹 PowerShell
```powershell
# Compila todo o código-fonte para a pasta out
javac --release 25 -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName

# Executa o exemplo de cada padrão (a opção entre aspas mantém os acentos no terminal do Windows)
java "-Dstdout.encoding=UTF-8" -cp out br.com.padroes.Aplicacao
```

<br>

## 🧪 Testes

```bash
# Roda as 40 verificações dos padrões e termina com código 1 se alguma falhar
java -cp out br.com.padroes.VerificacaoPadroes
```

A verificação também é escrita em Java puro, sem framework de testes: cada padrão tem checagens do comportamento esperado e dos casos de erro, e o resumo final informa quantas passaram.

<br>

## 🧩 Padrões Implementados

| Padrão | Pacote | Cenário | Ideia central |
|---|---|---|---|
| ♟️ **Strategy** | `strategy` | Cálculo de frete (Sedex, PAC e retirada na loja) | Cada algoritmo é uma classe que implementa `EstrategiaFrete`, e a `CalculadoraFrete` troca de estratégia em tempo de execução, sem `if/else` |
| 🧱 **Builder** | `builder` | Montagem de um `Pedido` | Objeto imutável criado passo a passo por uma API fluente, sem construtores sobrecarregados, com tudo validado no `build()` |
| 🏭 **Factory** | `factory` | Envio de notificações (e-mail, SMS e push) | `NotificacaoFactory` decide qual implementação criar; a interface `sealed` e o `switch` exaustivo fazem o compilador acusar um tipo novo sem tratamento |
| 🛡️ **Proxy** | `proxy` | Cotação de moedas | `ServicoCotacaoComCache` implementa a mesma interface do serviço remoto e se coloca na frente dele com um cache: o cliente não percebe a diferença, só a velocidade |
| 👀 **Observer** | `observer` | Eventos de pedido criado | `PublicadorPedidos` avisa e-mail, nota fiscal e fidelidade sem conhecer nenhum deles; ouvintes entram e saem em tempo de execução e podem ser uma lambda |
| 📋 **Template Method** | `templatemethod` | Relatórios de vendas em texto e em CSV | `GeradorRelatorio.gerar()` é `final` e fixa o algoritmo; as subclasses preenchem as etapas e `rodape` é um gancho opcional |
| 🔗 **Chain of Responsibility** | `chainofresponsibility` | Aprovação de compra | Quantidade → estoque → crédito → antifraude: cada elo decide ou passa adiante, e uma regra nova é só mais um elo |
| 🗄️ **Repository + DTO + Injeção de Dependência** | `repository` | Cadastro de produtos | `ProdutoService` recebe as dependências pelo construtor e depende da interface `ProdutoRepository`; a entidade valida as próprias regras e só DTOs saem do service |

<br>

## 💉 Injeção de Dependência sem Framework

Sem Spring, quem monta os objetos é o próprio código, em um único ponto chamado *composition root*. Neste projeto esse ponto é o `ExemploRepository`:

```java
ProdutoRepository produtoRepository = new ProdutoRepositoryEmMemoria();
var produtoService = new ProdutoService(new ProdutoMapper(), produtoRepository);
```

Para trocar a persistência em memória por JDBC, basta criar outra implementação de `ProdutoRepository` e mudar essa linha. O `ProdutoService` continua igual.

<br>

## ✨ Recursos do Java 25

* **`void main()` de instância (JEP 512):** `Aplicacao` e `VerificacaoPadroes` dispensam `static` e `String[] args`, e escrevem no console com `java.lang.IO`

* **`record`:** DTOs, eventos e valores imutáveis, como `PedidoCriado`, `Venda`, `Compra` e `ProdutoDTO`

* **`sealed interface` com `switch` exaustivo:** a `NotificacaoFactory` não compila se um `TipoNotificacao` ficar sem implementação

* **APIs recentes da biblioteca padrão:** `Locale.of`, `Thread.sleep(Duration)`, `Stream.toList()` e `String.formatted`

<br>

## 📁 Estrutura

```
src/main/java/br/com/padroes
├── Aplicacao.java              # Ponto de entrada: executa o exemplo de cada padrão
├── Exemplo.java                # Contrato comum dos exemplos (titulo e executar)
├── Moeda.java                  # Formatação de valores em reais
├── strategy                    # EstrategiaFrete, FreteSedex, FretePac, FreteRetiradaNaLoja e CalculadoraFrete
├── builder                     # Pedido, com o Builder interno, e ItemPedido
├── factory                     # Notificacao, implementações de e-mail, SMS e push, e NotificacaoFactory
├── proxy                       # ServicoCotacao, ServicoCotacaoRemoto e ServicoCotacaoComCache
├── observer                    # PublicadorPedidos, OuvintePedido, os três ouvintes e o evento PedidoCriado
├── templatemethod              # GeradorRelatorio, RelatorioTexto, RelatorioCsv e Venda
├── chainofresponsibility       # ValidadorCompra, os quatro validadores, Compra e ResultadoValidacao
└── repository                  # ProdutoRepository e ProdutoRepositoryEmMemoria
    ├── dto                     # SalvarProdutoDTO e ProdutoDTO
    ├── exceptions              # ProdutoNaoEncontradoException e ProdutoJaCadastradoException
    ├── mapper                  # ProdutoMapper
    ├── model                   # Produto
    └── service                 # ProdutoService

src/test/java/br/com/padroes
└── VerificacaoPadroes.java     # 40 verificações em Java puro, sem framework de testes
```

Cada pacote de padrão tem também uma classe `Exemplo<Padrão>`, que monta o cenário e é chamada pela `Aplicacao`.

<br>

## 🖥️ Desenvolvedor

### 🔵 LinkedIn: [Gustavo Correa](https://www.linkedin.com/in/gustavo-chauar-correa-946168269/)