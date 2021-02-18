package net.pryszawa.foonk.lang

import net.pryszawa.foonk.FoonkException

abstract class FoonkInstruction {

    abstract fun go(ctx: FoonkRunContext)

}

class FoonkAssignmentInstruction(private val variable: FoonkVariable, private val rExpr: FoonkExpression) : FoonkInstruction() {

    override fun go(ctx: FoonkRunContext) {
        val fvalue = rExpr.compute(ctx)
        ctx.variables[variable] = fvalue
        ctx.out.println("${variable.name} = $fvalue")
        ctx.out.flush()
    }

}

class FoonkVariableInstruction(private val variable: FoonkVariable) : FoonkInstruction() {

    override fun go(ctx: FoonkRunContext) {
        val fvalue = ctx.variables[variable]
        ctx.out.println("${variable.name} = $fvalue")
        ctx.out.flush()
    }

}

class FoonkErrorInstruction(private val errorMessage: String) : FoonkInstruction() {

    constructor(ex: FoonkException) : this(ex.message ?: ex.javaClass.canonicalName)

    override fun go(ctx: FoonkRunContext) {
        ctx.err.println("ERROR: $errorMessage")
        ctx.err.flush()
    }

}