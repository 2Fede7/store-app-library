package it.gruppopam.app_common.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PlainISODateDeserializer extends StdDeserializer<Date> {

    public PlainISODateDeserializer() {
        this(null);
    }

    public PlainISODateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String isoDateFormat = "yyyy-MM-dd";
        JsonNode node = p.getCodec().readTree(p);
        String date = node.textValue();
        try {
            return new SimpleDateFormat(isoDateFormat, Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            throw new IOException(String.format("Un parsable date %s supported format %s", date, isoDateFormat));
        }
    }
}
