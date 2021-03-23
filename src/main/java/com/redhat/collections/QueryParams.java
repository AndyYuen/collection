package com.redhat.collections;


public class QueryParams {
	

	private String date;
	private String suburb;
	private Integer limit;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class QueryParams {\n");
	
		sb.append("    date: ").append(date).append("\n");
		sb.append("    suburb: ").append(suburb).append("\n");
		sb.append("    limit: ").append(limit).append("\n");
		sb.append("}");
	    return sb.toString();
	  } 

}
