package org.ocmc.ioc.liturgical.schemas.models.db.internal;


import java.util.ArrayList;
import java.util.List;

import org.ocmc.ioc.liturgical.schemas.exceptions.BadIdException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

/**
 * Provides an entry whose value is a JsonObject.
 * If you want the value to just be a String, use LTKVString instead!
 * 
 * Notes:
 * 1. If valueSchemaId is set, it will add the schema name as the 
 *     first label in the list of labels.
 * 
 * @author mac002
 *
 */
public class LTKVJsonObject extends LTK {
	@Expose String _valueSchemaId = "";
	@Expose JsonObject value; 
	@Expose List<String> labels = new ArrayList<String>();
	
	/**
	 * Constructor
	 * The JsonObject value must include a property _id
	 * whose value is of the form library|topic|key
	 * @param value to be saved
	 * @throws BadIdException of ID not formatted correctly
	 */
	public LTKVJsonObject(JsonObject value) throws BadIdException {
		super(value.get("_id").getAsString());
		if (value.has("value")) {
			this.value = value.get("value").getAsJsonObject();
		} else {
			this.value = value;
		}
	}
	
	/**
	 * Constructor
	 * The JsonObject value must include a property _id
	 * whose value is of the form library|topic|key
	 * @param _id the id
	 * @param schema id
	 * @param value the value
	 * @throws BadIdException if ID not formatted properly
	 */
	public LTKVJsonObject(
			String _id
			, String schema
			, JsonObject value
			) throws BadIdException {
		super(_id);
		this._valueSchemaId = schema;
		this.value = value;
		addLabels();
	}
	
	/**
	 * Constructor
	 * @param library the library
	 * @param topic the topic
	 * @param key the key
	 * @param schema id the id
	 * @param value the value
	 * @throws BadIdException if ID not formatted properly
	 */
	public LTKVJsonObject(
			String library
			, String topic
			, String key
			, String schema
			, JsonObject value
			) 
		throws BadIdException {
		super(library,topic, key);
		this._valueSchemaId = schema;
		this.value =  value;
		addLabels();
	}
	
	/**
	 * Adds a set of labels to the outer part of the LTKVJsonObject.
	 * If the Object uses a schema, the schema name becomes
	 * the first label of the LTKVJsonObject.
	 * 
	 * If the value (which is itself a JsonObject) also has labels,
	 * these will be externalized.  That is, they will be added to the
	 * outer set of labels as well.  The purpose of externalizing labels
	 * is to provide a database field that can explicitly be searched.
	 * 
	 * TODO: why would the value labels sometimes be a delimited string
	 * and other times be a JsonArray?  There is an inconsitency here
	 * that needs to be solved lest it bite us later.
	 */
	private void addLabels() {
		addLabel(getSchemaAsLabel());
		if (this.value.has("labels")) {
			if (value.get("labels").isJsonArray()) {
				JsonArray labelsArray = value.get("labels").getAsJsonArray();
				for (JsonElement e : labelsArray) {
					addLabel(e.getAsString());
				}
			} else {
				String labelString = value.get("labels").getAsString();
				String [] labels = labelString.split(":");
				for (String label : labels) {
					addLabel(label);
				}
			}
		}
	}

	public String get_valueSchemaId() {
		return _valueSchemaId;
	}

	public void set_valueSchemaId(String _valueSchemaId) {
		this._valueSchemaId = _valueSchemaId;
		addLabel(getSchemaAsLabel());
	}

	public JsonObject getValue() {
		return value;
	}

	public void setValue(JsonObject value) {
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	public Object getInstance(@SuppressWarnings("rawtypes") Class c) {
		String json = null;
		if (this.getValue().has("value")) {
			json = this.getValue().get("value").toString();
		} else {
			json = this.getValue().toString();
		}
		return gson.fromJson(json, c);
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	
	public void addLabel(String label) {
		if (! this.labels.contains(label)) {
			this.labels.add(label);
		}
	}

	public String getSchemaAsLabel() {
		String result = this._valueSchemaId;
		try {
			String [] parts = this._valueSchemaId.split(":");
			if (parts.length > 1) {
				result = parts[0];
			}
		} catch (Exception e) {
			// ignore
		}
		return result;
	}
	
	/**
	 * Returns the labels as a delimited path.
	 * For Neo4j, the delimiter should be a colon :
	 * @param delimiter to separate labels
	 * @return the labels delimited
	 */
	public String getDelimitedLabels(String delimiter) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i < labels.size()-1; i++) {
			sb.append(labels.get(i));
			sb.append(delimiter);
		}
		sb.append(labels.get(labels.size()-1));
		sb.append(this.getSchemaAsLabel());
		return sb.toString();
	}

}
