package net.pryszawa.foonk.lang

import net.pryszawa.foonk.FoonkException
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.RecognitionException
import java.io.InputStream


inline fun InputStream.toFoonkInstruction(): FoonkInstruction = ANTLRInputStream(this).toFoonkInstruction()

inline fun String.toFoonkInstruction(): FoonkInstruction = ANTLRInputStream(this).toFoonkInstruction()

inline fun ANTLRInputStream.toFoonkInstruction(): FoonkInstruction =
    try {
        val instr = FoonkLanguageParser(CommonTokenStream(FoonkLanguageLexer(this))).instr()
        instr.instruction
    } catch (ex: RecognitionException) {
        FoonkErrorInstruction(FoonkException(message = ex.message, cause = ex))
    }
