package ru.skroba.shop.server;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.skroba.shop.base.FunctionWithException;
import ru.skroba.shop.exception.HandlerException;
import rx.Observable;

public final class SimpleHandler extends BaseRequestHandler {
    private final FunctionWithException<HttpServerRequest<ByteBuf>, Observable<String>, HandlerException> handler;
    
    private SimpleHandler(final String path,
                          FunctionWithException<HttpServerRequest<ByteBuf>, Observable<String>, HandlerException> handler) {
        super(path);
        this.handler = handler;
    }
    
    public static SimpleHandler of(final String path,
                                   FunctionWithException<HttpServerRequest<ByteBuf>, Observable<String>, HandlerException> handler) {
        return new SimpleHandler(path, handler);
    }
    
    @Override
    protected Observable<String> handlerImpl(final HttpServerRequest<ByteBuf> request) throws HandlerException {
        return handler.apply(request);
    }
}
