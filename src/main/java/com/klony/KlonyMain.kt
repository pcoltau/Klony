package com.klony

import com.klony.organiser.ResultOrganizer
import com.klony.utils.Console
import com.klony.utils.extensionCheckCaseInsensitive
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import java.io.File

class KlonyMain {
    companion object {
        @JvmStatic fun main(args: Array<String>) = mainBody {
            val parsedArgs = ArgParser(args).parseInto(::Args)

            val formattedExtensions = parsedArgs.includedExtensions.map {
                it.extensionCheckCaseInsensitive(parsedArgs.caseSensitive)
            }

            val sizes = mutableMapOf<Long, File>()
            val checksumMap = ChecksumMap()

            parsedArgs.directoryRoots.forEach { fileRoot ->
                fileRoot.walkTopDown().forEach file@{ file ->
                    if (file.isFile) {
                        guard(checkFileExtension(file.extension, parsedArgs, formattedExtensions)).otherwise {
                            return@file // continue with next file
                        }

                        val size = file.length()

                        guard(checkFileSizeLimits(size, parsedArgs)).otherwise {
                            return@file // continue with next file
                        }

                        // Is there already a file with the same size?
                        val existingFile = sizes[size]
                        if (existingFile != null) {
                            // There is already a file with the same size! So we add the new file to the ChecksumMap
                            checksumMap.add(file)
                            // We make sure to also add the existing file. If it is already there, it won't be added again
                            checksumMap.add(existingFile)
                        }
                        sizes[size] = file
                    } else if (file.isDirectory && parsedArgs.reportProgress) {
                        // TODO: Performance test the print - perhaps we want to only print every 100ms?
                        Console.erasePreviousLine()
                        Console.printLine("Scanning: $file\r")
                    }
                }
            }

            Console.erasePreviousLine()
            val result = ResultOrganizer.organise(checksumMap, parsedArgs)
            Console.printResult(result, parsedArgs.outputFormatter)
        }

        private fun checkFileExtension(extension: String, parsedArgs: Args, formattedExtensions: List<String>): Boolean {
            return formattedExtensions.isEmpty() || formattedExtensions.contains(extension.extensionCheckCaseInsensitive(parsedArgs.caseSensitive))
        }

        private fun checkFileSizeLimits(size: Long, parsedArgs: Args) =
                size >= parsedArgs.sizeLimitLower && size <= parsedArgs.sizeLimitHigher

        @Suppress("NOTHING_TO_INLINE")
        private inline fun guard(condition: Boolean) = object {
            inline fun otherwise(otherwise: () -> Unit) {
                if (!condition) {
                    otherwise()
                    check(false) {
                        "If a guard fails, the 'otherwise' block must return!"
                    }
                }
            }
        }
    }
}