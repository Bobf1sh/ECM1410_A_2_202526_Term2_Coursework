import cityrescue.*;
import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class DemoTest {

    public static void main(String[] args) throws Exception {
        CityRescue rescue = new CityRescueImpl();
        rescue.initialise(10, 10);

        System.out.println("Setting up two stations and three units...\n");
        int stationA = rescue.addStation("Alpha Base", 0, 0);
        int stationB = rescue.addStation("Beta Base", 8, 8);
        int amb1 = rescue.addUnit(stationA, UnitType.AMBULANCE);
        int amb2 = rescue.addUnit(stationA, UnitType.AMBULANCE);
        int fire = rescue.addUnit(stationB, UnitType.FIRE_ENGINE);

        System.out.println(rescue.viewUnit(amb1));
        System.out.println(rescue.viewUnit(amb2));
        System.out.println(rescue.viewUnit(fire));

        System.out.println("\nReporting two medical incidents and dispatching...\n");
        int inc1 = rescue.reportIncident(IncidentType.MEDICAL, 3, 3, 0);
        int inc2 = rescue.reportIncident(IncidentType.MEDICAL, 2, 1, 0);
        rescue.dispatch();

        System.out.println("Both ambulances are at (0,0). Tie broken by lowest ID - amb1 goes to inc1, amb2 to inc2.\n");
        System.out.println(rescue.viewUnit(amb1));
        System.out.println(rescue.viewUnit(amb2));
        System.out.println(rescue.viewIncident(inc1));
        System.out.println(rescue.viewIncident(inc2));

        System.out.println("\nTicking - watch amb1 move towards incident at (3,0)...\n");
        for (int i = 1; i <= 3; i++) {
            rescue.tick();
            System.out.println("tick " + i + "  " + rescue.viewUnit(amb1));
        }

        System.out.println("\nAmb1 is now AT_SCENE. Ticking until incident resolves...\n");
        for (int i = 4; i <= 15; i++) {
            rescue.tick();
            System.out.println("tick " + i + "  " + rescue.viewUnit(amb1) + "  " + rescue.viewIncident(inc1));
            if (rescue.viewIncident(inc1).contains("RESOLVED")) break;
        }

        System.out.println("\nNow demonstrating exception handling...\n");

        try {
            rescue.reportIncident(IncidentType.MEDICAL, 6, 2, 2);
        } catch (InvalidSeverityException e) {
            System.out.println("Severity 6 rejected: " + e.getClass().getSimpleName());
        }

        try {
            rescue.addStation("Bad", 99, 99);
        } catch (InvalidLocationException e) {
            System.out.println("Location (99,99) rejected: " + e.getClass().getSimpleName());
        }

        try {
            rescue.viewUnit(999);
        } catch (IDNotRecognisedException e) {
            System.out.println("Unit 999 not found: " + e.getClass().getSimpleName());
        }

        try {
            rescue.decommissionUnit(amb2);
        } catch (IllegalStateException e) {
            System.out.println("Cannot decommission busy unit: " + e.getClass().getSimpleName());
        }

        System.out.println("\nFinal system state:\n");
        System.out.println(rescue.getStatus());
    }
}