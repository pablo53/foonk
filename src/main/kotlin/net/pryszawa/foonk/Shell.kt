package net.pryszawa.foonk

import net.pryszawa.foonk.lang.FoonkRunContext
import net.pryszawa.foonk.lang.toFoonkInstruction
import org.apache.sshd.server.Environment
import org.apache.sshd.server.ExitCallback
import org.apache.sshd.server.channel.ChannelSession
import org.apache.sshd.server.command.Command
import org.jline.reader.LineReaderBuilder
import org.jline.terminal.TerminalBuilder
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.lang.Exception

class Shell(val channel: ChannelSession,
            val server: Server) : Command, Runnable {

    private lateinit var input: InputStream
    private lateinit var output: OutputStream
    private lateinit var errOutput: OutputStream
    private lateinit var exitCBack: ExitCallback
    private lateinit var thread: Thread

    override fun start(channel: ChannelSession?, env: Environment?) {
        thread = Thread(this).apply { start() }
    }

    override fun destroy(channel: ChannelSession?) {
        thread.interrupt()
    }

    override fun setInputStream(input: InputStream?) {
        input?.let { this.input = it }
    }

    override fun setOutputStream(output: OutputStream?) {
        output?.let { this.output = it }
    }

    override fun setErrorStream(err: OutputStream?) {
        err?.let { this.errOutput = it }
    }

    override fun setExitCallback(callback: ExitCallback?) {
        callback?.let { this.exitCBack = it }
    }

    override fun run() {
        try {
            TerminalBuilder.builder().system(false).streams(input, output).build().use {
                val ctx = FoonkRunContext(out = PrintStream(output), err = PrintStream(errOutput))
                val reader = LineReaderBuilder.builder().terminal(it).build()
                var line = reader.readLine("Foonk>")
                while (line != null) {
                    if (line == ":q" || thread.isInterrupted) {
                        server.stopServer()
                        break
                    } else
                        line.toFoonkInstruction().go(ctx)
                    line = reader.readLine("\r\nFoonk>")
                }
                it.flush()
            }
            exitCBack.onExit(0)
        } catch (ex: Exception) {
            ex.printStackTrace()
            try {
                server.stopServer()
            } catch (ex2: Exception) {
                ex2.printStackTrace()
            }
            exitCBack.onExit(-1)
        }
    }

}