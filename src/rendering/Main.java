package rendering;

import shaders.ShaderProgram;
import shaders.StaticShader;
import terrain.Terrain;
import terrain.ITerrainGenerator;
import terrain.NumberTerrainGenerator;
import terrain.PerlinTerrainGenerator;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Main {
	
	private final static int HEIGHT = 720;
	private final static int WIDTH = 1280;

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		ShaderProgram shader = new StaticShader();
		Renderer r = new Renderer(shader);
		r.prepare();
		ITerrainGenerator tg = new PerlinTerrainGenerator();
		Terrain t = tg.generateTerrain();
		
		float i = 0;
		
		while(!DisplayManager.shouldClose()) {
			r.render(t, i);
			DisplayManager.updateDisplay();
			if(i >= 360) {
				i = 0;
			}
			i += 0.2f;
		}
	}

}
