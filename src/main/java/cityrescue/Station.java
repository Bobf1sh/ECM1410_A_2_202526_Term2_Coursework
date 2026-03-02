package cityrescue;

public class Station { 

  private int id;

  private int x;

  private int y;

  private String name; 

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
  public int getUnitCount() {
    return unitCount;
  }
  public void incrementUnit() {
    if(unitCount < capacity) unitCount++;
  }
  public void decrementUnit() {
    if(unitCount > 0) unitCount--;
  }

  
public void setCapacity(int newCapacity) {
  if (newCapacity < unitCount) {
    throw new IllegalArgumentException("The new capacity cannot be less than total number of units");
  }
  int[] newArray = new int[newCapacity];
  for (int i = 0; i < unitCount; i++) {
    newArray[i] = unitIds[i];
  }
  unitIds = newArray;
  capacity = newCapacity;
}

public void removeUnit(int unitId) {
  int index = -1;

  for (int i = 0; i < unitCount; i++) {
    if (unitIds[i] == unitId) {
      index = i;
      break;
    }
  }
  if (index == -1) return; 
  for (int i = index; i < unitCount - 1; i++) {
    unitIds[i] = unitIds[i + 1];
  }

  unitCount --;
  }
}
  
