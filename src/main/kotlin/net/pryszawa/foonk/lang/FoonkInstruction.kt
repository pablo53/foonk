package net.pryszawa.foonk.lang

abstract class FoonkInstruction {

    abstract fun go(ctx: FoonkComputingContext)

}

class FoonkAssignmentInstruction(val variable: FoonkVariable, val rExpr: FoonkExpression) : FoonkInstruction() {

    override fun go(ctx: FoonkComputingContext) {
        val fvalue = rExpr.compute(ctx)
        ctx.variables[variable] = fvalue
        println("${variable.name} = $fvalue")
    }

}

class FoonkVariableInstruction(val variable: FoonkVariable) : FoonkInstruction() {

    override fun go(ctx: FoonkComputingContext) {
        val fvalue = ctx.variables[variable]
        println("${variable.name} = $fvalue")
    }

}