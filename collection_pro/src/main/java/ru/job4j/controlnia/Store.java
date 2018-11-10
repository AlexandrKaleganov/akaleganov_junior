package ru.job4j.controlnia;

import ru.job4j.mapmain.HashMapMain;

import java.util.HashMap;
import java.util.List;

/**
 * сунул один массив в хешмеп, и потом листом в цикле прошёлся по хешмепу, и нашёл все совпадения и 
 */
public class Store {
    public Info diff(List<User> previoues, List<User> current) {
        Info rsl = new Info();
        HashMap<Integer, String> currentMap = new HashMap<Integer, String>();
        current.forEach(user -> {
            currentMap.put(user.id, user.name);
        });
        previoues.forEach(user -> {
            if (currentMap.containsKey(user.id)) {
                rsl.deleteTemp();
                if (!user.name.equals(currentMap.get(user.id))) {
                    rsl.setReplaseElement();
                }
            }
        });
        rsl.setDeleteElement(previoues.size());
        rsl.setAddElement(previoues.size(), current.size());
        return rsl;
    }

    static class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof User)) {
                return false;
            }

            User user = (User) o;

            if (id != user.id) {
                return false;
            }
            return name != null ? name.equals(user.name) : user.name == null;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }
    }
}