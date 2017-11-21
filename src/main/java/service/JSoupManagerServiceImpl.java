package service;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import service.api.JSoupManagerService;


public class JSoupManagerServiceImpl implements JSoupManagerService {

	public Document getDocumentFromUrl(String url) {
		try {
			return Jsoup.connect(url).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
