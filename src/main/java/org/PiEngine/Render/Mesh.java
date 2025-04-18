package org.PiEngine.Render;

import static org.lwjgl.opengl.GL30.*;


public class Mesh
{
    private int vao;
    private int vbo;
    private int vertexCount;
    float[] vertices;

    public Mesh(float[] vertices)
    {
        this.vertices = vertices;
        vertexCount = vertices.length / 5; // 3 for position, 2 for UV

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        // Position attribute
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * Float.BYTES, 0);

        // UV attribute
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);

        // Unbind
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void updateVertices(float[] newVertices)
    {
        this.vertices = newVertices;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    public void render()
    {        
        glBindVertexArray(vao);
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
        glBindVertexArray(0);
    }

    public void dispose()
    {
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
    }
}
