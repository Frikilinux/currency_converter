package ar.systec;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private static DateTimeFormatter dateFormatted = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static List<String[]> codeList = obtenerCodigos().getSupported_codes();
    final static String API_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        int option = -1;

        String banner = """
                ╔══════════════════════════════════════════════════════╗
                ║                                                      ║
                ║  ░█▀▀░█▀█░█▀█░█░█░█▀▀░█▀▄░█▀▀░█▀█░█▀▄░░░█▀█░█▀▄░█▀█  ║
                ║  ░█░░░█░█░█░█░▀▄▀░█▀▀░█▀▄░▀▀█░█░█░█▀▄░░░█▀▀░█▀▄░█░█  ║
                ║  ░▀▀▀░▀▀▀░▀░▀░░▀░░▀▀▀░▀░▀░▀▀▀░▀▀▀░▀░▀░░░▀░░░▀░▀░▀▀▀  ║
                ║                                                      ║
                ╚══════════════════════════════════════════════════════╝
                    """;

        String menu = """
                1 - Obtener lista de monedas soportadas.
                2 - Convertir de una moneda a otra.
                3 - Listar todas las conversiones.
                -----------------------------------------
                0 - Salir.
                    """;

        while (option != 0) {
            System.out.println(CliColors.GREEN + banner + CliColors.RESET);
            System.out.println(CliColors.PURPLE_BOLD_BRIGHT + "Menú de opciones\n" + CliColors.RESET);
            System.out.println(menu);

            try {
                System.out.print("Introduce una opción: ");
                option = Integer.parseInt(userInput());
            } catch (NumberFormatException e) {
                System.out.println(CliColors.RED_BOLD_BRIGHT + "\n Introduce sólo números!" + CliColors.RESET);
                option = -1;
            } catch (Exception e) {
                System.out.println("Error desconocido" + e);
            }

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
                    String base_currency = userInput();
                    System.out.print("¿A que moneda convertir? ej.: ARS, USD, EUR: ");
                    String target_currency = userInput().toUpperCase();
                    System.out.print("Escribe el monto: ");
                    Double amount = Double.parseDouble(userInput());

                    var resultado = obtenerconversion(base_currency, target_currency, amount);
                    resultado.setBase_amount(amount);
                    conversions.add(resultado);

                    System.out.println("");
                    System.out.printf(
                            "El monto corresponde a " + CliColors.RED + "%.2f" + CliColors.RESET
                                    + " %ss. Última actualización %s\n",
                            resultado.getConversion_result(), obtenerNombreDeMoneda(target_currency),
                            dateFormatted.format(resultado.getTime_last_update_utc()));

                    break;
                case 3:
                    System.out.println(
                            CliColors.WHITE_BOLD_BRIGHT + "\nHistorial de conversiones: " + CliColors.RESET);
                    for (int i = 0; i < conversions.size(); i++) {
                        var conv = conversions.get(i);
                        System.out.printf(
                                "\n%s - %.2f %ss " + CliColors.CYAN_BOLD_BRIGHT + "➔" + CliColors.RESET + "  %.2f %ss",
                                dateFormatted.format(conv.getDate()), conv.getBase_amount(),
                                obtenerNombreDeMoneda(conv.getBase_code()), conv.getConversion_result(),
                                obtenerNombreDeMoneda(conv.getTarget_code()));
                    }
                    break;

                default:
                    System.out.println(CliColors.YELLOW + "\nOpción desconocida!, intenta otra vez." + CliColors.RESET);
                    break;
            }
        }

        System.out.println("Hasta luego perro!");

    }

    public static String userInput() {
        return System.console().readLine();
    }

    public static String obtenerNombreDeMoneda(String code) {
        return codeList.stream().filter(c -> c[0].equalsIgnoreCase(code)).toList().get(0)[1];
    }

    public static <T> T conversorDTO(String json, Class<T> clase) {
        return new Gson().fromJson(json, clase);
    }

    public static Codes obtenerCodigos() {
        var response = QueryApi.getData(API_URL + API_KEY + "/codes");
        var codesQuery = conversorDTO(response, CodesDTO.class);
        return new Codes(codesQuery);
    }

    public static Conversion obtenerconversion(String base_currency, String target_currency, double amount) {
        var response = QueryApi.getData(API_URL + API_KEY + "/pair/" + base_currency + "/"
                + target_currency + "/" + String.format("%.0f", amount));
        var conversionQuery = conversorDTO(response, ConversionDTO.class);
        return new Conversion(conversionQuery);
    }
}
