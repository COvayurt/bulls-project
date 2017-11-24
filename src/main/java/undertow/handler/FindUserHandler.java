package undertow.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import model.User;
import service.UserServiceImpl;
import service.api.UserService;

import java.util.Deque;
import java.util.List;
import java.util.Map;

public class FindUserHandler extends BaseRegisterHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {

        UserService ser = new UserServiceImpl();

        Map<String, Deque<String>> pathParams = getPathParams(httpServerExchange);


        httpServerExchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "application/json; charset=utf-8");
        httpServerExchange.getResponseSender().send("");
    }

}
