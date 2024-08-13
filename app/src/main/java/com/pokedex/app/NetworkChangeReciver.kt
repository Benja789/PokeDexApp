package com.pokedex.app
import android.app.Activity
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

class NetworkChangeReceiver(private val activity: Activity) : BroadcastReceiver() {
    private var dialog: AlertDialog? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (isOnline(context)) {
            val currentDialog = dialog
            if (currentDialog != null && currentDialog.isShowing) {
                currentDialog.dismiss()
                Toast.makeText(context, "Conectado a Internet", Toast.LENGTH_LONG).show()
            }
        } else {
            val builder = AlertDialog.Builder(activity, R.style.AlertDialogCustom)
            builder.setTitle("Error de conexión").setCancelable(false)
            val message = TextView(activity)
            message.text = "No posees conexion a interntet, estamos intentando reconectarnos..."
            message.gravity = Gravity.CENTER
            message.setTextColor(Color.BLACK)
            message.textSize = 18f
            dialog = builder.create()
            dialog?.setView(message, 50, 50, 50, 50)
            dialog?.show()
        }
    }

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}