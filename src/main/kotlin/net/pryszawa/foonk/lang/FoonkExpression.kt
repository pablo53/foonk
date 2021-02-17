package net.pryszawa.foonk.lang

import kotlin.math.pow

typealias FoonkValueBiOperation = (FoonkValue, FoonkValue) -> FoonkValue

abstract class FoonkExpression() {

    abstract fun compute(ctx: FoonkComputingContext): FoonkValue

}

class FoonkConstExpression(val value: FoonkValue) : FoonkExpression() {

    override fun compute(ctx: FoonkComputingContext) = value

}

class FoonkVariableExpression(val identifier: FoonkVariable) : FoonkExpression() {

    override fun compute(ctx: FoonkComputingContext) = ctx.variables[identifier] ?: neutralFunkValue

}

abstract class FoonkBiExpression(val lvalue: FoonkExpression, val rvalue: FoonkExpression) : FoonkExpression() {

    override fun compute(ctx: FoonkComputingContext) = operation(lvalue.compute(ctx), rvalue.compute(ctx))

    abstract val operation: FoonkValueBiOperation

}

class FoonkAddExpression(lvalue: FoonkExpression, rvalue: FoonkExpression) : FoonkBiExpression(lvalue, rvalue) {

    override val operation: FoonkValueBiOperation = FoonkValue::plus

}

class FoonkSubtractExpression(lvalue: FoonkExpression, rvalue: FoonkExpression) : FoonkBiExpression(lvalue, rvalue) {

    override val operation: FoonkValueBiOperation = FoonkValue::minus

}

class FoonkMultiplyExpression(lvalue: FoonkExpression, rvalue: FoonkExpression) : FoonkBiExpression(lvalue, rvalue) {

    override val operation: FoonkValueBiOperation = FoonkValue::times

}

class FoonkDivideExpression(lvalue: FoonkExpression, rvalue: FoonkExpression) : FoonkBiExpression(lvalue, rvalue) {

    override val operation: FoonkValueBiOperation = FoonkValue::div

}

class FoonkPowerExpression(lvalue: FoonkExpression, rvalue: FoonkExpression) : FoonkBiExpression(lvalue, rvalue) {

    override val operation: FoonkValueBiOperation = FoonkValue::pow

}
