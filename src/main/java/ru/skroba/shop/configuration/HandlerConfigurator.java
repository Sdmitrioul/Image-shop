package ru.skroba.shop.configuration;

import ru.skroba.shop.base.Currency;
import ru.skroba.shop.response.ArrayResponse;
import ru.skroba.shop.response.BaseResponse;
import ru.skroba.shop.server.CurrencyRequestHandler;
import ru.skroba.shop.server.RequestHandler;
import ru.skroba.shop.server.SimpleHandler;
import rx.Observable;

public class HandlerConfigurator {
    private final ServiceConfigurator serviceConfigurator = new ServiceConfigurator();
    
    public RequestHandler getHandler() {
        return new CurrencyRequestHandler(serviceConfigurator.getCurrencyService()).add(SimpleHandler.of("/",
                r -> Observable.just(new BaseResponse(200, new ArrayResponse(Currency.values())).toString())));
    }
}
