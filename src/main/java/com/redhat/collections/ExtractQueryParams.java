package com.redhat.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.camel.Exchange;
import org.json.JSONArray;
import org.json.JSONObject;

import com.redhat.collections.model.RefuseCollection;
import com.redhat.collections.model.RefuseCollectionList;
import com.redhat.collections.model.WasteTypeEnum;


public class ExtractQueryParams {


	// Get the REST Service Query Parameters
    public QueryParams extract(Exchange exchange) throws Exception {
    	QueryParams params = new QueryParams();
    	params.setDate(exchange.getIn().getHeader("date", String.class));
    	params.setSuburb(exchange.getIn().getHeader("suburb", String.class));
    	params.setLimit(exchange.getIn().getHeader("limit", Integer.class));
        
        System.out.println("QueryParams:\n" + params);
        
        return params;

    } 
  
    // transform query params to that required by external collect service
    public void setCollectParams(Exchange exchange) throws Exception  {
    	QueryParams query = extract(exchange);
    	
        exchange.getIn().setHeader("QueryParams", query);
        
    	StringBuilder buf = new StringBuilder();
    			//resource_id=f815755d-3fbb-4e03-bdbd-4184f0e0141d&filters={"Suburb":"EUREKA", "Date": "2016-06-30"}
    	buf.append("resource_id=f815755d-3fbb-4e03-bdbd-4184f0e0141d");

    	if ((query.getDate() == null) || (query.getDate().equals(""))) {
    		if ((query.getSuburb() != null) && (! query.getSuburb().equals(""))) {
    			buf.append(String.format("&filters={\"Suburb\":\"%s\"}", query.getSuburb()));
    		}
    		
    	} else if ((query.getSuburb() == null) || (query.getSuburb().equals(""))) {
    		if ((query.getDate() != null) && (! query.getDate().equals(""))) {
    			buf.append(String.format("&filters={\"Date\":\"%s\"}", query.getDate()));
    		}
    	} else {
    		buf.append(String.format("&filters={\"Date\":\"%s\", \"Suburb\":\"%s\"}", query.getDate(), query.getSuburb()));
    	}

    	
    	if ((query.getLimit() != null) && (query.getLimit() != 0)) {
    		buf.append("&limit="); buf.append(query.getLimit()); 
    	}
    			

        exchange.getIn().setHeader(Exchange.HTTP_QUERY, buf.toString());

    }
    
