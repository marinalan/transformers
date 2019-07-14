package net.marinalan.aequilibrium.transformers;

public enum TransformerType {
  Autobot      ("A"),
  Decepticon   ("D");

  private final String shortName;

  TransformerType(String shortName) {
    this.shortName = shortName;
  }

  String shortVal(){
    return this.shortName;
  }
}