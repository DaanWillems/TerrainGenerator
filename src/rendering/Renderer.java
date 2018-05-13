package rendering;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


import org.joml.Matrix4f;
import org.joml.Vector3f;

import shaders.ShaderProgram;
import terrain.Terrain;

public class Renderer {
	
	private ShaderProgram shader;
	
	private static final float FOV = (float) Math.toRadians(60.0f);
	private static final float Z_NEAR = 0.01f;
	private static final float Z_FAR = 1000.f;
	private Matrix4f projectionMatrix;

	private Transformation transformation;
	
	public Renderer(ShaderProgram _shader) {
		this.shader = _shader;
		
		transformation = new Transformation();
		
		try {
			shader.createUniform("projectionMatrix");
			shader.createUniform("worldMatrix");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void prepare() {
		glClearColor(0.3f, 0.3f, 0.3f, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//		glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
	}
	
	public void render(Terrain t, float test) {
		
		prepare();
		
		shader.start();
		
	    Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, DisplayManager.getWidth(), 
	    		DisplayManager.getHeight(), Z_NEAR, Z_FAR);
	    
	    Matrix4f worldMatrix =
	            transformation.getWorldMatrix(
	                new Vector3f(-0, -2, -25),
	                new Vector3f(10, test, 0),
	                1);
	    
		shader.setUniform("worldMatrix", worldMatrix);
		shader.setUniform("projectionMatrix", projectionMatrix);
		
	    // Bind to the VAO
	    glBindVertexArray(t.getVaoID());
	    glEnableVertexAttribArray(0);
	    glEnableVertexAttribArray(1);
	    glEnableVertexAttribArray(2);
	    // Draw the vertices
	    glDrawElements(GL_TRIANGLES, t.getVertexCount(), GL_UNSIGNED_INT, 0);
	    
	    // Restore state
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	    
	    shader.stop();
	}
}
