package com.swlc.gadgetmart.backend.main.dto.response;

public class Response {
    private boolean state;
    private String message;

    public Response() {
    }

    public Response(boolean state, String message) {
        this.setState(state);
        this.setMessage(message);
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "StandardResponse{" +
                "state='" + state + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
