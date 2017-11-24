package undertow.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import model.User;
import service.BullsTickerStatisticsServiceImpl;
import service.UserServiceImpl;
import service.api.BullsTickerStatisticsService;
import service.api.UserService;

import java.util.List;

public class FindAllUsersHandler extends BaseRegisterHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {

        UserService ser = new UserServiceImpl();
        List<User> users = ser.findAllUsers();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(users);

        httpServerExchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "application/json; charset=utf-8");
        httpServerExchange.getResponseSender().send(json);
    }

}
