import org.junit.jupiter.api.Test;
import cityrescue.CityRescue;
import cityrescue.CityRescueImpl;
import cityrescue.enums.UnitType;
import cityrescue.enums.IncidentType;

public class DemoTest {

    @Test
    void demonstration() {
        CityRescue rescue = new CityRescueImpl();

        // Add station
        rescue.addStation("S1", 0, 0);

        // Add units
        rescue.addUnit("S1", "U1", UnitType.AMBULANCE);
        rescue.addUnit("S1", "U2", UnitType.AMBULANCE);

        // Add incident
        rescue.addIncident("I1", IncidentType.MEDICAL, 3, 0);

        // Dispatch
        rescue.dispatch("I1");

        // Show state
        System.out.println(rescue.getUnitStatus("U1"));
        System.out.println(rescue.getIncidentStatus("I1"));

        // Tick several times
        rescue.tick();
        rescue.tick();
        rescue.tick();

        // Show updated state
        System.out.println(rescue.getUnitStatus("U1"));
        System.out.println(rescue.getIncidentStatus("I1"));
    }
}