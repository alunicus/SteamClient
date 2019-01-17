package com.github.alunicus.steamclient

import java.io.File
import java.io.IOException
import java.util.*

class ResourceReader {
    fun getText(fileName: String): String {

        val result = StringBuilder()
        val classLoader = javaClass.classLoader

        if (classLoader != null) {
            val file = File(classLoader.getResource(fileName)!!.file)

            try {
                Scanner(file).use { scanner ->
                    while (scanner.hasNextLine()) {
                        val line = scanner.nextLine()
                        result.append(line).append("\n")
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return result.toString()
    }
}