package ar.systec;

import ar.systec.models.QueryCurrency;

public class Main {
    public static void main(String[] args) {
        QueryCurrency query = new QueryCurrency();

        System.out.println(query.getCurrency("ARS", "USD", 6579.00));
    }
}
