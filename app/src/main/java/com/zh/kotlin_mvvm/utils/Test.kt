package com.zh.kotlin_mvvm.utils


class Test (var name:String)
val <T> List<T>.lastIndex:Int get() = size-1

fun Test.haha(){
    print("打印_$name")
}


fun main() {
   Test("大狗子").haha()
}
