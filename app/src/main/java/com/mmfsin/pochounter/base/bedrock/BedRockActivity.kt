package com.mmfsin.pochounter.base.bedrock

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import com.mmfsin.pochounter.R
import com.mmfsin.pochounter.databinding.ActivityBedrockBinding
import com.mmfsin.pochounter.utils.ROOT_ACTIVITY_NAV_GRAPH
import com.mmfsin.pochounter.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BedRockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBedrockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBedrockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBar(R.color.white)
        setUpNavGraph()
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

    private fun setUpNavGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.br_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = intent.getIntExtra(ROOT_ACTIVITY_NAV_GRAPH, -1)
        navController.apply { if (navGraph != -1) setGraph(navGraph) else error() }
    }

    fun setUpToolbar(title: String? = "", action: () -> Unit) {
        binding.toolbar.apply {
            ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            tvTitle.text = title
            btnAdd.setOnClickListener { action() }
        }
    }

    private fun error() = showErrorDialog()
}