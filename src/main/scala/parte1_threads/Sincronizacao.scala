package parte1_threads

import java.util.concurrent.atomic.AtomicInteger

class CofreSincronizado:
  private var saldoMonitor: Int = 0
  val saldoAtomico = new AtomicInteger(0)

  // Abordagem 1: Exclusão mútua tradicional com Monitor Lock (synchronized)
  def depositarComLock(valor: Int): Unit =
    this.synchronized {
      saldoMonitor += valor
    }

  // Abordagem 2: Lock-free utilizando instruções atômicas de hardware (CAS)
  def depositarAtomico(valor: Int): Unit =
    saldoAtomico.addAndGet(valor)

  def obterSaldoMonitor: Int = this.synchronized { saldoMonitor }

@main def executarSincronizacao(): Unit =
  println("=== PARTE 1.3: Sincronização de Threads ===")
  val cofre = new CofreSincronizado()

  val trabalhadores = (1 to 10).map { _ =>
    new Thread(() => {
      for _ <- 1 to 1000 do
        cofre.depositarComLock(1)
        cofre.depositarAtomico(1)
    })
  }

  trabalhadores.foreach(_.start())
  trabalhadores.foreach(_.join())

  println(s"Saldo com synchronized (esperado 10000): ${cofre.obterSaldoMonitor}")
  println(s"Saldo com AtomicInteger (esperado 10000): ${cofre.saldoAtomico.get()}")