package util;

import model.BullsTicker;
import model.User;
import org.bson.Document;
import org.bson.types.Decimal128;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    public static User unmarshallUser(Document tickerMongoDbDoc) {
        List<BullsTicker> userBullsTickerList = new ArrayList<>();

        List<Document> userBullsTickerDocList = (List<Document>)tickerMongoDbDoc.get("userFollowTickers");

        if(userBullsTickerDocList != null && userBullsTickerDocList.size() > 0){
            for (Document doc : userBullsTickerDocList) {
                BullsTicker bullsTicker = unmarshallTicker(doc);
                userBullsTickerList.add(bullsTicker);
            }
        }

        User user = new User();
        user.setUsername((String) tickerMongoDbDoc.get("username"));
        user.setPassword((String) tickerMongoDbDoc.get("password"));
        user.setEmail((String) tickerMongoDbDoc.get("email"));
        user.setAccessToken((String) tickerMongoDbDoc.get("accessToken"));
        user.setNameSurname((String) tickerMongoDbDoc.get("nameSurname"));
        user.setTokenExpirationTime((Long) tickerMongoDbDoc.get("tokenExpirationTime"));
        user.setUserFollowTickers(userBullsTickerList);

        return user;
    }

    public static Document marshallUser(User user) {

        List<Document> docList = new ArrayList<>();
        for (BullsTicker ticker : user.getUserFollowTickers()) {
            Document tickerDoc = marshallTicker(ticker);
            docList.add(tickerDoc);
        }

        return new Document("username", user.getUsername())
                .append("password", user.getPassword())
                .append("email", user.getEmail())
                .append("nameSurname", user.getNameSurname())
                .append("accessToken", user.getAccessToken())
                .append("tokenExpirationTime", user.getTokenExpirationTime())
                .append("userFollowTickers", docList);
    }


    public static Document marshallTicker(BullsTicker bullsTicker) {
        return new Document("tickerShortCode", bullsTicker.getTickerShortCode())
                .append("tickerLongName", bullsTicker.getTickerLongName())
                .append("lastSignal", bullsTicker.getLastSignal())
                .append("lastFormation", bullsTicker.getLastFormation())
                .append("updateDate", bullsTicker.getUpdateDate())
                .append("lastPriceInTL", bullsTicker.getLastPriceInTL())
                .append("sixMonthsSuccessRate", bullsTicker.getSixMonthsSuccessRate())
                .append("oneYearSuccessRate", bullsTicker.getOneYearSuccessRate())
                .append("twoYearsSuccessRate", bullsTicker.getTwoYearsSuccessRate())
                .append("sixMonthIncome", bullsTicker.getSixMonthIncome())
                .append("oneYearIncome", bullsTicker.getOneYearIncome())
                .append("twoYearsIncome", bullsTicker.getTwoYearsIncome())
                .append("bullUrl", bullsTicker.getBullUrl())
                .append("tickerLang", bullsTicker.getTickerLang());
    }

    public static BullsTicker unmarshallTicker(Document tickerMongoDbDoc) {
        BullsTicker ticker = new BullsTicker();
        ticker.setTickerLang((String) tickerMongoDbDoc.get("tickerLang"));
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
        ticker.setUpdateDate((Long) tickerMongoDbDoc.get("updateDate"));

        return ticker;
    }


    public static Date isDateWithFormatReturnDate(String value, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date date = df.parse(value);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isDateWithFormat(String value, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            df.parse(value);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public static boolean isAmountShareValue(String amount) {
        try {
            String[] amountSplit = amount.split("[.]");
            if (amountSplit[1].length() > 2) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public static boolean isAmountShareCapitalValue(String amount) {
        try {
            String[] amountSplit = amount.split("[.]");
            if (amountSplit[1].length() <= 2) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

}
