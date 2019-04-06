package com.klony.performance

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File
import java.time.Duration
import java.time.Instant
import java.util.zip.Adler32
import java.util.zip.CRC32

@Disabled
class PerformanceTests {
    private val file = File("/Users/pcoltau/VirtualBox VMs/Win7/Win7.vdi")
    private val durationInSec = 10

    @Test
    fun `Performance test Adler32`() {
        timeHash(HashTypeChecksum(Adler32::class))
    }

    @Test
    fun `Performance test CRC32`() {
        timeHash(HashTypeChecksum(CRC32::class))
    }

    @Test
    fun `Performance test CRC64`() {
        timeHash(HashTypeChecksum(CRC64::class))
    }

    @Test
    fun `Performance test MD5`() {
        timeHash(HashTypeMessageDigest("MD5"))
    }

    @Test
    fun `Performance test SHA-1`() {
        timeHash(HashTypeMessageDigest("SHA-1"))
    }

    @Test
    fun `Performance test SHA-256`() {
        timeHash(HashTypeMessageDigest("SHA-256"))
    }

    @Test
    fun `Performance test SHA-512`() {
        timeHash(HashTypeMessageDigest("SHA-512"))
    }

    private fun <T: Any> calculateHash(hashType: HashType<T>): T {
        val hash = hashType.createHash()
        file.forEachBlock(1048576) { buffer, _ ->
            hash.update(buffer, 0, buffer.size)
        }
        return hash.getValue()
    }

    private fun <T: Any> timeHash(hashType: HashType<T>) {
        val checksumClassName = hashType.getHashName()
        println("Counting # of $checksumClassName checksums in $durationInSec seconds: ")
        val durationInMS = durationInSec * 1000
        val time = Instant.now()
        var count = 0
        //while (Duration.between(time, Instant.now()).toMillis() < durationInMS) {
            calculateHash(hashType)
            count++
        //}
        //println(count)
        println(Duration.between(time, Instant.now()).toMillis())
    }
}

