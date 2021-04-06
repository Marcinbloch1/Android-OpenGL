package pl.polsl.lab5v2

import android.content.Context
import android.opengl.GLSurfaceView


class MySurface(context: Context) : GLSurfaceView(context) {
    init {
        setRenderer(MyRenderer())
    }
}