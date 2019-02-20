package ru.job4j.forum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedList;

public abstract class Mmain {
    public static void main(String[] args) {
        A object = new B();
        object.setK(1);
        System.out.println(((B) object).k);
    }

    private static class A {
        private int k;

        public int getK() {
            return k;
        }

        public void setK(int k) {
            this.k = k + 1;
        }
    }

    private static class B extends A {
        private int k;

        @Override
        public void setK(int k) {
            this.k = k * 10;
        }
    }

    LinkedList<String> json = new LinkedList<>();
    ObjectMapper objectMapper = new ObjectMapper();

    public <E> void jsonListAdd(E e) throws JsonProcessingException {
        json.push(objectMapper.writeValueAsString(e));
    }
    public String jsonListPool() {
        return this.json.poll();
    }
}
