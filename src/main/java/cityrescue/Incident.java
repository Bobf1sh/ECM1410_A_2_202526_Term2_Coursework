
package cityrescue;

import cityrescue.enums.*;

public class Incident {

  private int id;

  private IncidentType type;

  private int severity;

  private int x;

  private int y;

  private IncidentStatus status;

  private int assignedUnitId;

  public Incident(int id, IncidentType type, int severity, int x, int y) {

    this.id = id; 

    this.type = type;
    
    this.severity = severity;
    
    this.x = x;
    
    this.y = y;
    
    this.status = IncidentStatus.REPORTED;

    this.assignedUnitId = -1;
  }

  public int getId() {
    return id;
  }

  public IncidentType getType() {
    return type;
  }
  
  public int getSeverity() {
    return severity;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public IncidentStatus getStatus() {
    return status;
  }
  
  public int getAssignedUnitId(int id) {
    return assignedUnitId = id;
  }

  public void setSeverity(int s) {
    this.severity = s;
  }

  public void setStatus(IncidentStatus s) {
    this.status = s;
  }

  public void setAssignedUnitId(int id) {
    this.assignedUnitId = id;
  }

  public void markAssigned() {
    if (assignedUnitId != -1) {
      System.out.printIn("Incident assigned to the unit:" + assignedUnitId);
    }
  }

}