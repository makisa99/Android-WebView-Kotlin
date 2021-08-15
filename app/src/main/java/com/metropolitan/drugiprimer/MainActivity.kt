package com.metropolitan.drugiprimer

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.metropolitan.drugiprimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var ucitaoPrviPut = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ucitajLink("https://www.metropolitan.ac.rs/")
    }

    fun ucitajLink(sajt: String) {
        if (isOnline(this)) {
            binding.vebvju.loadUrl(sajt)
            binding.vebvju.visibility = View.VISIBLE
            if(sajt == "https://www.metropolitan.ac.rs/"){
                ucitaoPrviPut = true
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Nemate pristup internetu",
                Toast.LENGTH_LONG
            ).show()
            binding.vebvju.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.osvezivanje) {
            if (isOnline(this)) {
                binding.vebvju.visibility = View.VISIBLE
                if(ucitaoPrviPut) {
                    binding.vebvju.reload()
                }
                else{
                    ucitajLink("https://www.metropolitan.ac.rs/")
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Nemate pristup internetu",
                    Toast.LENGTH_LONG
                ).show()
                binding.vebvju.visibility = View.GONE
            }
            return true
        }
        if (id == R.id.poziv) {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "0112030885")
            startActivity(dialIntent)
            return true
        }
        if (id == R.id.uplatnica) {
            ucitajLink("https://www.metropolitan.ac.rs/upis/uplatnica/")
            return true
        }
        if (id == R.id.izlazak) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}