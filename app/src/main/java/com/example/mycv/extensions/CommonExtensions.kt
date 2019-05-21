package com.example.mycv.extensions


val List<String>.newLineSeparatedString
    get() = this.joinToString("\n")
