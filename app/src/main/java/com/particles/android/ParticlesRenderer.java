package com.particles.android;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;

import com.example.administrator.opengl.util.Geometry;
import com.example.administrator.opengl.util.Geometry.Point;
import com.example.administrator.opengl.util.MatrixHelper;
import com.particles.android.objects.ParticleShooter;
import com.particles.android.objects.ParticleSystem;
import com.particles.android.programs.ParticleShaderProgram;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

/**
 * Created by Administrator on 2016/3/17.
 */
public class ParticlesRenderer implements GLSurfaceView.Renderer {
    private final Context context;
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private ParticleShaderProgram particleProgram;
    private ParticleSystem particleSystem;
    private ParticleShooter redParticleShooter;
    private ParticleShooter greenParticleShooter;
    private ParticleShooter blueParticleShooter;
    private long globalStartTime;

    public ParticlesRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        particleProgram = new ParticleShaderProgram(context);
        particleSystem = new ParticleSystem(10000);
        globalStartTime = System.nanoTime();
        final Geometry.Vector particleDirection = new Geometry.Vector(0f, 0.5f, 0f);
        final float angleVarianceInDegrees = 5f;
        final float speedVariance = 1f;
        redParticleShooter = new ParticleShooter(new Point(-1f, 0f, 0f), particleDirection, Color
                .rgb(255, 50, 5), angleVarianceInDegrees, speedVariance);
        greenParticleShooter = new ParticleShooter(new Point(0f, 0f, 0f), particleDirection,
                Color.rgb(25, 255, 25), angleVarianceInDegrees, speedVariance);
        blueParticleShooter = new ParticleShooter(new Point(1f, 0f, 0f), particleDirection, Color
                .rgb(5, 50, 255), angleVarianceInDegrees, speedVariance);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);
        setIdentityM(viewMatrix, 0);
        translateM(viewMatrix, 0, 0f, -1.5f, -5f);
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        float currentTime = (System.nanoTime() - globalStartTime) / 1000000000f;
        redParticleShooter.addParticles(particleSystem, currentTime, 5);
        greenParticleShooter.addParticles(particleSystem, currentTime, 5);
        blueParticleShooter.addParticles(particleSystem, currentTime, 5);
        particleProgram.useProgram();
        particleProgram.setUniforms(viewProjectionMatrix, currentTime);
        particleSystem.bindData(particleProgram);
        particleSystem.draw();

    }
}
