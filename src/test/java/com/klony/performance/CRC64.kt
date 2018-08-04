package com.klony.performance

import java.util.zip.Checksum
import net.boeckling.crc.CRC64 as OriginalCRC64

class CRC64 : OriginalCRC64(), Checksum {
    /**
     * Updates the checksum with the specified byte (the low eight
     * bits of the argument b).
     *
     * @param b the byte to update the checksum with
     */
    override fun update(b: Int) {
        super.update(byteArrayOf(b.toByte()), 1)
    }

    override fun update(b: ByteArray, off: Int, len: Int) {
        super.update(b.sliceArray(IntRange(off, off + len - 1)), len)
    }

    override fun reset() {
        TODO("not implemented - there is no way to reset a ")
    }

}