val scala3Version = "3.3.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "concorrencia",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      // Testes
      "org.scalameta" %% "munit" % "1.0.0" % Test,

      // Coleções Paralelas do Scala
      "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",

      // ZIO (Corrotinas/Fibras e STM)
      "dev.zio" %% "zio" % "2.0.21",

      // Apache Pekko Typed (Modelo de Atores)
      "org.apache.pekko" %% "pekko-actor-typed" % "1.0.2",
      "org.slf4j" % "slf4j-simple" % "2.0.12"
    )
  )