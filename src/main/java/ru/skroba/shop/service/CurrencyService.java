package ru.skroba.shop.service;

import ru.skroba.shop.base.Currency;
import ru.skroba.shop.repository.CurrencyExchangeRepository;
import rx.Observable;

public class CurrencyService {
    private final CurrencyExchangeRepository currencyExchangeRepository;
    
    public CurrencyService(final CurrencyExchangeRepository currencyExchangeRepository) {
        this.currencyExchangeRepository = currencyExchangeRepository;
    }
    
    public Observable<Boolean> upsertRate(Currency sold, Currency bought, Double rate) {
        return currencyExchangeRepository.upsert(sold, bought, rate);
    }
}
