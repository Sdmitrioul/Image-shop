package ru.skroba.shop;

import io.reactivex.netty.protocol.http.server.HttpServer;
import ru.skroba.shop.configuration.HandlerConfigurator;
import ru.skroba.shop.exception.HandlerException;
import rx.Observable;

public class Main {
    public static void main(String[] args) {
        var handler = new HandlerConfigurator().getHandler();
        int port = Integer.parseInt(System.getenv("SERVER_PORT"));
        
        HttpServer.newServer(port)
                .start((req, resp) -> {
                    Observable<String> response;
                    
                    try {
                        response = handler.handle(req);
                    } catch (HandlerException e) {
                        return resp.writeString(Observable.just(e.toString()));
                    }
                    
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }
}
