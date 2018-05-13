package terrain;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class Terrain {
	private int vaoId;
	private int vboId;
	private int idxVboId;
	private int vertexCount;
	
	float[] positions;
	int[] indices;
	float[] normals;
	
	public Terrain(float[] positions, int[] indices, Vector3f[][] _normals) {
		int length = (_normals.length * _normals[0].length)*3;
		this.normals = new float[length];
		this.positions = positions;
		this.indices = indices;
		
		int pointer = 0;
		for(int i = 0; i < _normals.length; i++) {
			for(int x = 0; x < _normals[i].length; x++) {
				System.out.println(_normals[i][x].x);
				this.normals[pointer++] = _normals[i][x].x;
				this.normals[pointer++] = _normals[i][x].y;
				this.normals[pointer++] = _normals[i][x].z;
			}
		}
		createVAO();
	}
	
	private void createVAO() {
		System.out.println(this.normals.length);
		System.out.println(this.positions.length);

		FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
		verticesBuffer.put(positions).flip();
		
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		
		vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		idxVboId = glGenBuffers();
		IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
		indicesBuffer.put(indices).flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		
		// Vertex normals VBO
		int vboId = glGenBuffers();
		FloatBuffer vecNormalsBuffer = MemoryUtil.memAllocFloat(normals.length);
		vecNormalsBuffer.put(normals).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, vecNormalsBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		
		if (verticesBuffer != null) {
		    MemoryUtil.memFree(verticesBuffer);
		}
		
		if (indicesBuffer != null) {
		    MemoryUtil.memFree(indicesBuffer);
		}
	}
	
	public int getVaoID() {
		return vaoId;
	}

	public int getVertexCount() {
		return indices.length;
	}
}
