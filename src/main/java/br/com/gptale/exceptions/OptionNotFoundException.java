package br.com.gptale.exceptions;

public class OptionNotFoundException extends RuntimeException {
  public OptionNotFoundException(int option) {
    super(String.format("Não existe uma opção de número %d para essa história.", option));
  }
}
