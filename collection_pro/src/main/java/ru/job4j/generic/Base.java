package ru.job4j.generic;

/**
 * @author Alexander Kaleganov
 * @since 15/05/2018
 * @version 1
 */
abstract class Base {
  private final String id;

  public Base(String id) {
      this.id = id;
  }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.id.toString();
    }
}
