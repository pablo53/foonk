package net.pryszawa.foonk.lang

import net.pryszawa.foonk.FoonkException

abstract class FoonkInstruction {

    abstract fun go(ctx: FoonkComputingContext)

}

class FoonkAssignmentInstruction(private val variable: FoonkVariable, private val rExpr: FoonkExpression) : FoonkInstruction() {

    override fun go(ctx: FoonkComputingContext) {
        val fvalue = rExpr.compute(ctx)
        ctx.variables[variable] = fvalue
        println("${variable.name} = $fvalue")
    }

}

class FoonkVariableInstruction(private val variable: FoonkVariable) : FoonkInstruction() {

    override fun go(ctx: FoonkComputingContext) {
        val fvalue = ctx.variables[variable]
        println("${variable.name} = $fvalue")
    }

}

class FoonkErrorInstruction(private val ex: FoonkException) : FoonkInstruction() {

    override fun go(ctx: FoonkComputingContext) {
        println("ERROR: ${ex.message}")
    }

}