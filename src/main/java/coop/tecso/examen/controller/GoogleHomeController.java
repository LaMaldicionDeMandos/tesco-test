package coop.tecso.examen.controller;

import com.google.actions.api.smarthome.*;
import com.google.api.client.json.Json;
import com.google.common.collect.Lists;
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

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class GoogleHomeController extends SmartHomeApp {
    private final static Logger LOG = LoggerFactory.getLogger(GoogleHomeController.class);
	
    @PostMapping("/smarthome")
    public ResponseEntity<String> sync(@RequestBody String body, @RequestHeader Map<String, String> headers) throws ExecutionException, InterruptedException {
        LOG.info("Entrando al sync --> Body: {} --> headers: {}", body, headers);
        String response = this.handleRequest(body.toString(), headers).get();
        LOG.info("Response: {}", response);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public void onDisconnect(@NotNull DisconnectRequest disconnectRequest, @Nullable Map<?, ?> map) {

    }

    @NotNull
    @Override
    public ExecuteResponse onExecute(@NotNull ExecuteRequest executeRequest, @Nullable Map<?, ?> map) {
        return null;
    }

    @NotNull
    @Override
    public QueryResponse onQuery(@NotNull QueryRequest queryRequest, @Nullable Map<?, ?> map) {
        return null;
    }

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
