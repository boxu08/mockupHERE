package DataParsing;

import COMSETsystem.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.io.Serializable;

public class MapWithData implements Serializable {
    
    private CityMap map;
    private String dataPath;
    private PriorityQueue<Main.Event> events;

    public MapWithData (CityMap map, String dataPath) {
        this.map = map;
        this.dataPath = dataPath;
        events = new PriorityQueue<>();
    }
    
    public void createMapWithData(Main main) {
        
        CSVParser parser = new CSVParser(dataPath);
        ArrayList<TimestampAgRe> eventsParsed = parser.parse();

        try {
            for (TimestampAgRe event : eventsParsed) {
                Intersection i = map.getNearestIntersection(event.getLon(), event.getLat());
                if (event.getType().equals("agent")) {
                    Main.AgentEvent ev = main.new AgentEvent(i, event.getTime());
                    events.add(ev);
                } else {
                    Main.ResourceEvent ev = main.new ResourceEvent(i, event.getTime());
                    events.add(ev);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public CityMap getMap() {
        return map;
    }
    
    public PriorityQueue<Main.Event> getEvents() {
        return events;
    }
}
