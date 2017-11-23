package undertow.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.undertow.connector.PooledByteBuffer;
import io.undertow.server.HttpServerExchange;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class BaseRegisterHandler {

    protected Gson gson = new GsonBuilder().create();

    protected JsonObject parseRequestBody(HttpServerExchange httpServerExchange) throws IOException {
        PooledByteBuffer pooledByteBuffer = httpServerExchange.getConnection().getByteBufferPool().allocate();
        ByteBuffer byteBuffer = pooledByteBuffer.getBuffer();


        httpServerExchange.getRequestChannel().read(byteBuffer);
        int pos = byteBuffer.position();
        byteBuffer.rewind();
        byte[] bytes = new byte[pos];
        byteBuffer.get(bytes);

        String requestBody = new String(bytes, Charset.forName("UTF-8"));
        JsonParser parser = new JsonParser();
        JsonObject requestBodyJson = parser.parse(requestBody).getAsJsonObject();


        byteBuffer.clear();

        return requestBodyJson;
    }
}
