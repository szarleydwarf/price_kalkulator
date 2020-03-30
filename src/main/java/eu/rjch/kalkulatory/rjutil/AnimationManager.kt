package eu.rjch.kalkulatory.rjutil

import android.content.Context
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.widget.Button

class AnimationManager {

    fun didTapButon(btn: Button, ctx: Context?, animationId: Int) {
        val anim = AnimationUtils.loadAnimation(ctx, animationId)
        btn.startAnimation(anim)
    }

    fun didTapButonInterpolate(btn: Button, ctx: Context?, animationId: Int,
                              amplitude:Double, frequency:Double) {
        val anim = AnimationUtils.loadAnimation(ctx, animationId)
        val interpolator = MyInterpolator(amplitude, frequency)
        anim.interpolator = interpolator
        btn.startAnimation(anim)
    }


}

class MyInterpolator(amplitude: Double, frequency:Double) : Interpolator {
    val amp : Double = amplitude
    val freq : Double = frequency

    override fun getInterpolation(time: Float) : Float{
        return ((-1 * Math.pow(Math.E, (-time / amp).toDouble()) * Math.cos((freq * time).toDouble()) + 1).toFloat())
    }

}