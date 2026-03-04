package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class CityRescueImpl implements CityRescue {

    private static final int MAX_STATIONS = 20;
    private static final int MAX_UNITS = 50;
    private static final int MAX_INCIDENTS = 200;

    private CityMap map;
    private int tick;

    private Station[] stations;
    private Unit[] units;
    private Incident[] incidents;

    private int stationCount;
    private int unitCount;
    private int incidentCount;

    private int nextStationId;
    private int nextUnitId;
    private int nextIncidentId;

    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException("Invalid Grid");
        }
        map = new CityMap(width, height);
        tick = 0;

        stations = new Station[MAX_STATIONS];
        units = new Unit[MAX_UNITS];
        incidents = new Incident[MAX_INCIDENTS];

        stationCount = 0;
        unitCount = 0;
        incidentCount = 0;

        nextStationId = 1;
        nextUnitId = 1;
        nextIncidentId = 1;
    }

    @Override
    public int[] getGridSize() {
        return new int[]{map.getWidth(), map.getHeight()};
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        map.addObstacle(x, y);
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        map.removeObstacle(x, y);
    }

    @Override
    public int addStation(String name, int x, int y)
            throws InvalidNameException, InvalidLocationException {

        if (name == null || name.isBlank()) {
            throw new InvalidNameException("Invalid name");
        }
        if (!map.isInBounds(x, y) || map.isBlocked(x, y)) {
            throw new InvalidLocationException("Invalid location");
        }
        if (stationCount >= MAX_STATIONS) {
            throw new CapacityExceededException("Max stations reached");
        }

        Station s = new Station(nextStationId, name, x, y);
        stations[stationCount++] = s;
        return nextStationId++;
    }

    @Override
    public void removeStation(int stationId)
            throws IDNotRecognisedException, IllegalStateException {

        int index = findStationIndex(stationId);
        if (stations[index].getUnitCount() > 0) {
            throw new IllegalStateException("Station not empty");
        }
        removeStationAt(index);
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits)
            throws IDNotRecognisedException, InvalidCapacityException {

        int index = findStationIndex(stationId);
        if (maxUnits <= 0) {
            throw new InvalidCapacityException("Invalid capacity");
        }
        if (maxUnits < stations[index].getUnitCount()) {
            throw new InvalidCapacityException("Too small");
        }
        stations[index].setCapacity(maxUnits);
    }

    @Override
    public int[] getStationIds() {
        int[] ids = new int[stationCount];
        for (int i = 0; i < stationCount; i++) {
            ids[i] = stations[i].getId();
        }
        return ids;
    }

    @Override
    public int addUnit(int stationId, UnitType type)
            throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {

        if (type == null) {
            throw new InvalidUnitException("Null type");
        }
        int sIndex = findStationIndex(stationId);
        Station s = stations[sIndex];

        if (s.getUnitCount() >= s.getCapacity()) {
            throw new IllegalStateException("No capacity");
        }
        if (unitCount >= MAX_UNITS) {
            throw new CapacityExceededException("Max units reached");
        }

        Unit u;
        int id = nextUnitId;

        if (type == UnitType.AMBULANCE)
            u = new Ambulance(id, stationId, s.getX(), s.getY());
        else if (type == UnitType.FIRE_ENGINE)
            u = new FireEngine(id, stationId, s.getX(), s.getY());
        else
            u = new PoliceCar(id, stationId, s.getX(), s.getY());

        units[unitCount++] = u;
        s.incrementUnit();

        return nextUnitId++;
    }

    @Override
    public void decommissionUnit(int unitId)
            throws IDNotRecognisedException, IllegalStateException {

        int index = findUnitIndex(unitId);
        Unit u = units[index];

        if (u.getStatus() == UnitStatus.EN_ROUTE ||
                u.getStatus() == UnitStatus.AT_SCENE) {
            throw new IllegalStateException("Unit busy");
        }

        stations[findStationIndex(u.getHomeStationId())].decrementUnit();
        removeUnitAt(index);
    }

    @Override
    public void transferUnit(int unitId, int newStationId)
            throws IDNotRecognisedException, IllegalStateException {

        int uIndex = findUnitIndex(unitId);
        int sIndex = findStationIndex(newStationId);

        Unit u = units[uIndex];
        Station newS = stations[sIndex];

        if (u.getStatus() != UnitStatus.IDLE) {
            throw new IllegalStateException("Unit not idle");
        }
        if (newS.getUnitCount() >= newS.getCapacity()) {
            throw new IllegalStateException("No space");
        }

        stations[findStationIndex(u.getHomeStationId())].decrementUnit();

        u.homeStationId = newStationId;
        u.setLocation(newS.getX(), newS.getY());

        newS.incrementUnit();
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService)
            throws IDNotRecognisedException, IllegalStateException {

        int index = findUnitIndex(unitId);
        Unit u = units[index];

        if (outOfService) {
            if (u.getStatus() != UnitStatus.IDLE) {
                throw new IllegalStateException("Not idle");
            }
            u.setStatus(UnitStatus.OUT_OF_SERVICE);
        } else {
            u.setStatus(UnitStatus.IDLE);
        }
    }

    @Override
    public int[] getUnitIds() {
        int[] ids = new int[unitCount];
        for (int i = 0; i < unitCount; i++) {
            ids[i] = units[i].getId();
        }
        return ids;
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        Unit u = units[findUnitIndex(unitId)];
        return formatUnit(u);
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y)
            throws InvalidSeverityException, InvalidLocationException {

        if (type == null || severity < 1 || severity > 5) {
            throw new InvalidSeverityException("Invalid severity");
        }
        if (!map.isInBounds(x, y) || map.isBlocked(x, y)) {
            throw new InvalidLocationException("Invalid location");
        }
        if (incidentCount >= MAX_INCIDENTS) {
            throw new CapacityExceededException("Max incidents reached");
        }

        Incident inc = new Incident(nextIncidentId, type, severity, x, y);
        incidents[incidentCount++] = inc;
        return nextIncidentId++;
    }

    @Override
    public void cancelIncident(int incidentId)
            throws IDNotRecognisedException, IllegalStateException {
        Incident inc = incidents[findIncidentIndex(incidentId)];

        if (inc.getStatus() == IncidentStatus.RESOLVED ||
                inc.getStatus() == IncidentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel");
        }

        if (inc.getStatus() == IncidentStatus.DISPATCHED) {
            Unit u = units[findUnitIndex(inc.getAssignedUnitId())];
            u.setStatus(UnitStatus.IDLE);
            u.setAssignedIncidentId(-1);
        }

        inc.setStatus(IncidentStatus.CANCELLED);
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity)
            throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {

        if (newSeverity < 1 || newSeverity > 5) {
            throw new InvalidSeverityException("Invalid severity");
        }

        Incident inc = incidents[findIncidentIndex(incidentId)];

        if (inc.getStatus() == IncidentStatus.RESOLVED ||
                inc.getStatus() == IncidentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot escalate");
        }

        inc.setSeverity(newSeverity);
    }

    @Override
    public int[] getIncidentIds() {
        int[] ids = new int[incidentCount];
        for (int i = 0; i < incidentCount; i++) {
            ids[i] = incidents[i].getId();
        }
        return ids;
    }

    @Override
    public String viewIncident(int incidentId)
            throws IDNotRecognisedException {
        Incident inc = incidents[findIncidentIndex(incidentId)];
        return formatIncident(inc);
    }

    @Override
    public void dispatch() {
        for (int i = 0; i < incidentCount; i++) {
            Incident inc = incidents[i];

            if (inc.getStatus() != IncidentStatus.REPORTED)
                continue;

            Unit best = null;
            int bestDist = Integer.MAX_VALUE;

            for (int j = 0; j < unitCount; j++) {
                Unit u = units[j];

                if (u.getStatus() != UnitStatus.IDLE)
                    continue;
                if (!u.canHandle(inc.getType()))
                    continue;

                int dist = manhattan(u.getX(), u.getY(),
                        inc.getX(), inc.getY());

                if (best == null ||
                        dist < bestDist ||
                        (dist == bestDist &&
                                u.getId() < best.getId())) {

                    best = u;
                    bestDist = dist;
                }
            }

            if (best != null) {
                inc.setStatus(IncidentStatus.DISPATCHED);
                inc.setAssignedUnitId(best.getId());
                best.setStatus(UnitStatus.EN_ROUTE);
                best.setAssignedIncidentId(inc.getId());
            }
        }
    }

    @Override
    public void tick() {

        tick++;

        for (int i = 0; i < unitCount; i++) {
            Unit u = units[i];

            if (u.getStatus() == UnitStatus.EN_ROUTE) {
                try {
                    Incident inc = incidents[findIncidentIndex(u.getAssignedIncidentId())];
                    moveUnit(u, inc.getX(), inc.getY());

                    if (u.getX() == inc.getX() &&
                            u.getY() == inc.getY()) {

                        u.setStatus(UnitStatus.AT_SCENE);
                        inc.setStatus(IncidentStatus.IN_PROGRESS);
                        u.workRemaining = u.getTicksToResolve();
                    }
                } catch (IDNotRecognisedException e) {
                    u.setStatus(UnitStatus.IDLE);
                    u.setAssignedIncidentId(-1);
                }
            }
        }

        for (int i = 0; i < unitCount; i++) {
            Unit u = units[i];

            if (u.getStatus() == UnitStatus.AT_SCENE) {
                u.decrementWork();

                if (u.getWorkRemaining() == 0) {
                    try {
                        Incident inc = incidents[findIncidentIndex(u.getAssignedIncidentId())];
                        inc.setStatus(IncidentStatus.RESOLVED);
                    } catch (IDNotRecognisedException e) {
                    }
                    u.setStatus(UnitStatus.IDLE);
                    u.setAssignedIncidentId(-1);
                }
            }
        }
    }

    @Override
    public String getStatus() {

        StringBuilder sb = new StringBuilder();

        sb.append("TICK=").append(tick).append("\n");
        sb.append("STATIONS=").append(stationCount)
                .append(" UNITS=").append(unitCount)
                .append(" INCIDENTS=").append(incidentCount)
                .append(" OBSTACLES=").append(map.getObstacleCount())
                .append("\n");

        sb.append("INCIDENTS\n");
        for (int i = 0; i < incidentCount; i++) {
            sb.append(formatIncident(incidents[i])).append("\n");
        }

        sb.append("UNITS\n");
        for (int i = 0; i < unitCount; i++) {
            sb.append(formatUnit(units[i])).append("\n");
        }

        return sb.toString().trim();
    }

    private int manhattan(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private void moveUnit(Unit u, int tx, int ty) {

        int[][] dirs = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

        for (int[] d : dirs) {
            int nx = u.getX() + d[0];
            int ny = u.getY() + d[1];

            if (!map.isInBounds(nx, ny) || map.isBlocked(nx, ny))
                continue;

            int current = manhattan(u.getX(), u.getY(), tx, ty);
            int next = manhattan(nx, ny, tx, ty);

            if (next < current) {
                u.setLocation(nx, ny);
                return;
            }
        }

        for (int[] d : dirs) {
            int nx = u.getX() + d[0];
            int ny = u.getY() + d[1];

            if (!map.isInBounds(nx, ny) || map.isBlocked(nx, ny))
                continue;

            u.setLocation(nx, ny);
            return;
        }
    }

    private String formatUnit(Unit u) {
        return "U#" + u.getId() +
                " TYPE=" + u.type +
                " HOME=" + u.getHomeStationId() +
                " LOC=(" + u.getX() + "," + u.getY() + ")" +
                " STATUS=" + u.getStatus() +
                " INCIDENT=" +
                (u.getAssignedIncidentId() == -1 ? "-" : u.getAssignedIncidentId()) +
                (u.getStatus() == UnitStatus.AT_SCENE ?
                        " WORK=" + u.getWorkRemaining() : "");
    }

    private String formatIncident(Incident i) {
        return "I#" + i.getId() +
                " TYPE=" + i.getType() +
                " SEV=" + i.getSeverity() +
                " LOC=(" + i.getX() + "," + i.getY() + ")" +
                " STATUS=" + i.getStatus() +
                " UNIT=" +
                (i.getAssignedUnitId() == -1 ? "-" : i.getAssignedUnitId());
    }

    private int findStationIndex(int id) throws IDNotRecognisedException {
        for (int i = 0; i < stationCount; i++)
            if (stations[i].getId() == id)
                return i;
        throw new IDNotRecognisedException("Not found");
    }

    private int findUnitIndex(int id) throws IDNotRecognisedException {
        for (int i = 0; i < unitCount; i++)
            if (units[i].getId() == id)
                return i;
        throw new IDNotRecognisedException("Not found");
    }

    private int findIncidentIndex(int id) throws IDNotRecognisedException {
        for (int i = 0; i < incidentCount; i++)
            if (incidents[i].getId() == id)
                return i;
        throw new IDNotRecognisedException("Not found");
    }

    private void removeStationAt(int index) {
        for (int i = index; i < stationCount - 1; i++)
            stations[i] = stations[i + 1];
        stations[--stationCount] = null;
    }

    private void removeUnitAt(int index) {
        for (int i = index; i < unitCount - 1; i++)
            units[i] = units[i + 1];
        units[--unitCount] = null;
    }
}