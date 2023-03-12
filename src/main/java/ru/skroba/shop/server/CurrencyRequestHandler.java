package ru.skroba.shop.server;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.skroba.shop.base.BiFunctionWithException;
import ru.skroba.shop.base.Currency;
import ru.skroba.shop.exception.EntityException;
import ru.skroba.shop.exception.HandlerException;
import ru.skroba.shop.response.BaseResponse;
import ru.skroba.shop.service.CurrencyService;
import rx.Observable;

import java.util.Optional;

public class CurrencyRequestHandler extends BaseRequestHandler {
    private static final BiFunctionWithException<String, HttpServerRequest<ByteBuf>, Currency, HandlerException> CURRENCY_FROM_REQUEST = (nameParameter, request) -> request.getQueryParameters()
            .get(nameParameter)
            .stream()
            .findFirst()
            .flatMap(name -> {
                try {
                    return Optional.of(Currency.getCurrencyByName(name));
                } catch (EntityException e) {
                    return Optional.empty();
                }
            })
            .orElseThrow(() -> new HandlerException(400, "Absent, or unknown" + nameParameter + " currency!"));
    
    private final CurrencyService service;
    
    public CurrencyRequestHandler(final CurrencyService service) {
        super("/currency/upsert");
        this.service = service;
    }
    
    @Override
    protected Observable<String> handlerImpl(final HttpServerRequest<ByteBuf> request) throws HandlerException {
        final Currency sold = CURRENCY_FROM_REQUEST.apply("sold", request);
        final Currency bought = CURRENCY_FROM_REQUEST.apply("bought", request);
        
        final Double rate = request.getQueryParameters()
                .get("rate")
                .stream()
                .findFirst()
                .flatMap(rateString -> {
                    try {
                        return Optional.of(Double.parseDouble(rateString));
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                })
                .orElseThrow(() -> new HandlerException(400, "Absent, or malformed rate!"));
        
        return service.upsertRate(sold, bought, rate)
                .map(result -> new BaseResponse(200, result ? "Successful!" : "Can't perform change now!"))
                .map(BaseResponse::toString);
    }
}
