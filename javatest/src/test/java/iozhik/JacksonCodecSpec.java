package iozhik;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import iozhik.geometry.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JacksonCodecSpec extends TestCase {

    @Test
    public void testTheTest() {
        assert(true);
    }

    @Test
    public void testSimpleParse() throws Exception {
        String jsonS
                = "{"
                + "  \"type\" : \"circle\","
                + "  \"area\" : 1,"
                + "  \"radius\" : 2,"
                + "  \"x\" : 3,"
                + "  \"y\" : 4"
                + "}";

        Circle obj = new ObjectMapper().readValue(jsonS, Circle.class);

        Circle mfsObj = new Circle(1, 2, 3, 4);

        assertEquals(mfsObj, obj);
    }

    @Test
    public void testDiscriminatorParsing() throws Exception {
        String jsonS
                = "["
                + "{"
                + "  \"type\" : \"circle\","
                + "  \"radius\" : 0,"
                + "  \"area\" : 0,"
                + "  \"x\" : 0,"
                + "  \"y\" : 0"
                + "},"
                + "{"
                + "  \"type\" : \"square\","
                + "  \"width\" : 0,"
                + "  \"height\" : 0,"
                + "  \"area\" : 0,"
                + "  \"x\" : 0,"
                + "  \"y\" : 0"
                + "}"
                + "]";

        List<Figure> obj = new ObjectMapper().readValue(jsonS, new TypeReference<List<Figure>>(){});

        List<Figure> mfsObj = Arrays.asList(new Circle(0, 0, 0, 0), new Square(0, 0, 0, 0, 0));

        assertEquals(mfsObj, obj);
    }

    @Test
    public void testDiscriminatorParsingWithSubobject() throws Exception {
        String jsonS
                = ("[{"
                + "  'type' : 'triangle',"
                + "  'area' : 10,"
                + "  'a' : { 'x': 1, 'y': 0 },"
                + "  'b' : { 'x': 0, 'y': 0 },"
                + "  'c' : { 'x': 0, 'y': 0 }"
                + "}]").replace('\'', '"');

        List<Figure> obj = new ObjectMapper().readValue(jsonS, new TypeReference<List<Figure>>(){});

        List<Figure> mfsObj = Collections.singletonList(new Triangle(10, new Point(1, 0), new Point(0, 0), new Point(0, 0)));

        assertEquals(mfsObj, obj);
    }
}
