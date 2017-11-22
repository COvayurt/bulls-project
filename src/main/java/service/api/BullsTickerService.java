package service.api;

import com.mongodb.client.MongoCollection;
import model.BullsTicker;

import java.util.List;

public interface BullsTickerService {

	List<BullsTicker> findBullsTicker();

	BullsTicker extractBullsTickerDetailByUrl(String url);

	void insertBullsTicker(BullsTicker bullsTicker, MongoCollection collection );
}
