package com.example.theexpendables

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils

class AddExpenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        overridePendingTransition(0, 0)
        setContentView(R.layout.new_expense)

        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
//            new_expense_layout.setBackgroundColor(animator.animatedValue as Int)
        }
        // Fade animation for the Popup Window
//        popup_window_view_with_border.alpha = 0f
//        popup_window_view_with_border.animate().alpha(1f).setDuration(500).setInterpolator(
//            DecelerateInterpolator()
//        ).start()

        colorAnimation.start()
    }

    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
//            popup_window_background.setBackgroundColor(
//                animator.animatedValue as Int
//            )
        }

        // Fade animation for the Popup Window when you press the back button
//        popup_window_view_with_border.animate().alpha(0f).setDuration(500).setInterpolator(
//            DecelerateInterpolator()
//        ).start()

        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
//                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }

    private fun setWindowFlag(activity: Activity, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_BLUR_BEHIND
        } else {
            winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        }
        win.attributes = winParams
    }
}