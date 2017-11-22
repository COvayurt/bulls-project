package undertow.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import model.BullsTicker;
import service.BullsTickerServiceImpl;
import service.BullsTickerStatisticsServiceImpl;
import service.api.BullsTickerService;
import service.api.BullsTickerStatisticsService;

import java.util.List;

public class FindTickersHandler implements HttpHandler{

    private Gson gson = new GsonBuilder().create();

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {

        BullsTickerService ser = new BullsTickerServiceImpl();
        List<BullsTicker> bullsTickerList = ser.findBullsTicker();

        httpServerExchange.getResponseSender().send(gson.toJson(bullsTickerList, BullsTicker.class));
    }

}
