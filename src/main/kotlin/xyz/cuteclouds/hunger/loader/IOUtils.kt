package xyz.cuteclouds.hunger.loader

import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import kotlin.text.Charsets.UTF_8

fun Path.bytes(): ByteArray {
    return Files.readAllBytes(this)
}

@JvmOverloads
fun Path.string(charset: Charset = UTF_8): String {
    return String(bytes(), charset)
}

@JvmOverloads
fun Path.lines(charset: Charset = UTF_8): List<String> {
    return Files.readAllLines(this, charset)
}

fun Path.write(content: ByteArray) {
    Files.write(this, content)
}

@JvmOverloads
fun Path.write(content: String, charset: Charset = UTF_8) {
    write(content.toByteArray(charset))
}

//region File overloads

fun File.bytes(): ByteArray = toPath().bytes()

@JvmOverloads
fun File.string(charset: Charset = UTF_8): String = toPath().string(charset)

@JvmOverloads
fun File.lines(charset: Charset = UTF_8): List<String> = toPath().lines(charset)

fun File.write(content: ByteArray) = toPath().write(content)

@JvmOverloads
fun File.write(content: String, charset: Charset = UTF_8) = toPath().write(content, charset)

//endregion