import java.io.File
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.zip.Adler32

fun calculateChecksum(file: File): Long {
    val checksum = Adler32()
    file.forEachBlock { buffer, _ ->
        checksum.update(buffer)
    }
    return checksum.value
}


val file = File("/Users/pcoltau/Pictures/Camera/2017.11/ida_iphone6s/IMG_0521.MOV")

val time = Instant.now()
var i = 0
while (i < 100) {
    calculateChecksum(file)
    i++
}
print(Duration.between(time, Instant.now()).toMillis())