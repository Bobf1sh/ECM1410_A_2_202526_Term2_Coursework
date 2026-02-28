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
  
