package ru.job4j.piterservice;


import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Kaleganov
 * @since 001
 * первый метод будет читать файл в Префиксное дерево,
 * и в значениях будет раниться сет из списка индексов с каких начинаются искомое слово
 * второй метод будет возвращать сет по ключу
 */
@SuppressWarnings("unchecked")
public class WordIndex {
    private Trie data;


    public void loadFile(String file) {
        Integer counter = 0;
        data = new Trie();

        String[] strim = file.split(" ");
        for (int i = 0; i < strim.length; i++) {
            HashSet<Integer> tempSet;
            if (data.get(strim[i]) != null) {
                tempSet = (HashSet<Integer>) data.get(strim[i]);
                tempSet.add(counter);
            } else {
                tempSet = new HashSet<>();
                tempSet.add(counter);
                data.put(strim[i], tempSet);
            }
            counter += strim[i].length() + 1;
        }
    }

    /**
     * по ключу получает объект , в качестве объекта мы используем HashSet<Integer>
     *
     * @param zapros
     * @return
     */
    public Set<Integer> getIndex4Word(String zapros) {
        return (HashSet<Integer>) data.get(zapros);
    }

}
