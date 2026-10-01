# Atlas

## O que é

Projeto de estudo com exemplos executáveis dos principais padrões de projeto em Java 25 puro,
sem framework e sem dependências: Strategy, Builder, Factory, Proxy, Observer, Template Method,
Chain of Responsibility e Repository + DTO + Injeção de Dependência, cada um em seu pacote e com
um cenário de e-commerce.

## Tipo

Projeto didático / CLI, sem consumidores externos. Release: commit numerado direto na `main`
(`origin`: `ByGustavoo/Atlas`), no formato `<n> - <descrição>`.

## Stack

- Java 25 (usa `void main()` de instância e `java.lang.IO`, da JEP 512)
- Sem Maven, sem Gradle e sem dependências: compila com `javac` e roda com `java`
- Sem framework de testes: a verificação é a classe `VerificacaoPadroes`

## Estrutura

- `src/main/java/br/com/atlas/` — `Aplicacao` (ponto de entrada), `Exemplo` (contrato dos exemplos) e `Moeda`
- Um pacote por padrão: `strategy`, `builder`, `factory`, `proxy`, `observer`, `templatemethod`, `chainofresponsibility` e `repository` (com `dto`, `model`, `mapper`, `service` e `exceptions`)
- Cada pacote tem uma classe `Exemplo<Padrão>`, registrada na lista da `Aplicacao`
- `src/test/java/br/com/atlas/VerificacaoPadroes.java` — as 40 verificações

## Comandos

| Objetivo | Comando |
|---|---|
| Compilar (Bash) | `javac --release 25 -Xlint:all,-serial -Werror -encoding UTF-8 -d out $(find src -name "*.java")` |
| Executar os exemplos | `java -cp out br.com.atlas.Aplicacao` |
| Verificar | `java -cp out br.com.atlas.VerificacaoPadroes` (sai com código 1 se algo falhar) |

## Convenções

- **Este projeto tem comentários, e eles são intencionais.** O usuário pediu comentários curtos
  em pt-BR explicando o papel de cada parte dos padrões. Isso é uma exceção explícita à regra
  geral de não comentar código: não remova esses comentários ao editar um arquivo. Código novo
  de um padrão segue o mesmo estilo, com `//` de uma linha acima da classe dizendo o papel dela no
  padrão (contrato, implementação concreta, contexto, cliente, gancho, elo) e acima de cada ponto-chave.
- Fora os comentários, valem as regras de estilo da skill `java-clean-architecture`: campos e
  imports do menor para o maior, nenhuma linha em branco antes de `}`, arquivo terminando no
  último `}` e mensagens de erro terminando em `!`.
- Um padrão novo ganha seu pacote, sua classe `Exemplo<Padrão>` na lista da `Aplicacao`, um método
  `verificar<Padrão>` na `VerificacaoPadroes` e uma linha nas tabelas do `README.md`.

## Pegadinhas

- As exceções não declaram `serialVersionUID`, seguindo o padrão dos outros projetos; por isso o
  lint `serial` fica desligado na compilação.
- No PowerShell 5.1, `-Dstdout.encoding=UTF-8` precisa de aspas, senão o PowerShell quebra a opção
  no ponto e o `java` tenta carregar uma classe chamada `.encoding=UTF-8`.
- `ServicoCotacaoRemoto` dorme 200 ms por consulta de propósito; a verificação leva cerca de 1 s por isso.