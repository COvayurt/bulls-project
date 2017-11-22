package service.api;

import model.BullsTickerDetail;

public interface BullsTickerDetailService {

	BullsTickerDetail extractBullsTickerDetailByUrl(String url);

	void insertBullsTicker(BullsTickerDetail bullsTickerDetail);
}
