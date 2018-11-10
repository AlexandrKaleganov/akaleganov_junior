package ru.job4j.forum;


import java.util.*;

public class FroindData {
    TreeMap<String, ArrayList<String>> map = new TreeMap<>();


    public void add(String stroka) {
        String[] froinds = stroka.split(" ");
        if (froinds.length == 2) {
            if (this.map.get(froinds[0]) == null) {
                this.map.put(froinds[0], new ArrayList<String>(Arrays.asList(froinds[1])));
            } else {
                ArrayList<String> temp = this.map.get(froinds[0]);
                temp.add(froinds[1]);
            }
        } else {
            System.out.println("Вводите текст правильно йопта согласно заданию");
        }
    }

    @SuppressWarnings("LoopStatementThatDoesntLoop")
    public String get(String stroka) {
        String rsl = "";
        int k = 0;
        LinkedHashSet<String> res = new LinkedHashSet<>(Arrays.asList(stroka, " дружит с : "));
        if (this.map.containsKey(stroka)) {
            res.addAll(this.map.get(stroka));
        }
        Iterator<String> iterator = this.map.keySet().iterator();
        while (iterator.hasNext()) {
            String temp = iterator.next();
            for (int i = 0; i < this.map.get(temp).size(); i++) {
                if (stroka.contains(this.map.get(temp).get(i))) {
                    for (int j = 0; j < this.map.get(temp).size(); j++) {
                        if (res.contains(this.map.get(temp).get(i))) {
                            res.add(temp);
                        }
                        break;
                    }

                }

            }
        }

        Iterator<String> iter = res.iterator();
        while (iter.hasNext()) {
            rsl += iter.next();
            if (k > 1 && iter.hasNext()) {
                rsl += ", ";
            }
            k++;
        }
        return rsl;
    }
}
