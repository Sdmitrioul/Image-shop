package ru.skroba.shop.response;

public record BaseResponse(int code, Object result) {
    @Override
    public String toString() {
        String message;
        
        if (result instanceof String resString) {
            message = "\"" + resString + "\"";
        } else {
            message = result.toString();
        }
        
        return "{\"code\": " + code + ", \"result\": " + message + "}";
    }
}