    // transform query params to that required by external landfill service
    public void setLandfillParamsBasedOnCollectResult(Exchange exchange) throws Exception  {

//    	System.out.println("Entered setLandfillParamsBasedOnCollectResult");
    	// create partial results
    	String text = exchange.getIn().getBody(String.class);
//    	System.out.println("Body: " + text);
    	JSONObject rObj = new JSONObject(text);
//    	System.out.println("Imported JSONObject from body: " + rObj.toString());
//    	System.out.println("Success: " + rObj.getBoolean("success"));
    	RefuseCollectionList list = new RefuseCollectionList();
    	
    	JSONObject result = rObj.getJSONObject("result");
//    	System.out.println("Imported JSONObject from rootObject: " + result.toString()); 
    	


    	JSONArray jArray = result.getJSONArray("records");
//    	System.out.println("Imported JSONArray from records");

    	StringBuilder tmpBuf = new StringBuilder();
    	Map<Integer, String> map = new HashMap<Integer, String>();
    	
    	for (int i = 0; i < jArray.length(); i++) {  
		     JSONObject obj = jArray.getJSONObject(i);
		     RefuseCollection col = new RefuseCollection();
		     
		     col.setId(obj.getInt("_id"));
		     col.setDate(obj.getString("Date"));
		     col.setVehicle(Integer.parseInt(obj.getString("Vehicle")));
		     col.setWasteType(WasteTypeEnum.fromValue(obj.getString("Waste Type")));
		     col.setSuburb(obj.getString("Suburb"));
		     col.setCount(obj.getInt("Count"));
		     col.setWeight(new Float(0.0));
		     
		     if (!map.containsKey(col.getVehicle())) {
			     map.put(col.getVehicle(), "");

			     if (tmpBuf.length() > 0) tmpBuf.append(", ");
			     tmpBuf.append('"');
			     tmpBuf.append(col.getVehicle());
			     tmpBuf.append('"');
		     }		     
		     list.addRecordsItem(col);

		}
//    	System.out.println("Setting RefuseCollectionList in out.body.");
    	exchange.getIn().setHeader("partialResult", list);
    	
    	StringBuilder parmBuf = new StringBuilder();
    	parmBuf.append("resource_id=7d5aee9b-04c1-4222-8913-65238d94d5d4");
    	
    	QueryParams query = (QueryParams) exchange.getIn().getHeader("QueryParams");

    	if ((query.getDate() != null) && (! query.getDate().equals(""))) {
    		if (tmpBuf.length() == 0) {
    			parmBuf.append(String.format("&filters={\"date\":\"%s\"}", query.getDate()));
    		} else {
    			parmBuf.append(String.format("&filters={\"date\":\"%s\", \"vehicle\": [%s]}", query.getDate(), tmpBuf.toString()));
    		}
    	} else if (tmpBuf.length() != 0) {
    		parmBuf.append(String.format("&filters={\"vehicle\": [%s]}", tmpBuf.toString()));
    	}

    	/*
    	if ((query.getDate() != null) && (! query.getDate().equals(""))) {
    		parmBuf.append(String.format("&filters={\"date\":\"%s\"}", query.getDate()));
    	}
    	*/

/*    	
    	if ((query.getLimit() != null) && (query.getLimit() != 0)) {
    		buf.append("&limit="); buf.append(query.getLimit()); 
    	}
*/
    	System.out.println("tmpBuf: " + tmpBuf.toString());
    	System.out.println("Landfill QParms: " + parmBuf.toString());
        exchange.getIn().setHeader(Exchange.HTTP_QUERY, parmBuf.toString());
        System.out.println("setLandfillParamsBasedOnCollectResult completed."); 
    }
    
    // combine the data from the 2 services based on key "vehicle" to set the weight
    // note that the data is not 100% consistent
    public void enrichResult(Exchange exchange) {
//    	System.out.println("enrichResult reached.");

    	String text = exchange.getIn().getBody(String.class);
//    	System.out.println("Body: " + text);
    	JSONObject rObj = new JSONObject(text);
//    	System.out.println("Imported JSONObject from body: " + rObj.toString());
//    	System.out.println("Success: " + rObj.getBoolean("success"));

    	JSONObject result = rObj.getJSONObject("result");
//    	System.out.println("Imported JSONObject from rootObject: " + result.toString()); 
    	


    	JSONArray jArray = result.getJSONArray("records");
//    	System.out.println("Imported JSONArray from records");

    	//StringBuilder buf = new StringBuilder();
    	Map<Integer, Float> map =  new HashMap<Integer, Float>();
    	
    	for (int i = 0; i < jArray.length(); i++) {  
		     JSONObject obj = jArray.getJSONObject(i);

		     map.put(obj.getInt("vehicle"), obj.getFloat("weight"));

		}

    	RefuseCollectionList list = (RefuseCollectionList) exchange.getIn().getHeader("partialResult");
    	exchange.getIn().getHeaders().keySet().remove("partialResult");
    	Iterator<RefuseCollection> it = list.getRecords().iterator();
        while (it.hasNext()) {
        	RefuseCollection col = it.next();
        	
        	if (map.containsKey(col.getVehicle())) {
        		col.setWeight(map.get(col.getVehicle()));
        	}
        }
        
        // update the list
        //exchange.getOut().setBody(list, RefuseCollectionList.class);
        exchange.getIn().setBody(list, RefuseCollectionList.class);
        
        System.out.println("enrichResult completed.");
    	
    }

}
