package ru.job4j.treebin;

/**
 * @param <E>
 * @author Alexander Kaleganov
 * @version 001
 * @since04/07/2018
 */
public class Node<E extends Comparable<E>> {
    private Node<E> left;
    private Node<E> right;
    private final E value;

    Node(E value) {
        this.value = value;
    }

    public Node<E> getLeft() {
        return left;
    }

    public Node<E> getRight() {
        return right;
    }

    /**
     * сли входящий элемент меньше либо равен корню то вернёт тру
     * иначе фальш в тестах есть тестирование ноды
     *
     * @param that
     * @return
     */
    public boolean eqLeft(E that) {
        return this.value.compareTo(that) >= 0;
    }

    public boolean eqValue(E that) {
        return this.value.compareTo(that) == 0;
    }

    public void setLeft(Node<E> left) {
        this.left = left;
    }

    public void setRight(Node<E> right) {
        this.right = right;
    }

    public E getValue() {
        return value;
    }
}

