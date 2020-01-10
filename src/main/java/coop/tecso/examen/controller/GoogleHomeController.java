package coop.tecso.examen.controller;

import com.google.actions.api.smarthome.*;
import com.google.api.client.json.Json;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
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
public class GoogleHomeController extends SmartHomeApp {
    private final static Logger LOG = LoggerFactory.getLogger(GoogleHomeController.class);
    private static boolean lightState = false;
    //headers: {host=tecso-test.herokuapp.com, connection=close, content-type=application/json;charset=UTF-8, google-assistant-api-version=v1, authorization=Bearer 123access, user-agent=Mozilla/5.0 (compatible; Google-Cloud-Functions/2.1; +http://www.google.com/bot.html), accept-encoding=gzip,deflate,br, x-request-id=24d7f548-37b4-4bc6-ac9c-1d437c128baa, content-length=117, x-forwarded-proto=https, x-forwarded-port=443, via=1.1 vegur, connect-time=0, x-request-start=1578626459848, total-route-time=2}
    @PostMapping("/smarthome")
    public ResponseEntity<String> onPost(@RequestBody String body, @RequestHeader Map<String, String> headers) throws ExecutionException, InterruptedException {
        LOG.info("Entrando al post --> Body: {} --> headers: {}", body, headers);
        String response = this.handleRequest(body, headers).get();
        LOG.info("Response: {}", response);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public void onDisconnect(@NotNull DisconnectRequest disconnectRequest, @Nullable Map<?, ?> map) {

    }

    //Body: {"inputs":[{"context":{"locale_country":"US","locale_language":"en"},"intent":"action.devices.EXECUTE","payload":{"commands":[{"devices":[{"id":"L1"}],"execution":[{"command":"action.devices.commands.OnOff","params":{"on":true}}]}]}}],"requestId":"2283667149695842823"}
    @NotNull
    @Override
    public ExecuteResponse onExecute(@NotNull ExecuteRequest executeRequest, @Nullable Map<?, ?> map) {
        ExecuteResponse res = new ExecuteResponse();
        List<ExecuteResponse.Payload.Commands> commandsResponse = new ArrayList<>();
        List<String> successfulDevices = new ArrayList<>();

        ExecuteRequest.Inputs.Payload.Commands.Execution execution =
                ((ExecuteRequest.Inputs) executeRequest.inputs[0]).payload.commands[0].execution[0];
        boolean param = (Boolean) execution.getParams().get("on");
        lightState = param;

        ExecuteResponse.Payload payload = new ExecuteResponse.Payload();

        payload.setCommands(new ExecuteResponse.Payload.Commands[] {
                new ExecuteResponse.Payload.Commands(
                        new String[] {"L1"},
                        "SUCCESS",
                        new HashMap<String, Object>() {{ put("on", lightState); }},
                        null, null)});
        return new ExecuteResponse(executeRequest.getRequestId(), payload);

    }

    //Body: {"inputs":[{"intent":"action.devices.QUERY","payload":{"devices":[{"id":"L1"}]}}],"requestId":"18012506326314158884"}
    @NotNull
    @Override
    public QueryResponse onQuery(@NotNull QueryRequest queryRequest, @Nullable Map<?, ?> map) {
        QueryResponse res = new QueryResponse();
        res.setRequestId(queryRequest.requestId);
        res.setPayload(new QueryResponse.Payload());

        Map<String, Map<String, Boolean>> state = Maps.newHashMap();
        Map<String, Boolean> m = Maps.newHashMap();
        m.put("on", lightState);
        state.put("L1", m);
        res.payload.setDevices(state);
        return res;
    }

    //Body: {"inputs":[{"intent":"action.devices.SYNC"}],"requestId":"6593579405166916194"}
    @NotNull
    @Override
    public SyncResponse onSync(@NotNull SyncRequest syncRequest, @Nullable Map<?, ?> map) {
        SyncResponse response = new SyncResponse();
        response.setRequestId(syncRequest.requestId);
        response.setPayload(new SyncResponse.Payload());
        response.payload.agentUserId = "83367.15267389";

        response.payload.devices = new SyncResponse.Payload.Device[1];
        SyncResponse.Payload.Device.Builder builder = new SyncResponse.Payload.Device.Builder();
        SyncResponse.Payload.Device device = builder.setId("L1")
                .setType("action.devices.types.LIGHT")
                .addTrait("action.devices.traits.OnOff")
                .setName(Arrays.asList("Luz Virtual"), "Luz V 1", Lists.newArrayList("la luz"))
                .setWillReportState(false)
                .build();
        response.payload.devices[0] = device;


        return response;
    }
}
