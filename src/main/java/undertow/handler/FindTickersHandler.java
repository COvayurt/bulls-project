package undertow.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import service.BullsTickerStatisticsServiceImpl;
import service.api.BullsTickerStatisticsService;

public class FindTickersHandler implements HttpHandler{

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {




        httpServerExchange.getResponseSender().send("success!");
    }

}
