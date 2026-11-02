package parte2_modelos

import org.apache.pekko.actor.typed.{ActorRef, ActorSystem, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object AtorContaBancaria:
  // Protocolo de mensagens imutáveis que o Ator aceita na sua Mailbox
  enum Comando:
    case Depositar(valor: Int)
    case ConsultarSaldo(responderPara: ActorRef[RespostaSaldo])

  case class RespostaSaldo(saldoAtual: Int)

  // Comportamento do Ator: sem variáveis mutáveis (var) nem locks!
  def apply(saldo: Int = 0): Behavior[Comando] =
    Behaviors.receiveMessage {
      case Comando.Depositar(valor) =>
        val novoSaldo = saldo + valor
        println(s"[AtorConta] Depósito de R$$ $valor processado. Saldo isolado: R$$ $novoSaldo")
        apply(novoSaldo) // Retorna novo comportamento com estado atualizado

      case Comando.ConsultarSaldo(cliente) =>
        cliente ! RespostaSaldo(saldo)
        Behaviors.same
    }

@main def executarModeloAtores(): Unit =
  println("=== PARTE 2.3: Modelo de Atores (Apache Pekko / Akka Typed) ===")

  val sistema = ActorSystem(
    Behaviors.setup[AtorContaBancaria.RespostaSaldo] { contexto =>
      val conta = contexto.spawn(AtorContaBancaria(0), "ator-conta-principal")

      // Envio de mensagens assíncronas (!) sem bloqueio
      conta ! AtorContaBancaria.Comando.Depositar(150)
      conta ! AtorContaBancaria.Comando.Depositar(350)
      conta ! AtorContaBancaria.Comando.ConsultarSaldo(contexto.self)

      Behaviors.receiveMessage { resposta =>
        println(s"[AtorCliente] Resposta recebida via mensagem: Saldo final = R$$ ${resposta.saldoAtual}")
        Behaviors.stopped
      }
    },
    "SistemaDeAtoresScala"
  )

  Thread.sleep(800)
  sistema.terminate()