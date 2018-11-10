package ru.job4j.tree;

/**
 * @author Alexander Kaleganov (mailto:alexmur07@mail.ru)
 * @version 2.0
 */

import java.util.Optional;

public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Добавить элемент child в parent.
     * Parent может иметь список child.
     *
     * @param parent parent.
     * @param child  child.
     * @return
     */
    boolean add(E parent, E child);

    Optional<Node<E>> findBy(E value);

}