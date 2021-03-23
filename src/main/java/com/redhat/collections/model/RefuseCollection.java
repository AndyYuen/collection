package com.redhat.collections.model;



public class RefuseCollection {
		    
    private Integer id;
    private String date;
    private Integer vehicle;
    private WasteTypeEnum wasteType;
    private String suburb;
    private Integer count;
    private Float weight;

    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getVehicle() {
		return vehicle;
	}
	public void setVehicle(Integer vehicle) {
		this.vehicle = vehicle;
	}
	public WasteTypeEnum getWasteType() {
		return wasteType;
	}
	public void setWasteType(WasteTypeEnum wasteType) {
		this.wasteType = wasteType;
	}
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class RefuseCollection {\n");
	    
	    sb.append("    id: ").append(toIndentedString(id)).append("\n");
	    sb.append("    date: ").append(toIndentedString(date)).append("\n");
	    sb.append("    vehicle: ").append(toIndentedString(vehicle)).append("\n");
	    sb.append("    wasteType: ").append(toIndentedString(wasteType)).append("\n");
	    sb.append("    suburb: ").append(toIndentedString(suburb)).append("\n");
	    sb.append("    count: ").append(toIndentedString(count)).append("\n");
	    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }    

	  /**
	   * Convert the given object to string with each line indented by 4 spaces
	   * (except the first line).
	   */
	  private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }
}
