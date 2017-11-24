package undertow.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import model.User;
import org.apache.commons.lang3.StringUtils;
import service.UserServiceImpl;
import service.api.UserService;

import java.util.Deque;
import java.util.List;
import java.util.Map;

public class FindUserHandler extends BaseRegisterHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {

        UserService ser = new UserServiceImpl();
        User user = null;
        Map<String, Deque<String>> pathParams = httpServerExchange.getQueryParameters();
        if (pathParams.size() > 0) {
            String username = pathParams.get("username").getFirst();

            if (!StringUtils.isEmpty(username)) {
                user = ser.findUserByUsername(username);
            }
        }

        httpServerExchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "application/json; charset=utf-8");


        if (user != null) {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(user);
            httpServerExchange.getResponseSender().send(json);
        } else {
            httpServerExchange.getResponseSender().send("failed to find user!");
        }
    }

}
