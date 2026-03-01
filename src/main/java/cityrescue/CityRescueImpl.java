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
    private in nextIncidentId;

    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        if (width <= 0 || height <= 0) {
        throw new InvalidGridException("Invalid Grid");
        }
        map = new CityMap(width,height);
        tick = 0;

        stations = 0;
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
            throw new InvalidCapacityException("Invlaid capacity"):
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
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
