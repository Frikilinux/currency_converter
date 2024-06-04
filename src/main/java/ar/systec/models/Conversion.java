package ar.systec.models;

import java.time.LocalDate;

public class Conversion {
  String result;
  String base_code;
  String target_code;
  double conversion_rate;
  double conversion_result;
  LocalDate date;
  double base_amount;

  public double getBase_amount() {
    return base_amount;
  }

  public void setBase_amount(double base_amount) {
    this.base_amount = base_amount;
  }

  public LocalDate getDate() {
    return date;
  }

  public String getResult() {
    return result;
  }

  public String getBase_code() {
    return base_code;
  }

  public String getTarget_code() {
    return target_code;
  }

  public double getConversion_rate() {
    return conversion_rate;
  }

  public double getConversion_result() {
    return conversion_result;
  }

  public Conversion(ConversionDTO conversionQuery) {
    this.result = conversionQuery.result();
    this.base_code = conversionQuery.base_code();
    this.target_code = conversionQuery.target_code();
    this.conversion_rate = conversionQuery.conversion_rate();
    this.conversion_result = conversionQuery.conversion_result();
    this.date = LocalDate.now();
  }
}
