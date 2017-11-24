package undertow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import model.User;
import org.apache.commons.lang3.StringUtils;
import service.UserServiceImpl;
import service.api.UserService;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class LoginUserHandler extends BaseRegisterHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {

        if (httpServerExchange.isInIoThread()) {
            httpServerExchange.dispatch(this);
            return;
        }


        JsonNode requestBody = parseRequestBodyJson(httpServerExchange);
        String username = requestBody.get("username").textValue();
        String password = requestBody.get("password").textValue();
        String accessToken = null;
        boolean success = false;
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {

            UserService userService = new UserServiceImpl();
            User user = userService.loginUser(username, password);

            if (user != null) {
                accessToken = UUID.randomUUID().toString();
                user.setAccessToken(accessToken);
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.MINUTE, 30);
                user.setTokenExpirationTime(cal.getTime().getTime());
                success = userService.updateUser(user);
            }
        }


        httpServerExchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "text/plain; charset=utf-8");

        if (!StringUtils.isEmpty(accessToken) && success) {
            httpServerExchange.getResponseSender().send("{\"accessToken\" : \"" + accessToken + "\"}");
        } else {
            httpServerExchange.getResponseSender().send("failed login!");
        }
    }
}
