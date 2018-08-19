package model.domain;

public class PlaceDTO {
	private String id;
	private String name;
	private String type;
	private String usage;
	private double lat;
	private double lng;
	private double distance;
	
	public PlaceDTO() {};
	public PlaceDTO(String id, String name, String type, String usage, double lat,double lng) {
		super();
		this.id=id;
		this.name=name;
		this.type=type;
		this.usage=usage;
		this.lat=lat;
		this.lng=lng;
	}
	public PlaceDTO(String id, String name, String type, String usage,double distance) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.usage = usage;
		this.distance=distance;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//builder.append("ID= ");
		//builder.append(id);
		builder.append("�̸�=");
		builder.append(name);
		builder.append("    �з�=");
		builder.append(usage);
		builder.append("    ������ �Ÿ�=");
		builder.append((int)distance);
		builder.append("M ");
		return builder.toString();
	}
	
	
	
	
}
