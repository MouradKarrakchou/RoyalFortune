package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonManagerTool {
    static final Logger LOGGER = Logger.getLogger(JsonManager.class.getName());
    static String exception = "Exception";
    /**
     *Create a InitGameToolDAO with a the InitGame JSON
     * @param jsonSeaEntities a String formated as JSON
     * @return a InitGameDAO that describe the game
     */
    public static List<SeaEntities> readListSeaEntitiesJson(String jsonSeaEntities) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        try {
            return mapper.readValue(jsonSeaEntities, new TypeReference<List<SeaEntities>>(){});
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.INFO, exception);
        }
        return null;
    }

        public static String convertListToJson(List<SeaEntities> list){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return  mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
