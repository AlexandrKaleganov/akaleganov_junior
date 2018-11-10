package ru.job4j.treebin;

import java.util.*;

/**
 * @param <E>
 * @author Alexander Kaleganov
 * @version 001
 * @since04/07/2018
 */
public class TreeBin<E extends Comparable<E>> implements Iterable<E> {
    private Node<E> root;
    private int size;

    public void add(E element) {
        Node<E> linkTemp;
        Node<E> addLink = new Node<E>(element);
        if (getSize() < 1) {
            root = new Node<E>(element);
        } else {
            linkTemp = findBylinkroot(element);
            if (!(linkTemp == null)) {
                if (linkTemp.eqLeft(element)) {
                    linkTemp.setLeft(addLink);
                } else {
                    linkTemp.setRight(addLink);
                }
            }
        }
        size++;
    }

    /**
     * метод будет находить Ноду, которая будет корнем  для нового элемента
     *
     * @param value
     * @return
     */
    public Node<E> findBylinkroot(E value) {
        Node<E> rsl = null;
        Node<E> linkTemp = root;
        boolean exit = false;
        while (!exit) {
            if (linkTemp.eqLeft(value)) {
                if (!(linkTemp.getLeft() == null)) {
                    linkTemp = linkTemp.getLeft();
                } else {
                    rsl = linkTemp;
                    exit = true;
                }
            } else {
                if (!(linkTemp.getRight() == null)) {
                    linkTemp = linkTemp.getRight();
                } else {
                    rsl = linkTemp;
                    exit = true;
                }
            }
        }
        return rsl;
    }

    /**
     * метод будет возвращать Ноду  которая содержит искомый объект
     * для данной реализации метод не нужен, но если делать скажем тремап то нам понадобиться искать ключ
     *
     * @param element
     * @return
     */
    public Optional<Node<E>> findBy(E element) {
        Optional<Node<E>> rsl = Optional.empty();
        Node<E> nextLink = root;
        while (nextLink != null) {
            if (nextLink.eqLeft(element)) {
                if (nextLink.eqValue(element)) {
                    rsl = Optional.of(nextLink);
                    break;
                } else {
                    nextLink = nextLink.getLeft();
                }
            } else {
                nextLink = nextLink.getRight();
            }
        }
        return rsl;
    }

    /**
     * получить размер дерева
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr<>();
    }

    private class Itr<E extends Comparable<E>> implements Iterator<E> {
        private int index;
        private Queue<Node<E>> linkqueue = new LinkedList<>();
        private Node<E> el;

        @SuppressWarnings("unchecked")
        Itr() {
            linkqueue.offer((Node<E>) root);
            poolLinkqueue();
        }

        private void poolLinkqueue() {
            if (!linkqueue.isEmpty()) {
                el = linkqueue.poll();
            }
        }

        @Override
        public boolean hasNext() {
            return this.index < getSize();
        }

        @Override
        public E next() {
            E rsl = null;
            if (!hasNext()) {
                throw new NoSuchElementException("элементы закончились");
            } else {
                rsl = el.getValue();
                if (el.getLeft() != null) {
                    linkqueue.offer(el.getLeft());
                }
                if (el.getRight() != null) {
                    linkqueue.offer(el.getRight());
                }
                poolLinkqueue();
                index++;
            }
            return rsl;
        }
    }
}
