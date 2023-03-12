package ru.skroba.shop.server;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.skroba.shop.base.Currency;
import ru.skroba.shop.exception.EntityException;
import ru.skroba.shop.exception.HandlerException;
import ru.skroba.shop.model.Product;
import ru.skroba.shop.response.BaseResponse;
import ru.skroba.shop.service.ProductService;
import rx.Observable;

import java.util.Optional;

public class ProductsHandler extends BaseRequestHandler {
    private final ProductService productService;
    
    public ProductsHandler(final ProductService productService) {
        super("/product/add");
        this.productService = productService;
    }
    
    @Override
    protected Observable<String> handlerImpl(final HttpServerRequest<ByteBuf> request) throws HandlerException {
        String productName = request.getQueryParameters()
                .get("name")
                .stream()
                .findFirst()
                .orElseThrow(() -> new HandlerException(400, "No product name parameter!"));
        Currency productCurrency = request.getQueryParameters()
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
        double productCost = request.getQueryParameters()
                .get("price")
                .stream()
                .findFirst()
                .flatMap(rateString -> {
                    try {
                        return Optional.of(Double.parseDouble(rateString));
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                })
                .orElseThrow(() -> new HandlerException(400, "No price or it is wrong formed!"));
        
        var product = Product.of(productName, productCost, productCurrency);
        
        return productService.addProduct(product)
                .map(success -> success ? new BaseResponse(201, product) : new BaseResponse(200,
                        "Product already " + "exist!"))
                .map(BaseResponse::toString);
    }
}
