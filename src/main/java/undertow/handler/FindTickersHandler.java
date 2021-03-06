package undertow.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import model.BullsTicker;
import service.BullsTickerServiceImpl;
import service.api.BullsTickerService;

import java.util.List;

public class FindTickersHandler extends BaseRegisterHandler implements HttpHandler{

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {

        BullsTickerService ser = new BullsTickerServiceImpl();
        List<BullsTicker> bullsTickerList = ser.findBullsTicker();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(bullsTickerList);

        httpServerExchange.getResponseHeaders().add(Headers.CONTENT_TYPE,"application/json; charset=utf-8");
        httpServerExchange.getResponseSender().send(json);
    }

}
