package undertow.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import service.BullsTickerServiceImpl;
import service.api.BullsTickerService;

public class GenerateTickerFromBullsHandler extends BaseRegisterHandler implements HttpHandler{

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {


        Thread te = new Thread(() -> {
            BullsTickerService ser = new BullsTickerServiceImpl();
            ser.extractBullsShareStatistics();
        });

        te.start();


        httpServerExchange.getResponseSender().send("success!");
    }

}
