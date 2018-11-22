package com.odde.tdd;

import java.time.YearMonth;

public class Budget {
    public static final Budget ZERO = new Budget(null, 0l);

    private final YearMonth month;
    private final long amount;

    public Budget(YearMonth month, long amount) {
        this.month = month;
        this.amount = amount;
    }

    public YearMonth getMonth() {
        return month;
    }

    public long getAmount() {
        return amount;
    }
}
