package service;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import constant.Constants;
import model.BullsTicker;
import service.api.BullsTickerService;
import service.api.BullsTickerStatisticsService;

public class BullsTickerStatisticsServiceImpl implements BullsTickerStatisticsService {

    private BullsTickerService bullsTickerService = new BullsTickerServiceImpl();

    public void extractBullsShareStatistics() {
        String tickers[] = Constants.BULLS_SHARES.split(",");

        System.setProperty("java.net.preferIPv4Stack" , "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("BullsTicker");

        for (String ticker : tickers) {
            String fullUrl = Constants.BULLS_BASE_URL + "?" + Constants.BULLS_LANG_ATTR + Constants.BULLS_LANGUAGES.get("tr") + "&" + Constants.BULLS_SHARE_ATTR + ticker.trim();

            BullsTicker bullsTicker = bullsTickerService.extractBullsTickerDetailByUrl(fullUrl);

            if(bullsTicker != null){
                bullsTickerService.insertBullsTicker(bullsTicker, collection);
            }

        }
    }
}
