package rendering;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public class DisplayManager {
	
	public static long windowID;
	
	private static int WIDTH = 1280;
	private static int HEIGHT = 720;

	public static void createDisplay() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
		{
		    System.err.println("Error initializing GLFW");
		    System.exit(1);
		}
		
		glfwWindowHint(GLFW_SAMPLES, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		windowID = glfwCreateWindow(WIDTH, HEIGHT, "My GLFW Window", NULL, NULL);

		if (windowID == NULL)
		{
		    System.err.println("Error creating a window");
		    System.exit(1);
		}
		
		glfwMakeContextCurrent(windowID);
		glfwSwapInterval(1);
		GL.createCapabilities();
		glfwShowWindow(windowID);
		glEnable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
	}
	
	public static void updateDisplay() {
		 glfwPollEvents();
		 glfwSwapBuffers(windowID);
	}
	
	public static void closeDisplay() {
		glfwDestroyWindow(windowID);
	}
	
	public static boolean shouldClose() {
		return glfwWindowShouldClose(windowID);
	}
	
	public static int getWidth() {
		return WIDTH;
	}
	
	public static int getHeight() {
		return HEIGHT;
	}
	
}
