package net.pryszawa.foonk

import org.apache.sshd.server.SshServer
import org.apache.sshd.server.channel.ChannelSession
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider
import org.apache.sshd.server.session.ServerSession

class Server(config: Config) : Runnable {

    private lateinit var thread: Thread

    private val sshServer = SshServer.setUpDefaultServer().apply {
        this.host = config.getHost()
        this.port = config.getPort()
        this.setPasswordAuthenticator { username: String?, password: String?, session: ServerSession? ->
            (config.getLogin()?.let { it == username } ?: true) &&
                    (config.getPassword()?.let { it == password } ?: true)
        }
        this.keyPairProvider = SimpleGeneratorHostKeyProvider()
        this.setShellFactory { channel: ChannelSession -> Shell(channel, this@Server) }
    }

    fun startServer() {
        thread = Thread(this, "server-main")
        thread.start()
    }

    fun waitForServer() {
        thread.join()
    }

    fun stopServer() {
        thread.interrupt()
    }

    override fun run() {
        sshServer.start()
        while (!thread.isInterrupted) {
            try {
                Thread.sleep(5000)
            } catch (ex: InterruptedException) {
                break
            }
        }
        sshServer.stop()
    }

}