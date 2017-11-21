package service.api;
 
import org.jsoup.nodes.Document;

public interface JSoupManagerService {

	Document getDocumentFromUrl(String url);
	
}
