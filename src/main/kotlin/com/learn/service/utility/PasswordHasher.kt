package com.learn.service.utility

import java.security.SecureRandom
import java.util.HexFormat
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class PasswordHasher {

    private val algorithm = "PBKDF2WithHmacSHA512"
    private val iterations = 120_000
    private val keyLength = 256
    private val secret = "PantodKontodAsud"

    private fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)

        return salt
    }

    private fun ByteArray.toHexString(): String = HexFormat.of().formatHex(this)

    fun  generateHash(password: String): String {
        val salt = generateSalt().toHexString()
        val combineWithSalt = "$salt$secret".toByteArray()
        val factory = SecretKeyFactory.getInstance(algorithm)
        val spec = PBEKeySpec(password.toCharArray(), combineWithSalt, iterations, keyLength)
        val key = factory.generateSecret(spec)

        return key.encoded.toHexString()
    }
}