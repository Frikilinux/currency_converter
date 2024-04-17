package ar.systec.models;

public record Currency(String result, String base_code, String target_code, double conversion_rate,
    double conversion_result) {

}
