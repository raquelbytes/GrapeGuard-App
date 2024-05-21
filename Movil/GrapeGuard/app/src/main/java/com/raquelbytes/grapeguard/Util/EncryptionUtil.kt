import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.google.gson.Gson
import com.raquelbytes.grapeguard.API.Model.Usuario
import javax.crypto.Cipher

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {
    private val gson = Gson()

    fun transformarUsuarioToJson(usuario: Usuario): String {
        return gson.toJson(usuario)
    }

    fun transformarJsonToUsuaro(jsonUsuario: String): Usuario {
        return gson.fromJson(jsonUsuario, Usuario::class.java)
    }

    @Throws(Exception::class)
    fun encriptar(data: String, keyString: String): String {
        val key = keyAesDerivada(keyString)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encrypted = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    fun desencriptar(data: String, keyString: String): String {
        val key = keyAesDerivada(keyString)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decrypted = cipher.doFinal(Base64.decode(data, Base64.DEFAULT))
        return String(decrypted)
    }

    @Throws(Exception::class)
    private fun keyAesDerivada(password: String): SecretKeySpec {
        val salt = byteArrayOf(0xa7.toByte(), 0x21.toByte(), 0x2b.toByte(), 0xc2.toByte(), 0x56.toByte(), 0xee.toByte(), 0x77.toByte(), 0x99.toByte())
        val iterations = 65536
        val spec = PBEKeySpec(password.toCharArray(), salt, iterations, 256)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val secretKey = factory.generateSecret(spec)
        return SecretKeySpec(secretKey.encoded, "AES")
    }

    fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        // Decodifica la cadena Base64 a un array de bytes
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        // Convierte el array de bytes a un Bitmap
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}
