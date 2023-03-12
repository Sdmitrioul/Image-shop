package ru.skroba.shop.repository;

import org.bson.Document;
import org.bson.conversions.Bson;
import ru.skroba.shop.base.Currency;
import ru.skroba.shop.model.CurrencyExchangeRate;
import rx.Observable;

import java.util.Objects;
import java.util.function.BiFunction;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class CurrencyExchangeRepository extends BaseRepository<CurrencyExchangeRate> {
    private static final String COLLECTION_NAME = "rates";
    
    private static final BiFunction<Currency, Currency, Bson> FILTER_FACTORY = (sold, bought) -> and(
            eq(CurrencyExchangeRate.CER_SOLD_CUR_FIELD_NAME, sold.getName()),
            eq(CurrencyExchangeRate.CER_BOUGHT_CUR_FIELD_NAME, bought.getName()));
    
    public CurrencyExchangeRepository(final Database database) {
        super(COLLECTION_NAME, database);
    }
    
    public Observable<CurrencyExchangeRate> getRateBySoldBought(final Currency sold, final Currency bought) {
        if (sold == bought) {
            return Observable.just(new CurrencyExchangeRate(-1, sold, bought, 1));
        }
        
        return getCollection().find(FILTER_FACTORY.apply(sold, bought))
                .toObservable()
                .singleOrDefault(null)
                .map(it -> it != null ? this.factory(it) : new CurrencyExchangeRate(-1, sold, bought, Double.NaN));
    }
    
    @Override
    protected CurrencyExchangeRate factory(final Document doc) {
        return CurrencyExchangeRate.of(doc);
    }
    
    public Observable<Boolean> upsert(final Currency sold, final Currency bought, final Double rate) {
        var filter = FILTER_FACTORY.apply(sold, bought);
        var cer = CurrencyExchangeRate.of(sold, bought, rate);
        
        return getCollection().find(filter)
                .toObservable()
                .singleOrDefault(null)
                .flatMap(it -> it == null ? this.addEntity(cer) : getCollection().findOneAndReplace(filter,
                                cer.getModel())
                        .map(Objects::nonNull));
    }
}
