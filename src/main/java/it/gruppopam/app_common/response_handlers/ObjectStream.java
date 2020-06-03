package it.gruppopam.app_common.response_handlers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ObjectStream<T> implements Closeable, Iterable<T>, Iterator<T> {

    private final JsonParser parser;
    private ObjectMapper mapper;
    private JsonToken token;
    private Class<T> clazz;

    public ObjectStream(InputStream inputStream, ObjectMapper mapper, Class<T> clazz) throws IOException {
        this.mapper = mapper;
        this.clazz = clazz;
        parser = mapper.getFactory().createParser(inputStream);
        token = parser.nextToken();
        if (token == null || !JsonToken.START_ARRAY.equals(token)) {
            bomb(null);
        }
    }

    @Override
    public void close() throws IOException {
        parser.close();
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        try {
            token = parser.nextToken();
        } catch (IOException e) {
            bomb(e);
        }
        return token != null && JsonToken.START_OBJECT.equals(token);
    }

    @Override
    public T next() {
        try {
            ObjectNode treeNode = mapper.readTree(parser);
            return mapper.treeToValue(treeNode, clazz);
        } catch (IOException e) {
            bomb(e);
            return null;
        }
    }

    @Override
    public void remove() {

    }

    private boolean bomb(IOException e) {
        throw new RuntimeException("Invalid json response. Expecting start of json array/object but got " + token, e);
    }
}
