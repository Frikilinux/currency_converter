package ar.systec;

import java.util.Scanner;
import ar.systec.models.QueryCurrency;

public class Main {
    public static void main(String[] args) {
        QueryCurrency query = new QueryCurrency();
        Scanner input = new Scanner(System.in);

        String banner = """

                """;

        String menu = """
                Opciones
                1 - Convertir a una moneda.
                2 - No se
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

                    System.out.println(query.getCurrency(base_currency, target_currency, amount));
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
