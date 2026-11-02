package parte2_modelos

import zio.*

object CorrotinasFibras extends ZIOAppDefault:

  def tarefaCooperativa(id: Int, duracaoMs: Int): UIO[Int] =
    for
      _ <- Console.printLine(s"[Fibra $id] Iniciada na thread: ${Thread.currentThread().getName}").orDie
      // ZIO.sleep suspende a fibra cooperativamente SEM bloquear a thread do SO!
      _ <- ZIO.sleep(Duration.fromMillis(duracaoMs))
      _ <- Console.printLine(s"[Fibra $id] Concluída!").orDie
    yield id * 10

  def run: ZIO[Any, Throwable, Unit] =
    for
      _      <- Console.printLine("=== PARTE 2.1: Corrotinas e Fibras Leves (ZIO) ===")
      // .fork cria fibras leves (Green Threads) que rodam de forma concorrente
      fibra1 <- tarefaCooperativa(1, 400).fork
      fibra2 <- tarefaCooperativa(2, 200).fork
      // .join suspende até que o resultado de cada fibra esteja pronto
      r1     <- fibra1.join
      r2     <- fibra2.join
      _      <- Console.printLine(s"Soma dos resultados das fibras: ${r1 + r2}")
    yield ()