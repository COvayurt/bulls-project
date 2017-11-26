package service.api;

import com.mongodb.client.MongoCollection;
import model.BullsTicker;

import java.util.List;

public interface BullsTickerService {

	List<BullsTicker> findTickersByQuery(String lastSignal, String tickerShortCode, Double sixMonthsSuccessRate, Boolean sixMonthsSuccessRateGreater,
                                         Double oneYearSuccessRate, Boolean oneYearSuccessRateGreater, Double twoYearsSuccessRate, Boolean twoYearsSuccessRateGreater,
                                         Double lastPriceInTL, Boolean lastPriceInTLGreater, Double sixMonthIncome, Boolean sixMonthIncomeGreater,
                                         Double oneYearIncome, Boolean oneYearIncomeGreater, Double twoYearsIncome, Boolean twoYearsIncomeGreater, String tickerLang);

	void extractBullsShareStatistics();

	List<BullsTicker> findBullsTicker();

	BullsTicker extractBullsTickerDetailByUrl(String url, String lang);

	void insertBullsTicker(BullsTicker bullsTicker, MongoCollection collection );
}
