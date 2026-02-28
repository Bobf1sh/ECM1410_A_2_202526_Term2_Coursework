package cityrescue;

import cityrescue.enums.*;

public class FireEngine extends Unit {

  
  public FireEngine(int id, int homeStationId, int x, int y) {

    super( id, UnitType.FIRE_ENGINE, homeStationId, x, y);
  }

   
  @Override
  public boolean canHandle(IncidentType type) {

  return type == IncidentType.FIRE;
  }
