# Padrões de Projeto em Java

Exemplos práticos e executáveis dos padrões de projeto mais usados no dia a dia, escritos em **Java 25 puro**, sem nenhum framework ou dependência externa.

Cada padrão fica em seu próprio pacote, com um cenário de e-commerce (frete, pedidos, notificações, cotações, relatórios, validação de compra e cadastro de produtos) para mostrar o problema que ele resolve, e não só a estrutura.

## Requisitos

- JDK 25

Não há Maven nem Gradle: o projeto compila e roda direto com `javac` e `java`.

## Como executar

**Bash / Git Bash / Linux / macOS**

```bash
javac --release 25 -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out br.com.padroes.Aplicacao
```

**PowerShell**

```powershell
javac --release 25 -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out br.com.padroes.Aplicacao
```

`Aplicacao` executa o exemplo de cada padrão, em sequência, imprimindo o que acontece.

Se os acentos aparecerem trocados no terminal do Windows, adicione a opção de encoding ao comando `java`. No PowerShell ela precisa de aspas:

```powershell
java "-Dstdout.encoding=UTF-8" -cp out br.com.padroes.Aplicacao
```

## Como verificar

```bash
java -cp out br.com.padroes.VerificacaoPadroes
```

`VerificacaoPadroes` faz 40 verificações sobre o comportamento de todos os padrões, também sem framework de testes, e termina com código de saída `1` se alguma falhar.

## Estrutura

```
src/
├── main/java/br/com/padroes/
│   ├── Aplicacao.java                  ponto de entrada, roda todos os exemplos
│   ├── Exemplo.java                    contrato comum dos exemplos
│   ├── Moeda.java                      formatação de valores em R$
│   ├── strategy/
│   ├── builder/
│   ├── factory/
│   ├── proxy/
│   ├── observer/
│   ├── templatemethod/
│   ├── chainofresponsibility/
│   └── repository/
│       ├── dto/
│       ├── model/
│       ├── mapper/
│       ├── service/
│       └── exceptions/
└── test/java/br/com/padroes/
    └── VerificacaoPadroes.java
```

Cada pacote tem uma classe `Exemplo<Padrão>` que monta o cenário e mostra o padrão funcionando.

## Os padrões

| Padrão | Pacote | Cenário | Ideia central |
|---|---|---|---|
| **Strategy** | `strategy` | Cálculo de frete (Sedex, PAC, retirada na loja) | Cada algoritmo é uma classe que implementa `EstrategiaFrete`. A `CalculadoraFrete` troca de estratégia em tempo de execução, sem `if/else`. |
| **Builder** | `builder` | Montagem de um `Pedido` | Objeto imutável criado passo a passo com uma API fluente. Campos opcionais não exigem construtores sobrecarregados, e `build()` valida tudo antes de criar. |
| **Factory** | `factory` | Envio de notificações (e-mail, SMS, push) | `NotificacaoFactory` decide qual implementação criar a partir do `TipoNotificacao`. A interface `sealed` e o `switch` exaustivo fazem o compilador avisar se um tipo novo ficar sem tratamento. |
| **Proxy** | `proxy` | Cotação de moedas | `ServicoCotacaoComCache` implementa a mesma interface do serviço remoto (lento) e se coloca na frente dele com cache. O cliente não percebe a diferença, só a velocidade. |
| **Observer** | `observer` | Eventos de pedido criado | `PublicadorPedidos` notifica e-mail, nota fiscal e fidelidade sem conhecer nenhum deles. Ouvintes entram e saem em tempo de execução, e podem até ser uma lambda. |
| **Template Method** | `templatemethod` | Relatórios de vendas (texto e CSV) | `GeradorRelatorio.gerar()` é `final` e fixa o algoritmo: validar, cabeçalho, linhas e rodapé. As subclasses preenchem as etapas, e `rodape` é um gancho opcional. |
| **Chain of Responsibility** | `chainofresponsibility` | Aprovação de compra | Quantidade → estoque → crédito → antifraude. Cada validador decide ou passa adiante, e a cadeia para no primeiro problema. Adicionar uma regra é criar um elo, sem mexer nos outros. |
| **Repository + DTO + Injeção de Dependência** | `repository` | Cadastro de produtos | O `ProdutoService` depende da interface `ProdutoRepository`, não da implementação em memória, e recebe tudo pelo construtor. A entidade valida as próprias regras e só DTOs saem do service. |

### Injeção de dependência sem framework

Sem Spring, quem monta o grafo de objetos é o próprio código, num único ponto, chamado de *composition root*. Aqui esse ponto é o `ExemploRepository`:

```java
ProdutoRepository produtoRepository = new ProdutoRepositoryEmMemoria();
var produtoService = new ProdutoService(new ProdutoMapper(), produtoRepository);
```

Trocar a persistência em memória por JDBC significa criar outra implementação de `ProdutoRepository` e mudar apenas essa linha. O `ProdutoService` continua igual.

## Recursos do Java 25 usados

- `void main()` como método de instância (JEP 512) e a classe `java.lang.IO` para entrada e saída no console
- `record` para DTOs, eventos e valores imutáveis
- `sealed interface` com `switch` exaustivo na factory
- `Locale.of`, `Thread.sleep(Duration)`, `Stream.toList()` e `String.formatted`