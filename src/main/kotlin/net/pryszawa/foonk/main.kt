package net.pryszawa.foonk

fun main(args: Array<String>) {
    println("Foonk started")
    val cfg = Config(args)
    val server = Server(cfg)
    server.startServer()
    server.waitForServer()
    println("Foonk stopped")
}
