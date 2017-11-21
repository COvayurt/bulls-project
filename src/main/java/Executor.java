import service.BullsShareStatisticsServiceImpl;
import service.api.BullsShareStatisticsService;

public class Executor {

	public static void main(String[] args){
		BullsShareStatisticsService bullsShareDetailService = new BullsShareStatisticsServiceImpl();
		
		bullsShareDetailService.extractBullsShareStatistics();
	}
	
}
