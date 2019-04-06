package com.klony.performance

import java.security.MessageDigest
import java.util.zip.Checksum
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

interface Hash<out T: Any> {
    fun update(b: ByteArray, off: Int, len: Int)

    fun getValue(): T
}

interface HashType<out T: Any> {
    fun createHash(): Hash<T>

    fun getHashName(): String?
}

class HashTypeChecksum<T: Checksum>(private val checksumClass: KClass<T>): HashType<Long> {
    override fun getHashName() = checksumClass.simpleName

    override fun createHash(): Hash<Long> {
        return object: Hash<Long> {
            private val checksum = checksumClass.createInstance()

            override fun update(b: ByteArray, off: Int, len: Int) {
                checksum.update(b, off, len)
            }

            override fun getValue(): Long {
                return checksum.value
            }
        }
    }
}

class HashTypeMessageDigest(private val messageDigestAlgorithm: String): HashType<ByteArray> {
    override fun getHashName() = messageDigestAlgorithm

    override fun createHash(): Hash<ByteArray> {
        return object: Hash<ByteArray> {
            private val messageDigest = MessageDigest.getInstance(messageDigestAlgorithm)

            override fun update(b: ByteArray, off: Int, len: Int) {
                messageDigest.update(b.sliceArray(IntRange(off, off + len - 1)))
            }

            override fun getValue(): ByteArray {
                return messageDigest.digest()
            }
        }
    }
}