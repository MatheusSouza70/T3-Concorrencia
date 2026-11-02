# Parte 3 — Concorrência em Scala

Trabalho acadêmico desenvolvido para demonstrar o funcionamento de concorrência, gerenciamento de threads, memória compartilhada, sincronização e modelos avançados de concorrência na linguagem **Scala 3**.

## Integrantes do Grupo
- **Matheus Dutra**
- **Pedro Henrique Lira**
- **Lucas Pinheiro**

---

## Estrutura do Repositório

- `src/main/scala/parte1_threads/`
  - `CriacaoThreads.scala`: Criação e ciclo de vida de threads nativas (`java.lang.Thread`).
  - `MemoriaCompartilhada.scala`: Demonstração de compartilhamento de Heap, *Race Conditions* e visibilidade com `@volatile`.
  - `Sincronizacao.scala`: Controle de concorrência com Monitor Locks (`synchronized`) e primitivas `AtomicInteger` (CAS).
- `src/main/scala/parte2_modelos/`
  - `CorrotinasFibras.scala`: Multitarefa cooperativa com fibras leves (*Green Threads*) utilizando **ZIO**.
  - `EventLoopPromises.scala`: Pool de eventos (`ExecutionContext`), `Future[T]` e `Promise[T]`.
  - `ModeloAtoresPekko.scala`: Modelo de Atores com estado isolado e troca de mensagens usando **Apache Pekko Typed**.
  - `OutrasFormasConcorrencia.scala`: Memória Transacional em Software (**ZSTM**) e Coleções Paralelas (`.par`).
- `slides/`: Contém a apresentação de slides detalhada da análise.

---

## Como Executar os Códigos

Pré-requisitos: **JDK 17+** (recomendado JDK 21) e **SBT (Scala Build Tool)** instalados.

No terminal, na raiz do projeto, execute:

```bash
# Compilar todos os exemplos
sbt compile

# Executar um exemplo específico:
sbt "runMain parte1_threads.executarCriacaoThreads"
sbt "runMain parte1_threads.executarMemoriaCompartilhada"
sbt "runMain parte1_threads.executarSincronizacao"
sbt "runMain parte2_modelos.CorrotinasFibras"
sbt "runMain parte2_modelos.executarEventLoopPromises"
sbt "runMain parte2_modelos.executarModeloAtores"
sbt "runMain parte2_modelos.OutrasFormasConcorrencia"