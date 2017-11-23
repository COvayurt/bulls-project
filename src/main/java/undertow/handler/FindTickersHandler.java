package undertow.handler;

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

        httpServerExchange.getResponseHeaders().add(Headers.CONTENT_TYPE,"text/plain; charset=utf-8");
        httpServerExchange.getResponseSender().send(gson.toJson(bullsTickerList));
    }

}
