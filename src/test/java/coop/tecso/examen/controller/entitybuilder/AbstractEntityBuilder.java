package coop.tecso.examen.controller.entitybuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractEntityBuilder<T> {
    public final String buildAsString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(build());
    }

    public final String buildAsString(T object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public final String buildValidAsString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(buildValid());
    }

    public abstract T build();
    public abstract T buildValid();
}