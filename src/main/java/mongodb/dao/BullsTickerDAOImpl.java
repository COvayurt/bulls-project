package mongodb.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.BullsTickerDetail;
import mongodb.dao.api.BullsTickerDAO;

public class BullsTickerDAOImpl implements BullsTickerDAO {

    public void createBullsTickerDetail(BullsTickerDetail bullsTickerDetail){

        if (bullsTickerDetail != null) {
            MongoClient mongoClient = new MongoClient();
            MongoDatabase database = mongoClient.getDatabase("bulls_project");
            MongoCollection collection = database.getCollection("BullsTickerDetail");
            DBObject bullsTickerDetailDbObject = new BasicDBObject("tickerShortCode", bullsTickerDetail.getTickerShortCode())
                    .append("tickerLongName", bullsTickerDetail.getTickerLongName())
                    .append("lastSignal", bullsTickerDetail.getLastSignal())
                    .append("lastFormation", bullsTickerDetail.getLastFormation())
                    .append("lastPriceInTL", bullsTickerDetail.getLastPriceInTL())
                    .append("sixMonthsSuccessRate", bullsTickerDetail.getSixMonthsSuccessRate())
                    .append("oneYearSuccessRate", bullsTickerDetail.getOneYearSuccessRate())
                    .append("twoYearsSuccessRate", bullsTickerDetail.getTwoYearsSuccessRate())
                    .append("sixMonthIncome", bullsTickerDetail.getSixMonthIncome())
                    .append("oneYearIncome", bullsTickerDetail.getOneYearIncome())
                    .append("twoYearsIncome", bullsTickerDetail.getTwoYearsIncome())
                    .append("bullUrl", bullsTickerDetail.getBullUrl());

            collection.insertOne(bullsTickerDetailDbObject);
        }

    }

}
