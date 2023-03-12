package ru.skroba.shop.server;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.skroba.shop.exception.HandlerException;
import ru.skroba.shop.model.Product;
import ru.skroba.shop.service.UserService;
import rx.Observable;

import java.util.Optional;

public class UserProductsHandler extends BaseRequestHandler {
    private final UserService userService;
    
    public UserProductsHandler(final UserService userService) {
        super("/user/products");
        this.userService = userService;
    }
    
    @Override
    protected Observable<String> handlerImpl(final HttpServerRequest<ByteBuf> request) throws HandlerException {
        long userId = request.getQueryParameters()
                .get("userId")
                .stream()
                .findFirst()
                .flatMap(it -> {
                    try {
                        return Optional.of(Long.parseLong(it));
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                })
                .orElseThrow(() -> new HandlerException(400, "User id is absent!"));
        
        return userService.getProductsForUser(userId).map(Product::toString);
    }
}
