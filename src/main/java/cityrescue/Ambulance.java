
package cityrescue;

import cityrescue.enums.*;

public class Ambulance extends Unit {
  public Ambulance(int id, int homeStationId, int x, int y) {
    super(id.UnitType.AMBULANCE.homeStationId, x, y);
  }
