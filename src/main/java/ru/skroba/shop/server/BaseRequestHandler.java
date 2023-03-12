package ru.skroba.shop.server;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.skroba.shop.exception.ConfigurationException;
import ru.skroba.shop.exception.HandlerException;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseRequestHandler implements RequestHandler {
    private final Map<String, RequestHandler> handlerMap = new HashMap<>();
    
    public BaseRequestHandler(final String path) {
        handlerMap.put(path, this);
    }
    
    public BaseRequestHandler(final Set<String> paths) {
        paths.forEach(path -> handlerMap.put(path, this));
    }
    
    @Override
    public RequestHandler add(final RequestHandler handler) {
        handler.getPaths()
                .forEach(path -> handlerMap.merge(path, handler, (f, s) -> {
                    throw new ConfigurationException(
                            path + " is used by two handlers: " + f.getClass() + ", " + s.getClass());
                }));
        
        return this;
    }
    
    @Override
    public Observable<String> handle(final HttpServerRequest<ByteBuf> request) throws HandlerException {
        RequestHandler handler = handlerMap.get(request.getDecodedPath());
        
        if (handler == null) {
            throw new HandlerException(404, "Not Found");
        }
        
        if (handler == this) {
            return this.handlerImpl(request);
        }
        
        return handler.handle(request);
    }
    
    @Override
    public Set<String> getPaths() {
        return handlerMap.keySet();
    }
    
    protected abstract Observable<String> handlerImpl(final HttpServerRequest<ByteBuf> request) throws HandlerException;
}
