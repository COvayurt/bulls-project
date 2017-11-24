package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import constant.BullsTickerSignal;
import constant.Constants;
import model.BullsTicker;
import model.BullsTickerSignalHistory;
import mongodb.dao.BullsTickerDAOImpl;
import mongodb.dao.api.BullsTickerDAO;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.api.BullsTickerService;
import service.api.JSoupManagerService;
import util.Utils;


public class BullsTickerServiceImpl implements BullsTickerService {

    private JSoupManagerService jSoupManagerService = new JSoupManagerServiceImpl();
    private BullsTickerDAO bullsTickerDAO = new BullsTickerDAOImpl();


    public List<BullsTicker> extractValuableBullsTickersForUser(String lastSignal, String tickerShortCode, Double sixMonthsSuccessRate, Boolean sixMonthsSuccessRateGreater,
                                                                Double oneYearSuccessRate, Boolean oneYearSuccessRateGreater, Double twoYearsSuccessRate, Boolean twoYearsSuccessRateGreater,
                                                                Double lastPriceInTL, Boolean lastPriceInTLGreater, Double sixMonthIncome, Boolean sixMonthIncomeGreater,
                                                                Double oneYearIncome, Boolean oneYearIncomeGreater, Double twoYearsIncome, Boolean twoYearsIncomeGreater) {
        return bullsTickerDAO.findTickersByQuery(lastSignal, tickerShortCode,
                sixMonthsSuccessRate, sixMonthsSuccessRateGreater,
                oneYearSuccessRate, oneYearSuccessRateGreater,
                twoYearsSuccessRate, twoYearsSuccessRateGreater,
                lastPriceInTL, lastPriceInTLGreater,
                sixMonthIncome, sixMonthIncomeGreater,
                oneYearIncome, oneYearIncomeGreater,
                twoYearsIncome, twoYearsIncomeGreater);
    }


