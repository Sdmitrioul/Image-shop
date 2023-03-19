package ru.skroba.shop.server;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.skroba.shop.response.ArrayResponse;
import ru.skroba.shop.response.BaseResponse;
import ru.skroba.shop.service.UserService;
import rx.Observable;

public class UsersHandler extends BaseRequestHandler {
    private final UserService userService;
    
    public UsersHandler(final UserService userService) {
        super("/users");
        this.userService = userService;
    }
    
    @Override
    protected Observable<String> handlerImpl(final HttpServerRequest<ByteBuf> request) {
        return userService.getUsers()
                .map(users -> new ArrayResponse(users.toArray()))
                .map(arrR -> new BaseResponse(200, arrR))
                .map(BaseResponse::toString);
    }
}
