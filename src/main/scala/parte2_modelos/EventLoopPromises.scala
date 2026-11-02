package parte2_modelos

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import scala.util.{Failure, Success}

@main def executarEventLoopPromises(): Unit =
  println("=== PARTE 2.2: Event Loop (ExecutionContext) e Modelo de Promises ===")

  // Promise é o contêiner de escrita única (produtor)
  val promessaPedido = Promise[String]()

  // Future é a visão somente-leitura associada à Promise (consumidor)
  val futuroPedido: Future[String] = promessaPedido.future

  // Registrando callbacks não-bloqueantes que serão agendados no ExecutionContext
  val pipelineProcessamento = futuroPedido
    .map(produto => s"Pedido confirmado para: ${produto.toUpperCase}")
    .map(mensagem => s"[Notificação] $mensagem")

  pipelineProcessamento.onComplete {
    case Success(resultado) =>
      println(s"Callback executado no Event Pool (${Thread.currentThread().getName}): $resultado")
    case Failure(erro) =>
      println(s"Falha no processamento: ${erro.getMessage}")
  }

  // Simulando um produtor assíncrono que completa a Promise após 300ms
  Future {
    Thread.sleep(300)
    println("[Produtor Assíncrono] Preenchendo a Promise...")
    promessaPedido.success("Notebook Gamer Scala")
  }

  // Aguarda apenas no método main para a JVM não encerrar antes do callback
  Await.result(pipelineProcessamento, 2.seconds)