package cityrescue;

import cityrescue.exceptions.InvalidLocationException;


public class CityMap {

    private int width;
    private int height;
    private boolean[][] blocked;

    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
        blocked = new boolean[width][height];

    }
    
    public in getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isInBounds(int x, int y)
{
    return x >= 0 && x < width && y >= 0 && y < height;
}



public boolean isBlocked(int x, int y) {
    return blocked[x][y];
}
public void addObstacle(int x, int y) throws InvalidLocationException {
    if (!isInBounds(x, y)) {
        throw new InvalidLocatoinException("Invalid Location"); 
    }
    blocked[x][y] = true;
}

public void removeObstacle(int x, int y) throws InvalidLocationException {
    if (!isInBounds(x, y)) {
        throw new InvalidLocationException("Invalid location");
    }
    blocked[x][y] = false;
}
public int getObstacleCount() {
    int count = 0;
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            if (blocked[i][j]) count++;
        }
    }
    return count;
}
}
