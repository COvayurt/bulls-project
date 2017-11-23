package undertow;

import io.undertow.Handlers;
import io.undertow.Undertow;
import undertow.handler.FindTickersHandler;
import undertow.handler.GenerateTickerFromBullsHandler;
import undertow.handler.RegisterUserHandler;

public class RequestServer {


    public static void main(String[] args) {

        String host = "";
        String port = System.getenv("PORT");

        if (port != null && !port.equals("")) {
            host = "0.0.0.0";
        } else {
            host = "localhost";
            port = "8080";
        }


        Undertow server = Undertow
                .builder()
                .addHttpListener(Integer.parseInt(port),
                        host,
                        Handlers.path()
                                .addExactPath("/users/find/{username}", new GenerateTickerFromBullsHandler())
                                .addExactPath("/users/find/all", new GenerateTickerFromBullsHandler())
                                .addExactPath("/users/register", new RegisterUserHandler())
                                .addExactPath("/tickers/generate", new GenerateTickerFromBullsHandler())
                                .addExactPath("/tickers/find/all", new FindTickersHandler())
                ).build();

        server.start();
    }

}
