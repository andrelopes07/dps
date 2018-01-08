package pt.eseig.dps.models;

public class Antenna {

	private String id;
	private String name;
	private String building;
    private String room;
    private String state;

    public Antenna(String id, String name, String building, String room, String state) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.room = room;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
