package com.example.webbookingroom.util;

import com.example.webbookingroom.dto.response.ServerResponse;
import com.example.webbookingroom.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public class CommonUtils {
    @NotNull
    public static ServerResponse getResponse(HttpStatus status, String message, Object data) {
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setStatus(status);
        serverResponse.setMessage(message);
        serverResponse.setData(data );
        return serverResponse;
    }
}
