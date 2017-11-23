package undertow.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import service.BullsTickerStatisticsServiceImpl;
import service.api.BullsTickerStatisticsService;

public class GenerateTickerFromBullsHandler extends BaseRegisterHandler implements HttpHandler{

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {


        Thread te = new Thread(() -> {
            BullsTickerStatisticsService ser = new BullsTickerStatisticsServiceImpl();
            ser.extractBullsShareStatistics();
        });

        te.start();


        httpServerExchange.getResponseSender().send("success!");
    }

}
