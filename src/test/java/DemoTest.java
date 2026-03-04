import org.junit.jupiter.api.Test;
import cityrescue.CityRescue;
import cityrescue.CityRescueImpl;
import cityrescue.enums.UnitType;
import cityrescue.enums.IncidentType;

public class DemoTest {

    @Test
    void demonstration() throws Exception {
        CityRescue rescue = new CityRescueImpl();

        // Initialise grid
        rescue.initialise(10, 10);

        // Add station
        int stationId = rescue.addStation("S1", 0, 0);

        // Add units
        int unitId1 = rescue.addUnit(stationId, UnitType.AMBULANCE);
        int unitId2 = rescue.addUnit(stationId, UnitType.AMBULANCE);

        // Add incident
        int incidentId = rescue.reportIncident(IncidentType.MEDICAL, 3, 3, 0);

        // Dispatch
        rescue.dispatch();

        // Show state
        System.out.println(rescue.viewUnit(unitId1));
        System.out.println(rescue.viewIncident(incidentId));

        // Tick several times
        rescue.tick();
        rescue.tick();
        rescue.tick();

        // Show updated state
        System.out.println(rescue.viewUnit(unitId1));
        System.out.println(rescue.viewIncident(incidentId));
    }
}