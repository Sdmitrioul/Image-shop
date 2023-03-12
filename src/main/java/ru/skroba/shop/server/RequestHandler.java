package ru.skroba.shop.server;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.skroba.shop.exception.HandlerException;
import rx.Observable;

import java.util.Set;

public interface RequestHandler {
    static RequestHandler of(RequestHandler handler) {
        return handler;
    }
    
    RequestHandler add(RequestHandler handler);
    
    Observable<String> handle(HttpServerRequest<ByteBuf> request) throws HandlerException;
    
    Set<String> getPaths();
}
