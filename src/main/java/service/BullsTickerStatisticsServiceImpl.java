package service;


import constant.Constants;
import model.BullsTickerDetail;
import service.api.BullsTickerDetailService;
import service.api.BullsTickerStatisticsService;

public class BullsTickerStatisticsServiceImpl implements BullsTickerStatisticsService {

    private BullsTickerDetailService bullsTickerDetailService = new BullsTickerDetailServiceImpl();

    public void extractBullsShareStatistics() {
        String tickers[] = Constants.BULLS_SHARES.split(",");

        for (String ticker : tickers) {
            String fullUrl = Constants.BULLS_BASE_URL + "?" + Constants.BULLS_LANG_ATTR + Constants.BULLS_LANGUAGES.get("tr") + "&" + Constants.BULLS_SHARE_ATTR + ticker.trim();

            BullsTickerDetail bullsTickerDetail = bullsTickerDetailService.extractBullsTickerDetailByUrl(fullUrl);

            if(bullsTickerDetail != null){
                bullsTickerDetailService.insertBullsTicker(bullsTickerDetail);
            }

        }
    }
}
