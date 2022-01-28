package fr.unice.polytech.si3.qgl.royal_fortune.json_management;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class Action  {
	private int sailorId;
	private String type;
	
	public Action(int sailorId, String type) {
		this.sailorId = sailorId;
		this.type = type;
	}
	
	public int getSailorId() {
		return sailorId;
	}
	public String getType() {
		return type;
	}
}
