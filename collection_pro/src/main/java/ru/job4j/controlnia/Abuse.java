package ru.job4j.controlnia;

/**
 * Задан список предложений содержащийся в списке. Задан список стоп-слов.
 *
 * Нужно удалить стоп слова их предложений. В этом задании нужно использовать Stream API.
 */

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Abuse {
    public List<String> clean(List<String> words, List<String> filthy) {
        return words.stream().map(e -> {
                    StringBuilder bilder = new StringBuilder(e);
                    filthy.forEach(f ->
                    {
                        if (e.contains(f)) {
                            bilder.setLength(0);
                            bilder.append(e.replace(f, ""));
                        }
                    });
                    return bilder.toString();
                }
        ).map(e -> e.trim()).collect(Collectors.toList());
    }

//      return  words.stream().map(e->{
//        for (int i = 0; i < filthy.size(); i++) {
//            e = e.replace(filthy.get(i),"");
//        }
//        return e;
//    }).map(e->e.trim()).collect(Collectors.toList())
}