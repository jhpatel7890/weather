package com.weather.exception;

/** Custom exception class to represent any internal processing error scenarios */
public class InternalServerErrorException extends Exception {
  public InternalServerErrorException(String message) {
    super(message);
  }
}
