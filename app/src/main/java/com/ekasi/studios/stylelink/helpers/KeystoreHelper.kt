package com.ekasi.studios.stylelink.helpers

import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher

class KeystoreHelper {

    private val keystore = KeyStore.getInstance("AndroidKeyStore")
    private val keyAlias = "access_token" // Replace with unique alias

    init {
        keystore.load(null) // Load keystore without password (managed by Android)
    }


    fun encrypt(data: String): String? {
        val secretKeyEntry = keystore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry
        val secretKey = secretKeyEntry.secretKey
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
    }

    fun decrypt(encryptedData: String): String? {
        val secretKeyEntry = keystore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry
        val secretKey = secretKeyEntry.secretKey
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedBytes = cipher.doFinal(Base64.decode(encryptedData, Base64.NO_WRAP))
        return String(decryptedBytes)
    }
}
