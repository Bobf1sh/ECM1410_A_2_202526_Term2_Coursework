
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
  
  
