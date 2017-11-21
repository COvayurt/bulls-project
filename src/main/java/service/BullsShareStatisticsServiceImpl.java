package service;


import constant.Constants;
import service.api.BullsShareDetailService;
import service.api.BullsShareStatisticsService;

public class BullsShareStatisticsServiceImpl implements BullsShareStatisticsService {

	BullsShareDetailService bullsShareDetailService = new BullsShareDetailServiceImpl();
	
	public void extractBullsShareStatistics(){
		String tickers[] = Constants.BULLS_SHARES.split(",");
		
		for (String ticker : tickers) {
			String fullUrl = Constants.BULLS_BASE_URL + "?" + Constants.BULLS_LANG_ATTR + Constants.BULLS_LANGUAGES.get("tr") + "&" + Constants.BULLS_SHARE_ATTR + ticker.trim();
			
			bullsShareDetailService.extractBullsShareDetailByUrl(fullUrl);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
