package ru.skroba.shop.configuration;

import ru.skroba.shop.base.Currency;
import ru.skroba.shop.response.ArrayResponse;
import ru.skroba.shop.response.BaseResponse;
import ru.skroba.shop.server.CurrencyRequestHandler;
import ru.skroba.shop.server.ProductsHandler;
import ru.skroba.shop.server.RequestHandler;
import ru.skroba.shop.server.SimpleHandler;
import ru.skroba.shop.server.UserProductsHandler;
import ru.skroba.shop.server.UserSignUpHandler;
import ru.skroba.shop.server.UsersHandler;
import rx.Observable;

public class HandlerConfigurator {
    private final ServiceConfigurator serviceConfigurator = new ServiceConfigurator();
    
    public RequestHandler getHandler() {
        return getCurrencyHandler().add(getUsersHandler())
                .add(getProductsHandler());
    }
    
    RequestHandler getCurrencyHandler() {
        return new CurrencyRequestHandler(serviceConfigurator.getCurrencyService()).add(SimpleHandler.of("/",
                r -> Observable.just(new BaseResponse(200, new ArrayResponse(Currency.values())).toString())));
    }
    
    RequestHandler getUsersHandler() {
        return new UserSignUpHandler(serviceConfigurator.getUserService()).add(
                        new UsersHandler(serviceConfigurator.getUserService()))
                .add(new UserProductsHandler(serviceConfigurator.getUserService()));
    }
    
    private RequestHandler getProductsHandler() {
        return new ProductsHandler(serviceConfigurator.getProductService());
    }
}
