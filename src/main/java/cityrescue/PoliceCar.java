package cityrescue;

import cityresuce.enums.*;
public class PoliceCar extends Unit {

  public PoliceCar(int id, int homeStationId, int x, int y) {

    
    super(id, UnitType.POLICE_CAR, homeStationId, x, y);
    
  }
  

  @Override
  public boolean canHandle(IncidentType type) {

    return type == IncidentType.CRIME;
  }

  @Override
  public int getTicksToResolve() {
    return 3;
  }
