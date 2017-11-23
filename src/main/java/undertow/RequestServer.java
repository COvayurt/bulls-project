package undertow;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import undertow.handler.FindTickersHandler;
import undertow.handler.GenerateTickerFromBullsHandler;

public class RequestServer {

    public static void helloWorldHandler(HttpServerExchange exchange) {
        exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send("Hello World!");
    }

    public static void main(String[] args) {

        int port = 8080;
        String host = "localhost";

        Undertow server = Undertow
                .builder()
                .addHttpListener(port,
                        host,
                        Handlers.path()
                                .addExactPath("/tickers/generate", new GenerateTickerFromBullsHandler())
                                .addExactPath("/tickers/find/all", new FindTickersHandler())
                ).build();

        server.start();
    }

}
