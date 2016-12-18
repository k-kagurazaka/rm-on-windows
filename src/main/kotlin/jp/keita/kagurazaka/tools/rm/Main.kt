package jp.keita.kagurazaka.tools.rm

import java.io.File

fun main(args: Array<String>) {
    try {
        val targetDir = getTargetDir(args)

        println("Renaming for delete...")
        renameForDelete(targetDir)

        println("Deleting...")
        targetDir.deleteRecursively()
    } catch (e: Throwable) {
        println(e.message)
    }
}

fun getTargetDir(args: Array<String>): File {
    if (args.size != 1) {
        throw RuntimeException("Usage: rm.jar [target dir]")
    }

    val targetDir = File(args[0]).absoluteFile

    if (!targetDir.exists()) {
        throw RuntimeException("Target directory does not exists: ${targetDir.absolutePath}")
    }
    if (!targetDir.isDirectory) {
        throw RuntimeException("Target must be a directory: ${targetDir.absolutePath}")
    }

    return targetDir
}

fun renameForDelete(dir: File) {
    var count = 0
    dir.listFiles {
        file, name ->
        File(file, name).isDirectory
    }.forEach {
        val dest = File(it.parent, "$count")
        count++
        it.renameTo(dest)
        renameForDelete(dest)
    }
}
