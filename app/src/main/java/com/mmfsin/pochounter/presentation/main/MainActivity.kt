package com.mmfsin.pochounter.presentation.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.mmfsin.pochounter.R
import com.mmfsin.pochounter.base.bedrock.BedRockActivity
import com.mmfsin.pochounter.databinding.ActivityMainBinding
import com.mmfsin.pochounter.utils.BEDROCK_BOOLEAN_ARGS
import com.mmfsin.pochounter.utils.BEDROCK_PARCELABLE_ARGS
import com.mmfsin.pochounter.utils.BEDROCK_STR_ARGS
import com.mmfsin.pochounter.utils.ROOT_ACTIVITY_NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        changeStatusBar(R.color.white)
    }

    private fun changeStatusBar(color: Int) {
        // Android 15+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
                view.setBackgroundColor(ContextCompat.getColor(this, color))
                view.setPadding(0, statusBarInsets.top, 0, 0)
                insets
            }

        } else {
            // For Android 14 and below
            @Suppress("DEPRECATION")
            window.statusBarColor = ContextCompat.getColor(this, color)
        }

        //true == dark
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

    }

    fun openBedRockActivity(
        navGraph: Int,
        strArgs: String? = null,
        booleanArgs: Boolean? = null,
        parcelable: Any? = null
    ) {
        val intent = Intent(this, BedRockActivity::class.java)
        strArgs?.let { intent.putExtra(BEDROCK_STR_ARGS, strArgs) }
        booleanArgs?.let { intent.putExtra(BEDROCK_BOOLEAN_ARGS, booleanArgs) }
        parcelable?.let { intent.putExtra(BEDROCK_PARCELABLE_ARGS, parcelable as Parcelable) }
        intent.putExtra(ROOT_ACTIVITY_NAV_GRAPH, navGraph)
        startActivity(intent)
    }
}