package undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import undertow.handler.*;

public class RequestServer {

    private static final HttpHandler ROUTES = new RoutingHandler()
            .get("/users/find/{username}", new FindUserHandler())
            .get("/users/find/all", new FindAllUsersHandler())
            .post("/users/register", new RegisterUserHandler())
            .post("/users/login", new LoginUserHandler())
            .post("/users/add-ticker", new AddTickerToUserHandler())
            .post("/users/remove-ticker", new RemoveTickerFromUserHandler())
            .get("/tickers/generate", new GenerateTickerFromBullsHandler())
            .post("/tickers/valuables/{username}", new ExtractValuableTickersForUserHandler())
            .get("/tickers/find/all", new FindTickersHandler());

    public static void main(String[] args) {

        String host;
        String port = System.getenv("PORT");

        if (port != null && !port.equals("")) {
            host = "0.0.0.0";
        } else {
            host = "localhost";
            port = "8080";
        }


        Undertow server = Undertow.builder().addHttpListener(Integer.parseInt(port), host, ROUTES).build();

        server.start();
    }

}
