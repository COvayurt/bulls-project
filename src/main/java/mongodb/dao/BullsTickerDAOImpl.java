package mongodb.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import model.BullsTicker;
import model.BullsTickerSignalHistory;
import mongodb.dao.api.BullsTickerDAO;
import org.bson.Document;
import org.bson.types.Decimal128;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BullsTickerDAOImpl implements BullsTickerDAO {


    private Gson gson = new GsonBuilder().create();

    public List<BullsTicker> findAllBullsTicker() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("bulls_project");
        MongoCollection collection = database.getCollection("BullsTicker");
        MongoCursor cursor = collection.find().iterator();
        List<BullsTicker> bullsTickerList = new ArrayList<>();
        while (cursor.hasNext()) {
            Document obj = (Document) cursor.next();
            BullsTicker ticker = new BullsTicker();
            ticker.setTickerShortCode((String) obj.get("tickerShortCode"));
            ticker.setTickerLongName((String) obj.get("tickerLongName"));
            ticker.setLastSignal((String) obj.get("lastSignal"));
            ticker.setLastFormation((String) obj.get("lastFormation"));
            ticker.setLastPriceInTL(((Decimal128)obj.get("lastPriceInTL")).bigDecimalValue());
            ticker.setBullsTickerSignalHistoryList((List<BullsTickerSignalHistory>) obj.get("bullsTickerSignalHistoryList"));
            ticker.setSixMonthsSuccessRate((Double) obj.get("sixMonthsSuccessRate"));
            ticker.setOneYearSuccessRate((Double) obj.get("oneYearSuccessRate"));
            ticker.setTwoYearsSuccessRate((Double) obj.get("twoYearsSuccessRate"));
            ticker.setBullUrl((String) obj.get("bullUrl"));
            ticker.setSixMonthIncome(((Decimal128)obj.get("sixMonthIncome")).bigDecimalValue());
            ticker.setOneYearIncome(((Decimal128)obj.get("oneYearIncome")).bigDecimalValue());
            ticker.setTwoYearsIncome(((Decimal128)obj.get("twoYearsIncome")).bigDecimalValue());


            bullsTickerList.add(ticker);
        }
        return bullsTickerList;
    }

    public void insertBullsTicker(BullsTicker bullsTicker, MongoCollection collection) {

        if (bullsTicker != null) {

            Document bullsTickerDetailDbObject = new Document("tickerShortCode", bullsTicker.getTickerShortCode())
                    .append("tickerLongName", bullsTicker.getTickerLongName())
                    .append("lastSignal", bullsTicker.getLastSignal())
                    .append("lastFormation", bullsTicker.getLastFormation())
                    .append("lastPriceInTL", bullsTicker.getLastPriceInTL())
                    .append("sixMonthsSuccessRate", bullsTicker.getSixMonthsSuccessRate())
                    .append("bullsTickerSignalHistoryList", bullsTicker.getBullsTickerSignalHistoryList())
                    .append("oneYearSuccessRate", bullsTicker.getOneYearSuccessRate())
                    .append("twoYearsSuccessRate", bullsTicker.getTwoYearsSuccessRate())
                    .append("sixMonthIncome", bullsTicker.getSixMonthIncome())
                    .append("oneYearIncome", bullsTicker.getOneYearIncome())
                    .append("twoYearsIncome", bullsTicker.getTwoYearsIncome())
                    .append("bullUrl", bullsTicker.getBullUrl());

            collection.insertOne(bullsTickerDetailDbObject);
        }

    }

}
