import android.os.Handler
import android.os.Looper

class RepeatedTask(private val interval: Long) {
    private val handler = Handler(Looper.getMainLooper())
    var executeTask = {}
    private val runnable = object : Runnable {
        override fun run() {
            executeTask()
            handler.postDelayed(this, interval)
        }
    }

    fun start() {
        handler.post(runnable)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
    }
}