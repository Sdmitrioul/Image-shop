package ru.skroba.shop.server;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.skroba.shop.base.Currency;
import ru.skroba.shop.exception.EntityException;
import ru.skroba.shop.exception.HandlerException;
import ru.skroba.shop.model.User;
import ru.skroba.shop.response.BaseResponse;
import ru.skroba.shop.service.UserService;
import rx.Observable;

import java.util.Optional;

public class UserSignUpHandler extends BaseRequestHandler {
    private final UserService service;
    
    public UserSignUpHandler(final UserService service) {
        super("/user/create");
        this.service = service;
    }
    
    @Override
    protected Observable<String> handlerImpl(final HttpServerRequest<ByteBuf> request) throws HandlerException {
        String userName = request.getQueryParameters()
                .get("userName")
                .stream()
                .findFirst()
                .orElseThrow(() -> new HandlerException(400, "User name is absent!"));
        Currency userCurrency = request.getQueryParameters()
                .get("currency")
                .stream()
                .findFirst()
                .flatMap(name -> {
                    try {
                        return Optional.of(Currency.getCurrencyByName(name));
                    } catch (EntityException e) {
                        return Optional.empty();
                    }
                })
                .orElseThrow(() -> new HandlerException(400, "Absent, or unknown currency!"));
        
        var user = User.of(userName, userCurrency);
        
        return service.registerUser(user)
                .map(success -> success ? new BaseResponse(201, user) : new BaseResponse(200, "User already exist!"))
                .map(BaseResponse::toString);
    }
}
