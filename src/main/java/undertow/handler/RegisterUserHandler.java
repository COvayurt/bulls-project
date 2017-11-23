package undertow.handler;

import com.google.gson.*;
import io.undertow.connector.PooledByteBuffer;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import model.BullsTicker;
import org.apache.commons.lang3.StringUtils;
import org.xnio.Pooled;
import service.BullsTickerServiceImpl;
import service.BullsTickerStatisticsServiceImpl;
import service.UserServiceImpl;
import service.api.BullsTickerService;
import service.api.BullsTickerStatisticsService;
import service.api.UserService;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;

public class RegisterUserHandler extends BaseRegisterHandler implements HttpHandler {



    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {

        if (httpServerExchange.isInIoThread()) {
            httpServerExchange.dispatch(this);
            return;
        }


        JsonObject requestBody = parseRequestBody(httpServerExchange);
        JsonElement userNameElement = requestBody.get("username");
        JsonElement passwordElement = requestBody.get("password");
        JsonElement nameSurnameElement = requestBody.get("nameSurname");
        JsonElement emailElement = requestBody.get("email");
        boolean success = false;
        if (userNameElement != null && !StringUtils.isEmpty(userNameElement.getAsString()) && passwordElement != null && !StringUtils.isEmpty(passwordElement.getAsString())
                && nameSurnameElement != null && !StringUtils.isEmpty(nameSurnameElement.getAsString()) && emailElement != null &&  !StringUtils.isEmpty(emailElement.getAsString())) {
            String username = userNameElement.toString();
            String password = passwordElement.toString();
            String nameSurname = nameSurnameElement.toString();
            String email = emailElement.toString();

            UserService userService = new UserServiceImpl();
            success = userService.registerUser(username, password, nameSurname, email);
        }



        httpServerExchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "text/plain; charset=utf-8");

        if(success){
            httpServerExchange.getResponseSender().send("success!");
        }else{
            httpServerExchange.getResponseSender().send("failed!");
        }
    }


}
