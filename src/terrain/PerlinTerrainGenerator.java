package terrain;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.joml.Vector3f;

public class PerlinTerrainGenerator implements ITerrainGenerator {
	
    float[][] heights = new float[50][50];

    float[] positions = new float[(heights.length * heights[0].length)*3];
	
    ArrayList<Integer> indices = new ArrayList<Integer>();;
    int[] indicesArr;
    
    
	public Terrain generateTerrain() {

		
		PerlinNoise perlinNoise= new PerlinNoise(3, 10, 0.35f); 
	    heights = new float[50][50];
		for (int z = 0; z < heights.length; z++) {
			for (int x = 0; x < heights[z].length; x++) {
				heights[z][x] = perlinNoise.getPerlinNoise(x, z);
			}
		}
		
		Vector3f[][] normals = NormalGenerator.generateNormals(heights);
		
		int pointer = 0;
		//Generate vertices
		for(int z = 0; z < heights.length; z++) {
			for(int x = 0; x < heights[z].length; x++) {
				positions[pointer++] = (float) (x-25); //Offset of 12.5 so it's centered and it can be rotated properly for fancy display
				positions[pointer++] = (float) (heights[z][x]);
				positions[pointer++] = (float) (z-25);
			}
		}

		int[][] indicesMap = new int[heights.length][heights[0].length];
		
		pointer = 0;
		for(int z = 0; z < heights.length; z++) {
			for(int x = 0; x < heights[z].length; x++) {
				indicesMap[z][x] = pointer++;
			}
		} 
		
		pointer = 0;
		for(int z = 0; z < indicesMap.length; z++) {
			for(int x = 0; x < indicesMap[z].length; x++) {
				if(indicesMap.length-1 == z) {
					continue;
				}
				if(indicesMap[z].length-1 == x) {
					continue;
				}
				int topLeft = indicesMap[z][x];
				int topRight = indicesMap[z][x+1];
				int bottomLeft = indicesMap[z+1][x];
				int bottomRight = indicesMap[z+1][x+1];
				
				indices.add(topLeft);
				indices.add(bottomLeft);
				indices.add(topRight);
				indices.add(topRight);
				indices.add(bottomLeft);
				indices.add(bottomRight);
			}
		}
		
		indicesArr = new int[indices.size()];
		
	    for (int i=0; i < indicesArr.length; i++) {
	    	indicesArr[i] = indices.get(i);
	    }
		
		return new Terrain(positions, indicesArr, normals);
	}
}
