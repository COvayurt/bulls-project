package mongodb.dao.api;

import com.mongodb.client.MongoCollection;
import model.BullsTicker;

import java.util.List;

public interface BullsTickerDAO {

    List<BullsTicker> findAllBullsTicker();

    void insertBullsTicker(BullsTicker bullsTicker, MongoCollection collection);
}
