package ru.job4j.list;

/**
 * проверим на цикличность связанный список
 *
 * @param <T>
 */
public class Node<T> {
    T value;
    Node<T> next;

    Node(T element) {
        this.value = element;
    }

    /**
     * если есть зацикливание то вернёт тру
     * элементы бегают в цикле с разными шагами если гдето есть кольцо
     * то рано или поздно они встретятся и метод вернёт true
     * иначе како-нибудь элемент накнётся на нул  и вернёт фальш
     *
     * @param first
     * @return
     */
    @SuppressWarnings("unchecked")
    boolean hasCycle(Node first) {
        boolean res = false;
        Node<T> linkOne = first;
        Node<T> linkTwo = first.next;
        while (linkOne != null) {
            if (linkOne == linkTwo) {
                res = true;
                break;
            } else if (linkOne == null || linkTwo == null) {
                break;
            }
            linkOne = linkOne.next;
            linkTwo = linkTwo.next;
            if (!(linkTwo == null)) {
                linkTwo = linkTwo.next;
            }
        }
        return res;
    }

}
