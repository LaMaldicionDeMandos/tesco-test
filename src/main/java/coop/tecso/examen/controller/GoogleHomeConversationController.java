package coop.tecso.examen.controller;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.smarthome.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
public class GoogleHomeConversationController extends DialogflowApp {
    private final static Logger LOG = LoggerFactory.getLogger(GoogleHomeConversationController.class);
    //headers: {host=tecso-test.herokuapp.com, connection=close, content-type=application/json;charset=UTF-8, google-assistant-api-version=v1, authorization=Bearer 123access, user-agent=Mozilla/5.0 (compatible; Google-Cloud-Functions/2.1; +http://www.google.com/bot.html), accept-encoding=gzip,deflate,br, x-request-id=24d7f548-37b4-4bc6-ac9c-1d437c128baa, content-length=117, x-forwarded-proto=https, x-forwarded-port=443, via=1.1 vegur, connect-time=0, x-request-start=1578626459848, total-route-time=2}
    @PostMapping("/conversation")
    public ResponseEntity<String> onPost(@RequestBody String body, @RequestHeader Map<String, String> headers) throws ExecutionException, InterruptedException {
        LOG.info("Entrando al post --> Body: {} --> headers: {}", body, headers);
        String response = this.handleRequest(body, headers).get();
        LOG.info("Response: {}", response);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ForIntent("color_favorito")
    public ActionResponse favoriteColor(ActionRequest request) {
        LOG.info("favorite_color start.");
        LOG.info("Request: {}", request.toString());
        String color = request.getParameter("color").toString();
        String response =
                "ok, tu color es "
                        + color + ", tiene " + color.length() + " letras.";

        ResponseBuilder responseBuilder = getResponseBuilder(request).add(response).endConversation();
        ActionResponse actionResponse = responseBuilder.build();
        LOG.info("Response: {}", actionResponse.toString());
        LOG.info("favorite_color end.");
        return actionResponse;
    }
}
