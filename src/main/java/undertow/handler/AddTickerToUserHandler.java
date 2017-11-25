package undertow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import model.BullsTicker;
import model.User;
import org.apache.commons.lang3.StringUtils;
import service.BullsTickerServiceImpl;
import service.UserServiceImpl;
import service.api.BullsTickerService;
import service.api.UserService;

import java.util.*;

public class AddTickerToUserHandler extends BaseRegisterHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {


        if (httpServerExchange.isInIoThread()) {
            httpServerExchange.dispatch(this);
            return;
        }

        ObjectMapper mapper = new ObjectMapper();


        JsonNode requestBody = parseRequestBodyJson(httpServerExchange);
        String username = requestBody.get("username").textValue();
        String tickerShortCode = requestBody.get("tickerShortCode").textValue();
        boolean success = false;
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(tickerShortCode)) {

            UserService userService = new UserServiceImpl();
            User user = userService.findUserByUsername(username);
            BullsTickerService bullsTickerService = new BullsTickerServiceImpl();
            List<BullsTicker> ticker = bullsTickerService.findTickersByQuery(null, tickerShortCode, null, null, null, null, null, null, null, null, null, null, null, null, null, null);


            if (user != null && ticker != null && ticker.size() == 1) {
                if (user.getUserFollowTickers() == null) {
                    user.setUserFollowTickers(new ArrayList<>());
                }
                user.getUserFollowTickers().add(ticker.get(0));
                success = userService.updateUser(user);
            }
        }


        httpServerExchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "application/json; charset=utf-8");
        ObjectNode resultNode = mapper.createObjectNode();

        resultNode.put("success", success);
        if (success) {
            resultNode.put("message", "ticker successfully added to user!");
        } else {
            resultNode.put("message", "failed adding ticker to user!");
        }

        httpServerExchange.getResponseSender().send(resultNode.textValue());
    }
}
