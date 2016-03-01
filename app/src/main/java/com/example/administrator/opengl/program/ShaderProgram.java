package com.example.administrator.opengl.program;

import android.content.Context;

import com.example.administrator.opengl.util.ShaderHelper;
import com.example.administrator.opengl.util.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;

/**
 * Created by Administrator on 2016/3/1.
 */
public class ShaderProgram {
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";// Attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";// Shader program
    protected static final String U_COLOR = "u_Color";
    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {// Compile the shaders and link the program.
        program = ShaderHelper.buildProgram(TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId), TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId));
    }

    public void useProgram() {// Set the current OpenGL shader program to this program.
        glUseProgram(program);
    }
}
