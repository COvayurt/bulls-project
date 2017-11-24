package undertow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.undertow.connector.PooledByteBuffer;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormParserFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;

public class BaseRegisterHandler {


    protected JsonNode parseRequestBodyJson(HttpServerExchange httpServerExchange) throws IOException {
        PooledByteBuffer pooledByteBuffer = httpServerExchange.getConnection().getByteBufferPool().allocate();
        ByteBuffer byteBuffer = pooledByteBuffer.getBuffer();


        httpServerExchange.getRequestChannel().read(byteBuffer);
        int pos = byteBuffer.position();
        byteBuffer.rewind();
        byte[] bytes = new byte[pos];
        byteBuffer.get(bytes);

        String requestBody = new String(bytes, Charset.forName("UTF-8"));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(requestBody);


        byteBuffer.clear();

        return actualObj;
    }

    protected ObjectNode parseRequestBodyFormData(HttpServerExchange httpServerExchange) throws IOException {
        FormParserFactory factory = FormParserFactory.builder().build();
        FormDataParser parser = factory.createParser(httpServerExchange);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode1 = mapper.createObjectNode();

        if (parser != null) {
            httpServerExchange.startBlocking();
            FormData formData = parser.parseBlocking();

            for (Iterator<String> it = formData.iterator(); it.hasNext(); ) {
                String s = it.next();
                objectNode1.put(s, formData.get(s).element().getValue());
            }

        }

        return objectNode1;
    }
}
