package net.pryszawa.foonk.lang

import java.io.PrintStream

class FoonkRunContext(
    val out: PrintStream,
    val err: PrintStream,
    val variables: MutableMap<FoonkVariable, FoonkValue> = HashMap(),
)