    public void extractBullsShareStatistics() {
        String tickers[] = Constants.BULLS_SHARES.split(",");

        System.setProperty("java.net.preferIPv4Stack", "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("BullsTicker");

        for (String ticker : tickers) {
            String fullUrl = Constants.BULLS_BASE_URL + "?" + Constants.BULLS_LANG_ATTR + Constants.BULLS_LANGUAGES.get("tr") + "&" + Constants.BULLS_SHARE_ATTR + ticker.trim();

            BullsTicker bullsTicker = extractBullsTickerDetailByUrl(fullUrl);

            if (bullsTicker != null) {
                insertBullsTicker(bullsTicker, collection);
            }

        }
    }

    public List<BullsTicker> findBullsTicker() {
        return bullsTickerDAO.findAllBullsTicker();
    }

    public void insertBullsTicker(BullsTicker bullsTicker, MongoCollection collection) {
        bullsTickerDAO.insertBullsTicker(bullsTicker, collection);
    }

    public BullsTicker extractBullsTickerDetailByUrl(String url) {
        Document doc = jSoupManagerService.getDocumentFromUrl(url);
        BullsTicker bullsTicker = null;
        if (doc != null) {
            bullsTicker = new BullsTicker();
            String shareShortCode = getShareShortCodeFromDoc(doc);
            if (shareShortCode != null && !shareShortCode.trim().equals("")) {
                bullsTicker.setTickerShortCode(shareShortCode);
            }

            String shareLastSignal = getShareLastSignalFromDoc(doc);
            if (shareLastSignal != null && !shareLastSignal.trim().equals("")) {
                bullsTicker.setLastSignal(shareLastSignal);
            }

            String shareLongName = getShareLongName(doc);
            if (shareLongName != null && !shareLongName.trim().equals("")) {
                bullsTicker.setTickerLongName(shareLongName);
            }

            String shareLastPriceInTL = getShareLastPriceInTL(doc);
            if (shareLastPriceInTL != null && !shareLastPriceInTL.trim().equals("")) {
                bullsTicker.setLastPriceInTL(new BigDecimal(shareLastPriceInTL));
            }

            String shareLastFormation = getShareLastFormation(doc);
            if (shareLastFormation != null && !shareLastFormation.trim().equals("")) {
                bullsTicker.setLastFormation(shareLastFormation);
            }

            List<BullsTickerSignalHistory> bullsTickerSignalHistoryList = getShareSignalHistory24List(doc);

            bullsTicker.setBullsTickerSignalHistoryList(bullsTickerSignalHistoryList);
            bullsTicker.setBullUrl(url);

            bullsTicker
                    .setSixMonthIncome(getSignalHistoryIncomeList(doc, Constants.SHARE_SIGNAL_HISTORY_TABLE_06_ID));
            bullsTicker
                    .setOneYearIncome(getSignalHistoryIncomeList(doc, Constants.SHARE_SIGNAL_HISTORY_TABLE_12_ID));
            if (bullsTickerSignalHistoryList.size() > 0
                    && bullsTickerSignalHistoryList.get(0).getPriceIfDoneWhatTold() != null
                    && bullsTickerSignalHistoryList.get(bullsTickerSignalHistoryList.size() - 1)
                    .getPriceIfDoneWhatTold() != null) {
                bullsTicker.setTwoYearsIncome(
                        bullsTickerSignalHistoryList.get(0).getPriceIfDoneWhatTold().subtract(bullsTickerSignalHistoryList
                                .get(bullsTickerSignalHistoryList.size() - 1).getPriceIfDoneWhatTold()));
            }

            System.out.println(bullsTicker.toString());
        }

        return bullsTicker;
    }

    private String getShareLastPriceInTL(Document doc) {
        Elements condition = doc.select("span[id$=" + Constants.SHARE_LAST_PRICE_IN_TL_SPAN_ID + "]");
        if (!condition.isEmpty()) {
            return condition.get(0).text();
        }
        return null;
    }

    private String getShareLastFormation(Document doc) {
        Elements condition = doc.select("span[id$=" + Constants.SHARE_LAST_FORMATION_SPAN_ID + "]");
        if (!condition.isEmpty()) {
            return condition.get(0).text();
        }
        return null;
    }

    private String getShareLongName(Document doc) {
        Elements condition = doc.select("span[id$=" + Constants.SHARE_LONG_NAME_SPAN_ID + "]");
        if (!condition.isEmpty()) {
            return condition.get(0).text();
        }
        return null;
    }

    private String getShareShortCodeFromDoc(Document doc) {
        Elements condition = doc.select("span[id$=" + Constants.SHARE_SHORT_CODE_SPAN_ID + "]");
        if (!condition.isEmpty()) {
            return condition.get(0).text();
        }
        return null;
    }

    private String getShareLastSignalFromDoc(Document doc) {
        Elements condition = doc.select("span[id$=" + Constants.SHARE_LAST_SIGNAL_SPAN_ID + "]");
        if (!condition.isEmpty()) {
            return condition.get(0).text();
        }
        return null;
    }

    private BigDecimal getSignalHistoryIncomeList(Document doc, String tableId) {
        Elements condition = doc.select("table[id$=" + tableId + "]");
        List<BullsTickerSignalHistory> bullsTickerSignalHistoryList = new ArrayList<>();
        Elements listElements = condition.get(0).children().get(0).children();

        for (int index = 1; index < listElements.size(); index++) {
            Elements tdElements = listElements.get(index).children();
            BullsTickerSignalHistory bullsTickerSignalHistory = new BullsTickerSignalHistory();

            for (int indexTd = 0; indexTd < tdElements.size(); indexTd++) {
                Element tdElement = tdElements.get(indexTd);
                String text = tdElement.text();
                if (text != null && !text.trim().equals("")) {
                    if (text.contains("/") && Utils.isDateWithFormat(text, Constants.DATE_FORMAT_SITE)) {
                        bullsTickerSignalHistory.setDate(text);
                    } else if (text.contains(".") && Utils.isAmountShareValue(text)) {
                        try {
                            bullsTickerSignalHistory.setPrice(new BigDecimal(text.replaceAll(",", "")));
                        } catch (Exception e) {
                            System.out.println("setPrice error " + text);
                        }
                    } else if (text.contains(".") && Utils.isAmountShareCapitalValue(text)) {
                        try {
                            bullsTickerSignalHistory.setPriceIfDoneWhatTold(new BigDecimal(text.replaceAll(",", "")));
                        } catch (Exception e) {
                            System.out.println("setPriceIfDoneWhatTold error " + text);
                        }
                    } else if (Constants.SHARE_SIGNAL_HISTORY_ACTIONS.containsKey(text.trim())) {
                        bullsTickerSignalHistory.setSignal(text.trim());
                    }

                } else {
                    Element tdElementImg = tdElement.child(0);
                    String attributeSrc = tdElementImg.attr("src");
                    bullsTickerSignalHistory.setCouldEarnMoney(!attributeSrc.toLowerCase().contains("uncheck"));
                }
            }

            bullsTickerSignalHistoryList.add(bullsTickerSignalHistory);
        }

        if (bullsTickerSignalHistoryList.size() > 0
                && bullsTickerSignalHistoryList.get(0).getPriceIfDoneWhatTold() != null && bullsTickerSignalHistoryList
                .get(bullsTickerSignalHistoryList.size() - 1).getPriceIfDoneWhatTold() != null) {
            return bullsTickerSignalHistoryList.get(0).getPriceIfDoneWhatTold().subtract(
                    bullsTickerSignalHistoryList.get(bullsTickerSignalHistoryList.size() - 1).getPriceIfDoneWhatTold());
        }
        return new BigDecimal("0");
    }

    private List<BullsTickerSignalHistory> getShareSignalHistory24List(Document doc) {
        Elements condition = doc.select("table[id$=" + Constants.SHARE_SIGNAL_HISTORY_TABLE_24_ID + "]");
        List<BullsTickerSignalHistory> bullsTickerSignalHistoryList = new ArrayList<>();
        Elements listElements = condition.get(0).children().get(0).children();

        for (int index = 1; index < listElements.size(); index++) {
            Elements tdElements = listElements.get(index).children();
            BullsTickerSignalHistory bullsTickerSignalHistory = new BullsTickerSignalHistory();

            for (int indexTd = 0; indexTd < tdElements.size(); indexTd++) {
                Element tdElement = tdElements.get(indexTd);
                String text = tdElement.text();
                if (text != null && !text.trim().equals("")) {
                    if (text.contains("/") && Utils.isDateWithFormat(text, Constants.DATE_FORMAT_SITE)) {
                        bullsTickerSignalHistory.setDate(text);
                    } else if (text.contains(".") && Utils.isAmountShareValue(text)) {
                        try {
                            bullsTickerSignalHistory.setPrice(new BigDecimal(text.replaceAll(",", "")));
                        } catch (Exception e) {
                            System.out.println("setPrice error " + text);
                        }
                    } else if (text.contains(".") && Utils.isAmountShareCapitalValue(text)) {
                        try {
                            bullsTickerSignalHistory.setPriceIfDoneWhatTold(new BigDecimal(text.replaceAll(",", "")));
                        } catch (Exception e) {
                            System.out.println("setPriceIfDoneWhatTold error " + text);
                        }
                    } else if (Constants.SHARE_SIGNAL_HISTORY_ACTIONS.containsKey(text.trim())) {
                        bullsTickerSignalHistory.setSignal(text.trim());
                    }

                } else {
                    Element tdElementImg = tdElement.child(0);
                    String attributeSrc = tdElementImg.attr("src");
                    bullsTickerSignalHistory.setCouldEarnMoney(!attributeSrc.toLowerCase().contains("uncheck"));
                }
            }

            bullsTickerSignalHistoryList.add(bullsTickerSignalHistory);
        }

        return bullsTickerSignalHistoryList;
    }
}
