package net.pryszawa.foonk

import org.apache.commons.cli.*

class Config(args: Array<String>) {

    val options: CommandLine

    init {
        val opts = Options()

        Option("p", "port", true, "Server port (default 5335))").apply {
            isRequired = false
        }.let { opts.addOption(it) }

        Option("h", "host", true, "Server network interface (deafult 0.0.0.0)").apply {
            isRequired = false
        }.let { opts.addOption(it) }

        Option("u", "username", true, "Session user name (default: none)").apply {
            isRequired = false
        }.let { opts.addOption(it) }

        Option("w", "password", true, "Session password (default: none)").apply {
            isRequired = false
        }.let { opts.addOption(it) }

        val parser: CommandLineParser = DefaultParser()

        try {
            options = parser.parse(opts, args)
        } catch (ex: ParseException) {
            HelpFormatter().printHelp("foonk", opts)
            throw FoonkException(message = "Usage error", cause = ex)
        }

    }

    fun getPort() = options.getOptionValue("port", "5335").toIntOrNull() ?: 8080

    fun getHost() = options.getOptionValue("host", "0.0.0.0")

    fun getLogin() = options.getOptionValue("login", null)

    fun getPassword() = options.getOptionValue("password", null)

}