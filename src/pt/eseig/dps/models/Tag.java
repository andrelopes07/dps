package pt.eseig.dps.models;

public class Tag {

	private String id;
	private String name;
	private String deviceName;
	private String deviceType;
	private String state;

    public Tag(String id, String name, String deviceName, String deviceType, String state) {
        this.id = id;
        this.name = name;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
