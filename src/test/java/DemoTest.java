import org.junit.jupiter.api.Test;
import cityrescue.CityRescue;
import cityrescue.CityRescueImpl;
import cityrescue.enums.UnitType;
import cityrescue.enums.IncidentType;

public class DemoTest {

    @Test
    void demonstration() throws Exception {
        CityRescue rescue = new CityRescueImpl();
        rescue.initialise(10, 10);

        // Setup
        int stationA = rescue.addStation("Alpha Base", 0, 0);
        int stationB = rescue.addStation("Beta Base", 8, 8);
        int amb1 = rescue.addUnit(stationA, UnitType.AMBULANCE);
        int amb2 = rescue.addUnit(stationA, UnitType.AMBULANCE);
        int fire = rescue.addUnit(stationB, UnitType.FIRE_ENGINE);

        System.out.println("Two stations added, three units created and sitting idle.");
        System.out.println(rescue.viewUnit(amb1));
        System.out.println(rescue.viewUnit(amb2));
        System.out.println(rescue.viewUnit(fire));

        // Dispatch
        int inc1 = rescue.reportIncident(IncidentType.MEDICAL, 3, 3, 0);
        int inc2 = rescue.reportIncident(IncidentType.MEDICAL, 2, 1, 0);
        rescue.dispatch();

        System.out.println("\nTwo incidents reported. Both ambulances are equidistant from inc1 so the lowest ID wins.");
        System.out.println(rescue.viewUnit(amb1));
        System.out.println(rescue.viewUnit(amb2));
        System.out.println(rescue.viewIncident(inc1));
        System.out.println(rescue.viewIncident(inc2));

        // Tick movement
        System.out.println("\nUnits move one step per tick towards their incident.");
        for (int i = 1; i <= 3; i++) {
            rescue.tick();
            System.out.println("tick " + i + " " + rescue.viewUnit(amb1));
        }

        // Resolution
        System.out.println("\nKeep ticking until the incident is resolved and the unit goes back to idle.");
        for (int i = 4; i <= 15; i++) {
            rescue.tick();
            System.out.println("tick " + i + " " + rescue.viewUnit(amb1) + " " + rescue.viewIncident(inc1));
            if (rescue.viewIncident(inc1).contains("RESOLVED")) break;
        }

        // Exceptions
        System.out.println("\nNow showing what happens when invalid inputs are given.");

        try {
            rescue.reportIncident(IncidentType.MEDICAL, 6, 2, 2);
        } catch (Exception e) {
            System.out.println("Severity 6 is out of range, got " + e.getClass().getSimpleName());
        }

        try {
            rescue.addStation("Bad", 99, 99);
        } catch (Exception e) {
            System.out.println("Location 99,99 is outside the grid, got " + e.getClass().getSimpleName());
        }

        try {
            rescue.viewUnit(999);
        } catch (Exception e) {
            System.out.println("Unit 999 does not exist, got " + e.getClass().getSimpleName());
        }

        try {
            rescue.decommissionUnit(amb2);
        } catch (Exception e) {
            System.out.println("Cannot decommission a unit that is still busy, got " + e.getClass().getSimpleName());
        }

        // Final state
        System.out.println("\nFull system state at the end.");
        System.out.println(rescue.getStatus());
    }
}