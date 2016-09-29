package com.github.perujug.samples.jsonp;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPointer;
import javax.json.JsonValue;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

/**
 * Probar el uso de JsonPointer para acceder y modificar elementos dentro de un objeto JSON
 */
public class JsonPointerSampleTest {

    private final String json = "{\n" +
            "  \"planets\": [\n" +
            "    {\n" +
            "      \"name\": \"Earth\",\n" +
            "      \"technologicallyAdvancedSpecies\": \"Homo sapien\",\n" +
            "      \"hasBuiltSpaceships\": true,\n" +
            "      \"moons\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Kerbin\",\n" +
            "      \"technologicallyAdvancedSpecies\": \"Kerbal\",\n" +
            "      \"hasBuiltSpaceships\": true,\n" +
            "      \"moons\": 2\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    private final String planet = "{\n" +
            "      \"name\": \"Mars\",\n" +
            "      \"technologicallyAdvancedSpecies\": \"None\",\n" +
            "      \"hasBuiltSpaceships\": true,\n" +
            "      \"moons\": 2\n" +
            "    }";
    final private JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
    final private JsonObject planetJsonObject = Json.createReader(new StringReader(planet)).readObject();

    /**
     * Probar que se accede al primer objeto/array del objeto JSON utilizando JsonPointer
     */
    @Test
    public void usingJsonPointerToGetObject() {
        final JsonPointer pointer = new JsonPointer("/planets");
        final JsonValue jsonValue = pointer.getValue(jsonObject);
        assertEquals(jsonValue, jsonObject.get("planets"));
    }

    /**
     * Probar que se accede a un valor del objeto JSON utilizando la operacion getValue(String jsonPointer)
     */
    @Test
    public void usingGetJsonValueToGetObject() {
        final JsonValue jsonValue = jsonObject.getValue("/planets/1/name");
        assertEquals(jsonValue.toString(), "\"Kerbin\"");
    }

    /**
     * Probar que se acceder a elemento root utilizando el jsonPointer ""
     */
    @Test
    public void shouldGetRootElement() {
        final JsonPointer pointer = new JsonPointer("");
        final JsonValue jsonValue = pointer.getValue(jsonObject);
        assertEquals(jsonValue, jsonObject);
    }

    /**
     * Probar que se puede agregar un nuevo objeto al final de un Json Array utilizando "-"
     */
    @Test
    public void shouldAddPlanet() {
        final JsonPointer pointer = new JsonPointer("/planets/-");
        final JsonObject planets = pointer.add(jsonObject, planetJsonObject);
        System.out.printf("Planets: %s", planets.toString());
        assertEquals(planets.getJsonArray("planets").size(), 3);
    }

    /**
     * Probar que se puede reemplazar un objeto dentro de un Json Array utilizando "-"
     */
    @Test
    public void shouldReplacePlanet() {
        final JsonPointer pointer = new JsonPointer("/planets/1");
        final JsonObject planets = pointer.replace(jsonObject, planetJsonObject);
        System.out.printf("Planets: %s", planets.toString());
        assertEquals(planets.getJsonArray("planets").size(), 2);
    }

    /**
     * Probar que se puede eliminar objeto dentro de un Json Array "-"
     */
    @Test
    public void shouldRemovePlanet() {
        final JsonPointer pointer = new JsonPointer("/planets/1");
        final JsonObject planets = pointer.remove(jsonObject);
        System.out.printf("Planets: %s", planets.toString());
        assertEquals(planets.getJsonArray("planets").size(), 1);
    }
}
