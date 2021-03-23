package com.redhat.collections.model;

// Data model to hold response for Bin Collection service
  public enum WasteTypeEnum {
	    LANDFILL("Landfill"),
	    RECYCLING("Recycling"),
	    GREEN_WASTE("Green Waste");

	    private String value;

	    WasteTypeEnum(String value) {
	      this.value = value;
	    }
	    public String getValue() {
	      return value;
	    }

	    @Override
	    public String toString() {
	      return String.valueOf(value);
	    }
	    public static WasteTypeEnum fromValue(String text) {
	      for (WasteTypeEnum b : WasteTypeEnum.values()) {
	        if (String.valueOf(b.value).equals(text)) {
	          return b;
	        }
	      }
	      return null;
	    }
  }
