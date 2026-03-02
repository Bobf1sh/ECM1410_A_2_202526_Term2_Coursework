package cityrescue;

import cityrescue.enums.*;

public abstract class Unit {
  protected int id;

  protected UnitType type;

  protected int homeStationId;

  protected int x;

  protected int y;

  protected int assignedIncidentId;

  protected int workRemaining;

  protected UnitStatus status;

  public Unit(int id, UnitType type, int homeStationId, int x, int y) {
    this.id = id;

    this.type = type;
    
    this.homeStationId = homeStationId;

    this.x = x;

    this.y = y;

    this.status = UnitStatus.IDLE;

    this.assignedIncidentId = -1;

    this.workRemaining = 0;
  }
  public int getId() {
    return id;
  }

  public int getHomeStationId() {
    return homeStationId;
  }

  public int getY() {
    return y;
  }

  public int getX() {
    return x;
  }

  public UnitStatus getStatus() {
    return status;
  }

  public int getAssignedIncidentId() {
    return assignedIncidentId;
  }

  public int getWorkRemaining() {
    return workRemaining;
  }

  public void setLocation(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void setStatus(UnitStatus status) {
    this.status = status;
  }

  public void setAssignedIncidentId(int id) {
    this.assignedIncidentId = id;
  }

  public void decrementWork() {
    workRemaining--;
  }

  public abstract boolean canHandle(IncidentType type);

  public abstract int getTicksToResolve();
}
