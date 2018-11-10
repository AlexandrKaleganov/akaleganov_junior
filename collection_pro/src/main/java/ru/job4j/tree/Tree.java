package ru.job4j.tree;

import java.util.*;

/**
 * @param <E>
 * @author Alexander Kaleganov (mailto:alexmur07@mail.ru)
 * @version 2.0
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    private Node<E> root;
    private int size;
    private int isBinary;

    public Tree(E i) {
        this.root = new Node<E>(i);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean add(E parent, E child) {
        boolean res = false;
        Optional<Node<E>> rsl = this.findBy(parent);
        if (rsl.isPresent() && !(this.findBy(child).isPresent())) { //проверяет что элемент отсутствует в нашем  дереве
            rsl.get().add(new Node<>(child));
            res = true;
            size++;
        }
        return res;
    }

    /**
     * будет обходить дерево и проверять есть ли
     * элемент в нашем дереве и если есть то возвращает ноду содержащую элемент
     * @param value
     * @return
     */
    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    /**
     * метод проверяет бинарное дерево или нет
     *
     * @return
     */
    public boolean isBinary() {
        boolean rsl = true;
        isBinary = 0;
        Itr<E> it = (Itr<E>) this.iterator();
        while (it.hasNext()) {
            it.next();
            if (isBinary > 2) {
                rsl = false;
                break;
            }
        }
        return rsl;
    }

    public int getSize() {
        return size;
    }

    public Node<E> getRoot() {
        return root;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> iterator() {
        return new Itr<>();
    }


    private class Itr<E extends Comparable<E>> implements Iterator<E> {
        Queue<Node<E>> fullList = new LinkedList<Node<E>>();
        Node<E> iterList;
        private int index;
        private int i;

        @SuppressWarnings("unchecked")
        Itr() {
            fullList.add((Node<E>) root);
            poolitrList();
        }

        private void poolitrList() {
            iterList = fullList.poll();
        }

        @Override
        public boolean hasNext() {
            return index <= size;
        }

        @Override
        public E next() {
            boolean exit = false;
            Node<E> returnNext = null;
            if (!hasNext()) {
                throw new NoSuchElementException("элементы закончились");
            } else {
                if (index == 0) {
                    returnNext = this.iterList;
                } else {
                    while (!exit) {
                        if (i < iterList.leaves().size()) {
                            isBinary = iterList.leaves().size();
                            returnNext = returnLinkCikle();
                            exit = true;
                        } else {
                            i = 0;
                            isBinary = 0;
                            poolitrList();
                        }
                    }
                }
            }
            index++;
            return returnNext.getValue();
        }

        private Node<E> returnLinkCikle() {
            Node<E> returnLink = null;
            returnLink = iterList.leaves().get(i);
            this.fullList.offer(returnLink);
            i++;
            return returnLink;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
