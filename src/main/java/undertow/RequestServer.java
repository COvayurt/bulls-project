package undertow;

import io.undertow.Handlers;
import io.undertow.Undertow;
import undertow.handler.FindTickersHandler;
import undertow.handler.GenerateTickerFromBullsHandler;

public class RequestServer {


    public static void main(String[] args) {

        String host = "0.0.0.0";
        String port = System.getenv("PORT");

        Undertow server = Undertow
                .builder()
                .addHttpListener(Integer.parseInt(port),
                        host,
                        Handlers.path()
                                .addExactPath("/tickers/generate", new GenerateTickerFromBullsHandler())
                                .addExactPath("/tickers/find/all", new FindTickersHandler())
                ).build();

        server.start();
    }

}
