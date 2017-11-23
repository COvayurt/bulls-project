package mongodb.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import model.BullsTicker;
import mongodb.dao.api.BullsTickerDAO;
import org.bson.Document;
import org.bson.types.Decimal128;

import java.util.ArrayList;
import java.util.List;

public class BullsTickerDAOImpl implements BullsTickerDAO {


    private Gson gson = new GsonBuilder().create();



    public List<BullsTicker> findAllBullsTicker() {
        MongoClient mongoClient = new MongoClient("mongodb://bulls:bulls*@10@ds117136.mlab.com:17136/heroku_4gkwzvlq");
        MongoDatabase database = mongoClient.getDatabase("bulls_project");
        MongoCollection collection = database.getCollection("BullsTicker");
        MongoCursor cursor = collection.find().iterator();
        List<BullsTicker> bullsTickerList = new ArrayList<>();
        while (cursor.hasNext()) {
            BullsTicker bullsTicker = unmarshallTicker((Document) cursor.next());
            bullsTickerList.add(bullsTicker);
        }
        return bullsTickerList;
    }

    public void insertBullsTicker(BullsTicker bullsTicker, MongoCollection collection) {

        if (bullsTicker != null) {

            Document bullsTickerDbObject = marshallTicker(bullsTicker);


            collection.insertOne(bullsTickerDbObject);
        }

    }


    private BullsTicker unmarshallTicker(Document tickerMongoDbDoc) {
        BullsTicker ticker = new BullsTicker();
        ticker.setTickerShortCode((String) tickerMongoDbDoc.get("tickerShortCode"));
        ticker.setTickerLongName((String) tickerMongoDbDoc.get("tickerLongName"));
        ticker.setLastSignal((String) tickerMongoDbDoc.get("lastSignal"));
        ticker.setLastFormation((String) tickerMongoDbDoc.get("lastFormation"));
        ticker.setLastPriceInTL(((Decimal128) tickerMongoDbDoc.get("lastPriceInTL")).bigDecimalValue());
        ticker.setSixMonthsSuccessRate((Double) tickerMongoDbDoc.get("sixMonthsSuccessRate"));
        ticker.setOneYearSuccessRate((Double) tickerMongoDbDoc.get("oneYearSuccessRate"));
        ticker.setTwoYearsSuccessRate((Double) tickerMongoDbDoc.get("twoYearsSuccessRate"));
        ticker.setBullUrl((String) tickerMongoDbDoc.get("bullUrl"));
        ticker.setSixMonthIncome(((Decimal128) tickerMongoDbDoc.get("sixMonthIncome")).bigDecimalValue());
        ticker.setOneYearIncome(((Decimal128) tickerMongoDbDoc.get("oneYearIncome")).bigDecimalValue());
        ticker.setTwoYearsIncome(((Decimal128) tickerMongoDbDoc.get("twoYearsIncome")).bigDecimalValue());

        return ticker;
    }

    private Document marshallTicker(BullsTicker bullsTicker) {
       return new Document("tickerShortCode", bullsTicker.getTickerShortCode())
                .append("tickerLongName", bullsTicker.getTickerLongName())
                .append("lastSignal", bullsTicker.getLastSignal())
                .append("lastFormation", bullsTicker.getLastFormation())
                .append("lastPriceInTL", bullsTicker.getLastPriceInTL())
                .append("sixMonthsSuccessRate", bullsTicker.getSixMonthsSuccessRate())
                .append("oneYearSuccessRate", bullsTicker.getOneYearSuccessRate())
                .append("twoYearsSuccessRate", bullsTicker.getTwoYearsSuccessRate())
                .append("sixMonthIncome", bullsTicker.getSixMonthIncome())
                .append("oneYearIncome", bullsTicker.getOneYearIncome())
                .append("twoYearsIncome", bullsTicker.getTwoYearsIncome())
                .append("bullUrl", bullsTicker.getBullUrl());
    }

}
