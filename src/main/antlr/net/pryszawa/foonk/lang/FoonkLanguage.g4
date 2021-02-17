grammar FoonkLanguage;

@parser::header {
package net.pryszawa.foonk.lang;

import net.pryszawa.foonk.lang.FoonkVariable;
import net.pryszawa.foonk.lang.FoonkExpression;
import net.pryszawa.foonk.lang.FoonkVariableExpression;
import net.pryszawa.foonk.lang.FoonkConstExpression;
import net.pryszawa.foonk.lang.FoonkAddExpression;
import net.pryszawa.foonk.lang.FoonkSubtractExpression;
import net.pryszawa.foonk.lang.FoonkMultiplyExpression;
import net.pryszawa.foonk.lang.FoonkDivideExpression;
import net.pryszawa.foonk.lang.FoonkPowerExpression;
import net.pryszawa.foonk.lang.FoonkInstruction;
import net.pryszawa.foonk.lang.FoonkAssignmentInstruction;
import net.pryszawa.foonk.lang.FoonkVariableInstruction;

}

@lexer::header {
package net.pryszawa.foonk.lang;
}

@lexer::members {
}

@parser::members {
}

instr returns [FoonkInstruction instruction]
      : var EOF { $instruction = new FoonkVariableInstruction($var.ctx.variable); }
      | asgmt EOF { $instruction = $asgmt.ctx.instruction; }
      ;

var returns [FoonkVariable variable]
//           @init { $variable = new FoonkVariable(); }
           : IDENTIFIER { $variable = new FoonkVariable($IDENTIFIER.text); }
           ;

rexpr returns [FoonkExpression expr]
     : IDENTIFIER { $expr = new FoonkVariableExpression(new FoonkVariable($IDENTIFIER.text)); }
     | NUMBER { $expr = new FoonkConstExpression(Double.parseDouble($NUMBER.text)); }
     | '(' rexpr ')' { $expr = $rexpr.ctx.expr; }
     | l=rexpr '+' r=rexpr { $expr = new FoonkAddExpression($l.ctx.expr, $r.ctx.expr); }
     | l=rexpr '-' r=rexpr { $expr = new FoonkSubtractExpression($l.ctx.expr, $r.ctx.expr); }
     | l=rexpr '*' r=rexpr { $expr = new FoonkMultiplyExpression($l.ctx.expr, $r.ctx.expr); }
     | l=rexpr '/' r=rexpr { $expr = new FoonkDivideExpression($l.ctx.expr, $r.ctx.expr); }
     | l=rexpr '^' r=rexpr { $expr = new FoonkPowerExpression($l.ctx.expr, $r.ctx.expr); }
     ;

asgmt returns [FoonkAssignmentInstruction instruction]
      : var BECOMES rexpr { $instruction = new FoonkAssignmentInstruction($var.ctx.variable, $rexpr.ctx.expr); }
      ;

IDENTIFIER : [a-zA-Z_][a-zA-Z_0-9]* ;
NUMBER : [1-9][0-9]* ('.' [0-9]*)? ;
BECOMES : ':' '=' ;
WS : [ \r\n\t] -> skip;
