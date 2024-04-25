package ar.systec;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ar.systec.models.Codes;
import ar.systec.models.QueryCodes;
import ar.systec.models.QueryCurrency;

public class Main {
    public static void main(String[] args) {
        QueryCurrency query = new QueryCurrency();
        QueryCodes codes = new QueryCodes();
        Scanner input = new Scanner(System.in);

        String banner = """

                """;

        String menu = """
                Opciones
                1 - Convertir a una moneda.
                2 - Get codes
                0 - Salir.
                    """;

        while (true) {
            int option;
            System.out.println(banner);
            System.out.println(menu);

            System.out.print("Ingrese una opción: ");
            option = input.nextInt();
            switch (option) {
                case 1:
                    System.out.print("Ingresa una moneda: ARS, USD, EUR: ");
                    String base_currency = input.next();
                    System.out.print("A que moneda convertir? ARS, USD, EUR: ");
                    String target_currency = input.next();
                    System.out.print("Escribe el monto: ");
                    double amount = input.nextDouble();

                    var resultado = query.getCurrency(base_currency, target_currency, amount);

                    // System.out.println(query.getCurrency(base_currency, target_currency,
                    // amount));
                    System.out.println("");
                    System.out.printf("El resultado es $ %.2f", resultado.conversion_result());
                    break;
                case 2:
                    System.out.println("Lista de todas las monedas soportadas: ");
                    var currencyCodes = new ArrayList<>(codes.getCodes().supported_codes());

                    System.out.println();

                    for (int i = 0; i < currencyCodes.size(); i++) {

                        System.out.printf("%s - %-36s\t%s", currencyCodes.get(i)[0], currencyCodes.get(i)[1],
                                (i + 1) % 3 == 0 ? "\n" : "");

                    }

                    // System.out.println(currencyCodes.size());

                    // for (String[] code : currencyCodes) {

                    // }

                    // currencyCodes.forEach(currency -> {
                    // System.out.printf("%s - %s\n", currency[0], currency[1]);
                    // });

                    break;
                default:
                    break;
            }

            if (option == 0) {
                System.out.println("Hasta luego perro!");
                break;
            }

        }

    }
}
