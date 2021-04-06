package pl.polsl.lab5v2

import android.content.res.Resources
import android.opengl.GLSurfaceView
import android.opengl.GLU
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.opengles.GL10
import kotlin.math.cos
import kotlin.math.sin


class MyRenderer : GLSurfaceView.Renderer {

    private var radius = 0.1f
    private var aspectRatio = 0.0f

    private var centerX = 0.0f
    private var centerY = 0.0f

    private var direction = -1.0f
    private var distance = 0.0f

    private var points = 20
    private var pointsArray = FloatArray(points * 3) {0.0f}

    init {
        val width = Resources.getSystem().displayMetrics.widthPixels.toFloat()
        val height = Resources.getSystem().displayMetrics.heightPixels.toFloat()

        this.aspectRatio = width/height   //when on real phone replace width and height
        radius /= aspectRatio


        this.pointsArray[0] = this.centerX
        this.pointsArray[1] = this.centerY

        var arrayIndex = 3
        val outerPoints = this.points - 1

        for (i in 0 until outerPoints) {
            val degrees = i/(outerPoints - 1).toFloat()
            val radians = degrees * 2 * Math.PI.toFloat()

            this.pointsArray[arrayIndex++] = (centerX + radius* cos(radians)) * aspectRatio
            this.pointsArray[arrayIndex++] = centerY + radius* sin(radians)
            arrayIndex++
        }

    }

    override fun onDrawFrame(gl: GL10?) {
        val pointsBuffer = ByteBuffer.allocateDirect(points * 3 * 4)
        pointsBuffer.order(ByteOrder.nativeOrder())
        val pointsBufferFloat = pointsBuffer.asFloatBuffer()
        pointsBufferFloat.put(this.pointsArray)
        pointsBufferFloat.position(0)
        gl?.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        gl?.glColor4f(0.0f, 0.0f, 1.0f, 1.0f)
        gl?.glLoadIdentity()



        distance += direction * 0.03f

        if (distance > 0.9f || distance < -0.9f) {
            direction *= -1
        }
        gl?.glTranslatef(distance, 0.0f, 0.0f)
        gl?.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl?.glVertexPointer(3, GL10.GL_FLOAT, 0, pointsBufferFloat)
        gl?.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, points)
        gl?.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        gl?.glViewport(0, 0, width, height)
        gl?.glMatrixMode(GL10.GL_PROJECTION)
        gl?.glLoadIdentity()
        GLU.gluPerspective(gl, 45.0f, 0.5f, 0.0f, -1.0f)
        gl?.glClearColor(0.0f, 1.0f, 0.0f, 1.0f)
    }

    override fun onSurfaceCreated(gl: GL10?, config: javax.microedition.khronos.egl.EGLConfig?) {
    }
}