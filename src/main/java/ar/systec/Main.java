package ar.systec;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import ar.systec.models.Codes;
import ar.systec.models.CodesDTO;
import ar.systec.models.Conversion;
import ar.systec.models.ConversionDTO;
import ar.systec.models.QueryApi;
import ar.systec.service.CliColors;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    private static String API_KEY = Dotenv.load().get("API_KEY");
    private static List<Conversion> conversions = new ArrayList<>();

    public static void main(String[] args) {
        Codes codesSupported = obtenerCodigos();
        Scanner input = new Scanner(System.in);

        String banner = """

                """;

        String menu = """
                Opciones
                1 - Obtener lista de monedas soportadas.
                2 - Convertir a una moneda.
                3 - Listar todas las conversiones.
                0 - Salir.
                    """;

        while (true) {
            int option;
            System.out.println(banner);
            System.out.println(menu);
            var codeList = codesSupported.getSupported_codes();

            System.out.print("Ingrese una opción: ");
            option = input.nextInt();
            switch (option) {
                case 1:
                    System.out.println("\nLista de todas las monedas soportadas: ");
                    for (int i = 0; i < codeList.size(); i++) {

                        System.out.printf(CliColors.GREEN + "%s" + CliColors.RESET + " - %-36s\t%s",
                                codeList.get(i)[0], codeList.get(i)[1], (i + 1) % 3 == 0 ? "\n" : "");
                    }
                    break;
                case 2:
                    System.out.print("Ingresa una moneda: ej.: ARS, USD, EUR: ");
                    String base_currency = input.next();
                    System.out.print("A que moneda convertir? ej.: ARS, USD, EUR: ");
                    String target_currency = input.next();
                    System.out.print("Escribe el monto: ");
                    double amount = input.nextDouble();

                    // var resultado = query.getCurrency(base_currency, target_currency, amount);
                    var resultado = obtenerconversion(base_currency, target_currency, amount);
                    resultado.setBase_amount(amount);
                    conversions.add(resultado);

                    var tipoDemoneda = codeList.stream().filter(c -> c[0].equalsIgnoreCase(target_currency)).toList();
                    var monedaSelecionada = tipoDemoneda.get(0)[1];
                    System.out.println("");
                    System.out.printf("El monto corresponde a " + CliColors.RED + "%.2f" + CliColors.RESET + " %s ",
                            resultado.getConversion_result(), monedaSelecionada);
                    break;
                case 3:
                    System.out.println("Lista de todas las conversiones: ");
                    for (int i = 0; i < conversions.size(); i++) {
                        var conversion = conversions.get(i);
                        System.out.printf(
                                "\n" + conversion.getDate() + " - Conversion de " + conversion.getBase_amount());
                    }
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

    public static <T> T conversorDTO(String json, Class<T> clase) {
        return new Gson().fromJson(json, clase);
    }

    public static Codes obtenerCodigos() {
        var response = QueryApi.getData("https://v6.exchangerate-api.com/v6/" +
                API_KEY + "/codes");
        var codesQuery = conversorDTO(response, CodesDTO.class);
        return new Codes(codesQuery);
    }

    public static Conversion obtenerconversion(String base_currency, String target_currency, double amount) {
        var response = QueryApi.getData("https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/" + base_currency + "/"
                + target_currency + "/" + amount);
        var conversionQuery = conversorDTO(response, ConversionDTO.class);
        return new Conversion(conversionQuery);
    }
}
