package parte2_modelos

import scala.collection.parallel.CollectionConverters.*
import zio.*
import zio.stm.*

object OutrasFormasConcorrencia extends ZIOAppDefault:

  // Transferência bancária atômica usando Software Transactional Memory (STM)
  def transferirSTM(origem: TRef[Int], destino: TRef[Int], valor: Int): UIO[Unit] =
    STM.atomically {
      for
        saldoOrigem <- origem.get
        _           <- if saldoOrigem < valor then STM.retry else STM.unit
        _           <- origem.update(_ - valor)
        _           <- destino.update(_ + valor)
      yield ()
    }

  def run: ZIO[Any, Throwable, Unit] =
    for
      _ <- Console.printLine("=== PARTE 2.4: Outras Formas de Concorrência (STM e Coleções Paralelas) ===")

      // 1. Demonstração de Coleções Paralelas (.par)
      _ <- ZIO.attempt {
        val numeros = (1 to 10).toList
        val somaParalela = numeros.par.map { n =>
          println(s"[Coleção Paralela] Processando $n na thread ${Thread.currentThread().getName}")
          n * n
        }.sum
        println(s"Resultado da soma dos quadrados em paralelo (.par): $somaParalela")
      }

      // 2. Demonstração de Software Transactional Memory (ZSTM)
      contaA <- TRef.makeCommit(1000)
      contaB <- TRef.makeCommit(200)
      _      <- transferirSTM(contaA, contaB, 400)
      saldoA <- contaA.get.commit
      saldoB <- contaB.get.commit
      _      <- Console.printLine(s"[STM Transacional] Conta A: R$$ $saldoA | Conta B: R$$ $saldoB")
    yield ()