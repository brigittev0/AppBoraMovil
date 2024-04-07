package pe.edu.idat.appborabora.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)

    fun getJwt(): String? {
        return sharedPreferences.getString("jwt", null)
    }

    fun getRole(): String? {
        return sharedPreferences.getString("role", null)
    }

    fun getIdentityDoc(): Int {
        return sharedPreferences.getInt("identityDoc", 0)
    }

    fun saveToSharedPrefs(username: String?, role: String?, jwt: String?, identityDoc: Int) {
        with (sharedPreferences.edit()) {
            putString("username", username)
            putString("role", role)
            putString("jwt", jwt)
            putInt("identityDoc", identityDoc)
            apply()
        }
    }
}