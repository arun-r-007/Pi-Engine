package org.PiEngine.Render;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL32.*;

import org.PiEngine.Math.*;

public class    Shader
{
    private int programId;

    public Shader(String vertexPath, String fragmentPath, String geometryPath)
    {
        try
        {
            String vertexSource = new String(Files.readAllBytes(Paths.get(vertexPath)), StandardCharsets.UTF_8);
            String fragmentSource = new String(Files.readAllBytes(Paths.get(fragmentPath)), StandardCharsets.UTF_8);

            

            int vertexId = compileShader(vertexSource, GL_VERTEX_SHADER);
            int fragmentId = compileShader(fragmentSource, GL_FRAGMENT_SHADER);

            programId = glCreateProgram();
            glAttachShader(programId, vertexId);
            glAttachShader(programId, fragmentId);

            int geometryId = -1;
            if (geometryPath != null && !geometryPath.isEmpty())
            {
                String geometrySource = new String(Files.readAllBytes(Paths.get(geometryPath)), StandardCharsets.UTF_8);
                geometryId = compileShader(geometrySource, GL_GEOMETRY_SHADER);
                glAttachShader(programId, geometryId);
            }

            glLinkProgram(programId);

            if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE)
            {
                String log = glGetProgramInfoLog(programId);
                throw new RuntimeException("Shader linking failed:\n" + log);
            }

            glDeleteShader(vertexId);
            glDeleteShader(fragmentId);
            if (geometryId != -1) glDeleteShader(geometryId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("Failed to load shader");
        }
    }


    private int compileShader(String source, int type)
    {
        int shaderId = glCreateShader(type);
        glShaderSource(shaderId, source);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE)
        {
            String log = glGetShaderInfoLog(shaderId);
            throw new RuntimeException("Shader compilation failed:\n" + log);

        }

        return shaderId;
    }

    public void use()
    {
        glUseProgram(programId);
    }

    public void stop()
    {
        glUseProgram(0);
    }

    public void delete()
    {
        glDeleteProgram(programId);
    }

    public int getId()
    {
        return programId;
    }

    // Example setter: you can add more as 
    public void setUniform1i(String name, int value)
    {
        int location = glGetUniformLocation(programId, name);
        glUniform1i(location, value);
    }

    public void setUniform1f(String name, float value)
    {
        int location = glGetUniformLocation(programId, name);
        glUniform1f(location, value);
    }

    // For matrix (JOML)
    public void setUniformMat4(String name, Matrix4 mat)
    {
        int location = glGetUniformLocation(programId, name);
        glUniformMatrix4fv(location, false, mat.toFloatBuffer());
    }

    public void setUniformVec2(String name, Vector vec2)
    {
        int location = glGetUniformLocation(programId, name);
        glUniform2f(location, vec2.x, vec2.y);
    }

    public void setUniformVec3(String name, Vector vec3)
    {
        int location = glGetUniformLocation(programId, name);
        glUniform3f(location, vec3.x, vec3.y, vec3.z);
    }

    public void setUniformVec4(String name, float x, float y, float z, float w)
    {
        int location = glGetUniformLocation(programId, name);
        glUniform4f(location, x, y, z, w);
    }

    public void setUniformBool(String name, boolean value)
    {
        int location = glGetUniformLocation(programId, name);
        glUniform1i(location, value ? 1 : 0);
    }


}