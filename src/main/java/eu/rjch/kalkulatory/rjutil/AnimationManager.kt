package eu.rjch.kalkulatory.rjutil

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.Button

class AnimationManager {

    fun didTapButon(btn: Button, ctx: Context?, animationId: Int) {
        val anim = AnimationUtils.loadAnimation(ctx, animationId)
        btn.startAnimation(anim)
    }

    fun didTapButonInterpolate(btn: Button, ctx: Context?, animationId: Int) {
        val anim = AnimationUtils.loadAnimation(ctx, animationId)
        anim.setInterpolator { interpolate(2500f, 0.2f, 20f) }
        btn.startAnimation(anim)
    }


    fun interpolate(time: Float, amplitude: Float, frequency : Float) : Float{
        return ((-1 * Math.pow(Math.E, (-time / amplitude).toDouble()) * Math.cos((frequency * time).toDouble()) + 1).toFloat())
    }
}