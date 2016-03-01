package com.example.administrator.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.example.administrator.opengl.objects.Mallet;
import com.example.administrator.opengl.objects.Puck;
import com.example.administrator.opengl.objects.Table;
import com.example.administrator.opengl.program.ColorShaderProgram;
import com.example.administrator.opengl.program.TextureShaderProgram;
import com.example.administrator.opengl.util.LoggerConfig;
import com.example.administrator.opengl.util.MatrixHelper;
import com.example.administrator.opengl.util.ShaderHelper;
import com.example.administrator.opengl.util.TextResourceReader;
import com.example.administrator.opengl.util.TextureHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glClear;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;


/**
 * Created by Administrator on 2016/2/29.
 */
public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    //    private static final int BYTES_PER_FLOAT = 4;
//    private static final int POSITION_COMPONENT_COUNT = 4;
//    private static final String U_COLOR = "u_Color";
//    private int uColorLocation;
//    private static final String A_POSITION = "a_Position";
//    private static final String A_COLOR = "a_Color";
//    private static final int COLOR_COMPONENT_COUNT = 3;
//    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
//    private int aColorLocation;
//    private int aPositionLocation;
//    private final FloatBuffer vertexData;
//    private final Context context;
//    private final String fragmentShaderSource;
//    private final String vertexShaderSource;
//    private int program;
//    private static final String U_MATRIX = "u_Matrix";
    private final Context context;
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private Table table;
    private Mallet mallet;
    private Puck puck;
    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;
    private int texture;
//    private int uMatrixLocation;

    public AirHockeyRenderer(Context context) {
        this.context = context;
//        float[] tableVerticesWithTriangles = {
//                // Triangle 1-0.5f, -0.5f,0.5f, 0.5f,-0.5f, 0.5f,
//                // Triangle 2-0.5f, -0.5f,0.5f, -0.5f,0.5f, 0.5f,
//                // Order of coordinates: X, Y, R, G, B
//                // TriangleFan
//                0f, 0f, 0f, 1.5f, 1f, 1f, 1f, -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f, 0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f, 0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f, -0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f, -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,
//                -0.5f, 0f, 0f, 1.5f, 1f, 0f, 0f, 0.5f, 0f, 0f, 1.5f, 1f, 0f, 0f,
//                // Mallets
//                0f, -0.4f, 0f, 1.25f, 0f, 0f, 1f, 0f, 0.4f, 0f, 1.75f, 1f, 0f, 0f};
//        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
//        vertexData.put(tableVerticesWithTriangles);
//        vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
//        fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);
        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface);
        table = new Table();
        mallet = new Mallet(0.08f, 0.15f, 32);
        puck = new Puck(0.06f, 0.02f, 32);
//        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
//        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
//        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
//        if (LoggerConfig.ON) {
//            ShaderHelper.validateProgram(program);
//        }
//        glUseProgram(program);
////        uColorLocation = glGetUniformLocation(program, U_COLOR);
//        aColorLocation = glGetAttribLocation(program, A_COLOR);
//        aPositionLocation = glGetAttribLocation(program, A_POSITION);
//        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
//
//        vertexData.position(0);
////        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
//        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
//        glEnableVertexAttribArray(aPositionLocation);
//        vertexData.position(POSITION_COMPONENT_COUNT);
//        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
//        glEnableVertexAttribArrayrtexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
//        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);
        setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);


//        setIdentityM(modelMatrix, 0);
//
//        translateM(modelMatrix, 0, 0f, 0f, -2.5f);
//
//        rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
//
//        final float[] temp = new float[16];
//
//        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
//
//        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
//        final float aspectRatio = width > height ?(float) width / (float) height :(float) height / (float) width;
//        if (width > height) {
//        // Landscape
//        orthoM(projectionMatrix,0,-aspectRatio,aspectRatio,-1f,1f, -1f, 1f);}
//        else {// Portrait or square
//        orthoM(projectionMatrix,0,-1f,1f,-aspectRatio,aspectRatio,-1f,1f);}
//Log.d("matrix",projectionMatrix[0]+","+projectionMatrix[1]+","+projectionMatrix[2]+","+projectionMatrix[3]+",\n"+projectionMatrix[4]+","+projectionMatrix[5]+","+projectionMatrix[6]+","+projectionMatrix[7]+",\n"+projectionMatrix[8]+","+projectionMatrix[9]+","+projectionMatrix[10]+","+projectionMatrix[11]+",\n"+projectionMatrix[12]+","+projectionMatrix[13]+","+projectionMatrix[14]+","+projectionMatrix[15]+","+"");
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        // Draw the table.
        positionTableInScene();
        textureProgram.useProgram();
        textureProgram.setUniforms(modelViewProjectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();
//        textureProgram.useProgram();
//        textureProgram.setUniforms(projectionMatrix, texture);
//        table.bindData(textureProgram);
//        table.draw();
        // Draw the mallets.
        positionObjectInScene(0f, mallet.height / 2f, -0.4f);
        colorProgram.useProgram();
        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        mallet.bindData(colorProgram);
        mallet.draw();
        positionObjectInScene(0f, mallet.height / 2f, 0.4f);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        // Note that we don't have to define the object data twice -- we just// draw the same mallet again but in a different position and with a// different color.
        mallet.draw();
        // Draw the puck.
        positionObjectInScene(0f, puck.height / 2f, 0f);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f);
        puck.bindData(colorProgram);
        puck.draw();
//
//        glClear(GL_COLOR_BUFFER_BIT);
//        glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);
////        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
//        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
////        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
//        glDrawArrays(GL_LINES, 6, 2);
////        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
//        glDrawArrays(GL_POINTS, 8, 1);// Draw the second mallet red.
////        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
//        glDrawArrays(GL_POINTS, 9, 1);

    }

    private void positionTableInScene() {// The table is defined in terms of X & Y coordinates, so we rotate it// 90 degrees to lie flat on the XZ plane.
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }

    private void positionObjectInScene(float x, float y, float z) {
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, x, y, z);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }

    public void handleTouchPress(float normalizedX, float normalizedY) {
    }

    public void handleTouchDrag(float normalizedX, float normalizedY) {
    }
}
