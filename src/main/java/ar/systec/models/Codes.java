package ar.systec.models;

import java.util.ArrayList;

public class Codes {
  String result;
  ArrayList<String[]> supported_codes;

  public Codes(CodesDTO codesQuery) {
    this.result = codesQuery.result();
    this.supported_codes = codesQuery.supported_codes();
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public ArrayList<String[]> getSupported_codes() {
    return supported_codes;
  }

  public void setSupported_codes(ArrayList<String[]> supported_codes) {
    this.supported_codes = supported_codes;
  }
}
