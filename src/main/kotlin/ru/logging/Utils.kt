package ru.logging

fun String.dropNamePackage(): String  = this.drop(this.lastIndexOf(".") + 1)