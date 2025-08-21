package com.mmfsin.pochounter.utils

import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.mmfsin.pochounter.base.dialog.ErrorDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun FragmentActivity.showErrorDialog(goBack: Boolean = false) {
    val dialog = ErrorDialog(goBack)
    this.let { dialog.show(it.supportFragmentManager, "") }
}

fun FragmentActivity?.showFragmentDialog(dialog: DialogFragment) =
    this?.let { dialog.show(it.supportFragmentManager, "") }

fun <T1 : Any, T2 : Any, R : Any> checkNotNulls(p1: T1?, p2: T2?, block: (T1, T2) -> R): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun View.animateY(pos: Float, duration: Long, onEnd: () -> Unit = {}) =
    this.animate().translationY(pos).setDuration(duration).withEndAction { onEnd() }

fun View.animateX(pos: Float, duration: Long, onEnd: () -> Unit = {}) =
    this.animate().translationX(pos).setDuration(duration).withEndAction { onEnd() }

fun View.showAlpha(visible: Boolean, duration: Long, onEnd: () -> Unit = {}) {
    val visibility = if (visible) 1f else 0f
    this.animate().alpha(visibility).setDuration(duration).withEndAction { onEnd() }
}

fun countDown(millis: Long, action: () -> Unit) {
    object : CountDownTimer(millis, 1000) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            action()
        }
    }.start()
}

fun Date.toSpanishDate(): String {
    val formatter = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
    val raw = formatter.format(this)
    return raw.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale("es", "ES"))
        else it.toString()
    }
}

fun formatList(items: List<String>): String {
    return when (items.size) {
        0 -> ""
        1 -> items[0]
        2 -> items.joinToString(" y ")
        else -> items.dropLast(1).joinToString(", ") + " y " + items.last()
    }
}

fun getRandomFunnyWord(): String {
    val words = listOf(
        "Zanahorílope",
        "Chirimbolo",
        "Tortugón",
        "Piruletasco",
        "Gusanitoide",
        "Burbujóptero",
        "Ñoñifante",
        "Patatús",
        "Tragaldabas",
        "Chupatintas",
        "Papanatas",
        "Ratonóptero",
        "Pelusín",
        "Cachivachete",
        "Mofletudo",
        "Lombricóptero",
        "Zampabollos",
        "Chicharrónix",
        "Tiquismiquis",
        "Gorilóptero"
    )
    return words.random()
}