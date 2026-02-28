package cityrescue;

public class Station { 

  private int id;

  private int x;

  private int y;

  private int capacity;

  private int[] unitIds;

  private int unitCount;

  public Station(int id, String name, int x, int y) {

    this.id = id;

    this.name = name;

    this.x = x;

    this.y = y;

    this.capacity = 1;

    this.unitIds = new int[capacity];

    this.unitCount = 0;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getCapacity() {
    return capacity;
  }

  public int[] getUnitIds() {
    return unitIds;
  }

  
public void setCapacity(int newCapacity) {

  if (newCapacity < unitCount) {
    throw new IllegalArgumentException();
  }

  int[] newArray[i] = new int[newCapacity];

  for (int i = 0; i < unitCount; i++) {
    newArray[i] = unitIds[i];
  }

  unitIds = newArray;

  capacity = newCapacity;
}

  
  
