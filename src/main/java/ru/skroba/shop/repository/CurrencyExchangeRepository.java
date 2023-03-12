package ru.skroba.shop.repository;

import org.bson.Document;
import ru.skroba.shop.base.Currency;
import ru.skroba.shop.model.CurrencyExchangeRate;
import rx.Observable;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class CurrencyExchangeRepository extends BaseRepository<CurrencyExchangeRate> {
    private static final String COLLECTION_NAME = "rates";
    
    public CurrencyExchangeRepository(final Database database) {
        super(COLLECTION_NAME, database);
    }
    
    public Observable<CurrencyExchangeRate> getRateBySoldBought(final Currency sold, final Currency bought) {
        if (sold == bought) {
            return Observable.just(new CurrencyExchangeRate(-1, sold, bought, 1));
        }
        
        return getCollection().find(and(eq(CurrencyExchangeRate.CER_SOLD_CUR_FIELD_NAME, sold.getName()),
                        eq(CurrencyExchangeRate.CER_BOUGHT_CUR_FIELD_NAME, bought.getName())))
                .toObservable()
                .singleOrDefault(null)
                .map(it -> it != null ? this.factory(it) : new CurrencyExchangeRate(-1, sold, bought, Double.NaN));
    }
    
    @Override
    protected CurrencyExchangeRate factory(final Document doc) {
        return CurrencyExchangeRate.of(doc);
    }
}
