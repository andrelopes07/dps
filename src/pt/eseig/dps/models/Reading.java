package pt.eseig.dps.models;

public class Reading {
	private Long datetime;
	private String antennaId;
	private String tagId;

	public Reading(Long datetime, String antennaId, String tagId) {
		this.datetime = datetime;
		this.antennaId = antennaId;
		this.tagId = tagId;
	}

	public Long getDateTime() {
		return datetime;
	}

	public String getAntennaId() {
		return antennaId;
	}		

	public String getTagId() {
		return tagId;
	}

}
