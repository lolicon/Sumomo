package dsl.beriru.regex

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.lexer.DefaultTokenizer

class Rex(private val pattern: String) {
    fun token() = DefaultTokenizer(Parser.tokens).tokenize(pattern).joinToString()
    fun ast() = Parser.parseToEnd(pattern)
    fun match(subject: String) = ast().match(subject, 0) { _, _ ->
        true
    }
}

val String.r
    get() = Rex(this)

fun alt(vararg remain: Regexp) = remain.reversed().fold(Fail) { ele: Regexp, acc ->
    Alternative(acc, ele)
}

fun seq(vararg remain: Regexp) = remain.reversed().fold(Pass) { ele: Regexp, acc ->
    Sequential(acc, ele)
}

fun seq(str : String) = str.toCharArray().map(::e).reversed().fold(Pass) { ele: Regexp, acc ->
    Sequential(acc, ele)
}


fun e(char: Char) = Exactly(char)
fun e(char: String) = Exactly(char[0])
fun lzy(child: Regexp, min: Int = 0, max: Int = Integer.MAX_VALUE) = Lazy(child, min, max)
fun grd(child: Regexp, min: Int = 0, max: Int = Integer.MAX_VALUE) = Greedy(child, min, max)





