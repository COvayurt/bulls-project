package mongodb.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import model.BullsTicker;
import mongodb.dao.api.BullsTickerDAO;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.Decimal128;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class BullsTickerDAOImpl implements BullsTickerDAO {

    public List<BullsTicker> findTickersByQuery(String lastSignal, String tickerShortCode, Double sixMonthsSuccessRate, Boolean sixMonthsSuccessRateGreater,
                                                Double oneYearSuccessRate, Boolean oneYearSuccessRateGreater, Double twoYearsSuccessRate, Boolean twoYearsSuccessRateGreater,
                                                Double lastPriceInTL, Boolean lastPriceInTLGreater, Double sixMonthIncome, Boolean sixMonthIncomeGreater,
                                                Double oneYearIncome, Boolean oneYearIncomeGreater, Double twoYearsIncome, Boolean twoYearsIncomeGreater) {

        System.setProperty("java.net.preferIPv4Stack", "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("BullsTicker");
        List<BullsTicker> bullsTickerList = new ArrayList<>();
        BasicDBObject field = new BasicDBObject();
        if (!StringUtils.isEmpty(lastSignal)) {
            field.put("lastSignal", lastSignal);
        }
        if (!StringUtils.isEmpty(tickerShortCode)) {
            field.put("tickerShortCode", tickerShortCode);
        }
        if (sixMonthsSuccessRate != null && sixMonthsSuccessRate > 0.0) {
            field.put("sixMonthsSuccessRate", new BasicDBObject(sixMonthsSuccessRateGreater ? "$gt" : "$lt", sixMonthsSuccessRate));
        }

        if (oneYearSuccessRate != null && oneYearSuccessRate > 0.0) {
            field.put("oneYearSuccessRate", new BasicDBObject(oneYearSuccessRateGreater ? "$gt" : "$lt", oneYearSuccessRate));
        }
        if (twoYearsSuccessRate != null && twoYearsSuccessRate > 0.0) {
            field.put("twoYearsSuccessRate", new BasicDBObject(twoYearsSuccessRateGreater ? "$gt" : "$lt", twoYearsSuccessRate));
        }

        if (lastPriceInTL != null && lastPriceInTL > 0.0) {
            field.put("lastPriceInTL", new BasicDBObject(lastPriceInTLGreater ? "$gt" : "$lt", lastPriceInTL));
        }

        if (sixMonthIncome != null && sixMonthIncome > 0.0) {
            field.put("sixMonthIncome", new BasicDBObject(sixMonthIncomeGreater ? "$gt" : "$lt", sixMonthIncome));
        }
        if (oneYearIncome != null && oneYearIncome > 0.0) {
            field.put("oneYearIncome", new BasicDBObject(oneYearIncomeGreater ? "$gt" : "$lt", oneYearIncome));
        }
        if (twoYearsIncome != null && twoYearsIncome > 0.0) {
            field.put("twoYearsIncome", new BasicDBObject(twoYearsIncomeGreater ? "$gt" : "$lt", twoYearsIncome));
        }

        for (Object bullsTickerObject : collection.find(field)) {
            BullsTicker bullsTicker = Utils.unmarshallTicker((Document) bullsTickerObject);
            if (bullsTicker != null) {
                bullsTickerList.add(bullsTicker);
            }
        }

        return bullsTickerList;
    }


    public List<BullsTicker> findAllBullsTicker() {
        System.setProperty("java.net.preferIPv4Stack", "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("BullsTicker");
        MongoCursor cursor = collection.find().iterator();
        List<BullsTicker> bullsTickerList = new ArrayList<>();
        while (cursor.hasNext()) {
            BullsTicker bullsTicker = Utils.unmarshallTicker((Document) cursor.next());
            bullsTickerList.add(bullsTicker);
        }
        return bullsTickerList;
    }

    public void insertBullsTicker(BullsTicker bullsTicker, MongoCollection collection) {

        if (bullsTicker != null) {

            Document bullsTickerDbObject = Utils.marshallTicker(bullsTicker);


            collection.insertOne(bullsTickerDbObject);
        }

    }





}
