package ru.job4j.piterservice;

import java.util.HashMap;

/**
 * @author Alexander Kaleganov
 * @since 24/07/2018 23:25
 * Создал статический класс нода? которая содержит хештаблицу  ключём которых является символ а значение нода)
 * в ноде храница значение если оно добавлено
 * использовал именно хешмапу, т.к. не хотел бегать в циклах по массиву а просто спрашивать контейнс, пут и гет
 * также обошёля без джинериков, просто значение хранит обжект и возвращает обжект, всё остальное делается в классе WordIndex
 * по приведению типов, в дефолтной реализации префиксного дерева к стате также значение хранится в обжекте не используя
 * джинерики
 */
public class Trie {
    private Node root;

    Trie() {
        root = new Node();
    }

    public void put(String key, Object element) {
        Node tempNode = root;
        for (String s : key.split("")) {
            Node nodeNext = new Node();
            if (!tempNode.getChildren().containsKey(s)) {
                tempNode.getChildren().put(s, nodeNext);
                tempNode = nodeNext;
            } else {
                tempNode = tempNode.getChildren().get(s);
            }
        }
        tempNode.addValue(element);
    }

    public Object get(String key) {
        Object rsl = null;
        Node tempNode = root;
        for (String s : key.split("")) {
            Node nodeNext = new Node();
            if (!tempNode.getChildren().containsKey(s)) {
                rsl = null;
                break;
            } else {
                tempNode = tempNode.getChildren().get(s);
                rsl = tempNode.getValue();
            }
        }
        return rsl;
    }

    class Node {
        private final HashMap<String, Node> children = new HashMap<String, Node>();
        private Object value;

        void addValue(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public HashMap<String, Node> getChildren() {
            return children;
        }
    }
}
