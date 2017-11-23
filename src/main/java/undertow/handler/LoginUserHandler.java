package undertow.handler;

import com.google.gson.*;
import io.undertow.connector.PooledByteBuffer;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import model.User;
import org.apache.commons.lang3.StringUtils;
import service.UserServiceImpl;
import service.api.UserService;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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


        JsonObject requestBody = parseRequestBody(httpServerExchange);
        JsonElement userNameElement = requestBody.get("username");
        JsonElement passwordElement = requestBody.get("password");
        String accessToken = null;
        boolean success = false;
        if (userNameElement != null && !StringUtils.isEmpty(userNameElement.getAsString()) && passwordElement != null && !StringUtils.isEmpty(passwordElement.getAsString())) {
            String username = userNameElement.toString();
            String password = passwordElement.toString();

            UserService userService = new UserServiceImpl();
            User user = userService.loginUser(username, password);

            if(user != null){
                accessToken = UUID.randomUUID().toString();
                user.setAccessToken(accessToken);
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.set(Calendar.MINUTE, 2);
                user.setTokenExpirationTime(cal.getTime().getTime());
                success = userService.updateUser(user);
            }
        }



        httpServerExchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "text/plain; charset=utf-8");

        if(!StringUtils.isEmpty(accessToken) && success){
            httpServerExchange.getResponseSender().send("{\"accessToken\" : \"" + accessToken + "\"}");
        }else{
            httpServerExchange.getResponseSender().send("failed login!");
        }
    }
}
