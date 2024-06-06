package ar.systec.models;

public record ConversionDTO(String result, String base_code, String target_code, double conversion_rate,
        double conversion_result, Integer time_last_update_unix) {

}
