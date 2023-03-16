package ru.logging

fun String.dropNamePackage(): String {
    return this.drop(this.lastIndexOf(".") + 1)
}