package undertow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import constant.BullsTickerSignal;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import model.BullsTicker;
import org.apache.commons.lang3.StringUtils;
import service.BullsTickerServiceImpl;
import service.api.BullsTickerService;

import java.util.Deque;
import java.util.List;
import java.util.Map;

public class ExtractValuableTickersForUserHandler extends BaseRegisterHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        httpServerExchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "application/json; charset=utf-8");
        BullsTickerService bullsTickerService = new BullsTickerServiceImpl();
        JsonNode payload = parseRequestBodyJson(httpServerExchange);
        Map<String, Deque<String>> params = httpServerExchange.getQueryParameters();
        boolean accessTokenSuccess = false;
        List<BullsTicker> bullsTickerList = null;
        if (payload != null && payload.size() > 0 && params != null && params.size() > 0) {
            String accessToken = payload.get("accessToken").textValue();
            String username = params.get("username").getFirst();

            String lastSignal = null;
            String tickerShortCode = null;
            Double sixMonthsSuccessRate = null;
            Boolean sixMonthsSuccessRateGreater = null;
            Double oneYearSuccessRate = null;
            Boolean oneYearSuccessRateGreater = null;
            Double twoYearsSuccessRate = null;
            Boolean twoYearsSuccessRateGreater = null;
            Double lastPriceInTL = null;
            Boolean lastPriceInTLGreater = null;
            Double sixMonthIncome = null;
            Boolean sixMonthIncomeGreater = null;
            Double oneYearIncome = null;
            Boolean oneYearIncomeGreater = null;
            Double twoYearsIncome = null;
            Boolean twoYearsIncomeGreater = null;

            if (params.get("lastSignal") != null) {
                lastSignal = params.get("lastSignal").getFirst();
            }
            if (params.get("tickerShortCode") != null) {
                tickerShortCode = params.get("tickerShortCode").getFirst();
            }
            if (params.get("sixMonthsSuccessRate") != null) {
                sixMonthsSuccessRate = Double.parseDouble(params.get("sixMonthsSuccessRate").getFirst());
            }
            if (params.get("sixMonthsSuccessRateGreater") != null) {
                sixMonthsSuccessRateGreater = params.get("sixMonthsSuccessRateGreater").getFirst().equals("true");
            }
            if (params.get("oneYearSuccessRate") != null) {
                oneYearSuccessRate = Double.parseDouble(params.get("oneYearSuccessRate").getFirst());
            }
            if (params.get("oneYearSuccessRateGreater") != null) {
                oneYearSuccessRateGreater = params.get("oneYearSuccessRateGreater").getFirst().equals("true");
            }
            if (params.get("twoYearsSuccessRate") != null) {
                twoYearsSuccessRate = Double.parseDouble(params.get("twoYearsSuccessRate").getFirst());
            }
            if (params.get("twoYearsSuccessRateGreater") != null) {
                twoYearsSuccessRateGreater = params.get("twoYearsSuccessRateGreater").getFirst().equals("true");
            }
            if (params.get("lastPriceInTL") != null) {
                lastPriceInTL = Double.parseDouble(params.get("lastPriceInTL").getFirst());
            }
            if (params.get("lastPriceInTLGreater") != null) {
                lastPriceInTLGreater = params.get("lastPriceInTLGreater").getFirst().equals("true");
            }
            if (params.get("sixMonthIncome") != null) {
                sixMonthIncome = Double.parseDouble(params.get("sixMonthIncome").getFirst());
            }
            if (params.get("sixMonthIncomeGreater") != null) {
                sixMonthIncomeGreater = params.get("sixMonthIncomeGreater").getFirst().equals("true");
            }
            if (params.get("oneYearIncome") != null) {
                oneYearIncome = Double.parseDouble(params.get("oneYearIncome").getFirst());
            }
            if (params.get("oneYearIncomeGreater") != null) {
                oneYearIncomeGreater = params.get("oneYearIncomeGreater").getFirst().equals("true");
            }
            if (params.get("twoYearsIncome") != null) {
                twoYearsIncome = Double.parseDouble(params.get("twoYearsIncome").getFirst());
            }
            if (params.get("twoYearsIncomeGreater") != null) {
                twoYearsIncomeGreater = params.get("twoYearsIncomeGreater").getFirst().equals("true");
            }


            if (!StringUtils.isEmpty(accessToken) && !StringUtils.isEmpty(username)) {
                accessTokenSuccess = checkAccessToken(accessToken, username);
                if (accessTokenSuccess) {
                    bullsTickerList = bullsTickerService.extractValuableBullsTickersForUser(lastSignal, tickerShortCode,
                            sixMonthsSuccessRate, sixMonthsSuccessRateGreater,
                            oneYearSuccessRate, oneYearSuccessRateGreater,
                            twoYearsSuccessRate, twoYearsSuccessRateGreater,
                            lastPriceInTL, lastPriceInTLGreater,
                            sixMonthIncome, sixMonthIncomeGreater,
                            oneYearIncome, oneYearIncomeGreater,
                            twoYearsIncome, twoYearsIncomeGreater);
                }
            }
        }

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(bullsTickerList);


        if (accessTokenSuccess) {
            httpServerExchange.getResponseSender().send(json);
        } else {
            ObjectNode resultNode = mapper.createObjectNode();
            resultNode.put("success", accessTokenSuccess);
            resultNode.put("message", "session has been ended! please login.");
        }


    }

}
