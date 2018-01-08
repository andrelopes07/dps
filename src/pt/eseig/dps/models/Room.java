package pt.eseig.dps.models;

public class Room {

	private String name;
    private String building;

    public Room(String name, String building) {
        this.name = name;
        this.building = building;
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

}
