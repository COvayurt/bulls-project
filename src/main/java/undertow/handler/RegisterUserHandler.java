package undertow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.commons.lang3.StringUtils;
import service.UserServiceImpl;
import service.api.UserService;

public class RegisterUserHandler extends BaseRegisterHandler implements HttpHandler {


    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {

        if (httpServerExchange.isInIoThread()) {
            httpServerExchange.dispatch(this);
            return;
        }


        JsonNode requestBody = parseRequestBodyJson(httpServerExchange);
        String username = requestBody.get("username").textValue();
        String password = requestBody.get("password").textValue();
        String nameSurname = requestBody.get("nameSurname").textValue();
        String email = requestBody.get("email").textValue();
        boolean success = false;
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password) && !StringUtils.isEmpty(nameSurname) && !StringUtils.isEmpty(email)) {
            UserService userService = new UserServiceImpl();
            success = userService.registerUser(username, password, nameSurname, email);
        }


        httpServerExchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "text/plain; charset=utf-8");

        if (success) {
            httpServerExchange.getResponseSender().send("success!");
        } else {
            httpServerExchange.getResponseSender().send("failed!");
        }
    }


}
