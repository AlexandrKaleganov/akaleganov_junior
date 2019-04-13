package ru.job4j.calculate;
/**
 * @autor Alexandr Kaleganov
 * @version 3
 * @since 11.04.2019
 */

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

class Calc {
    private final HashMap<String, Function<List<String>, Double>> funcMap;
    private final Double expected;
    private final StringBuilder resStroka = new StringBuilder();
    private boolean stop = false;

    Calc(Double expected) {
        this.expected = expected;
        this.funcMap = new HashMap<>();
        this.funcMap.put("+", list ->
                Double.valueOf(list.get(0)) + Double.valueOf(list.get(2))
        );
        this.funcMap.put("-", list ->
                Double.valueOf(list.get(0)) - Double.valueOf(list.get(2))
        );
        this.funcMap.put("*", list ->
                Double.valueOf(list.get(0)) * Double.valueOf(list.get(2))
        );
        this.funcMap.put("/", list ->
                Double.valueOf(list.get(0)) / Double.valueOf(list.get(2))
        );
    }

    public boolean canBeEqualTo24(Integer[] nums) throws InterruptedException {
        this.make(nums, new LinkedList<>(), nums.length, false, Optional.of((number) -> {
            this.calc(number, nums.length - 1);
        }));
        if (!this.stop) {
            System.out.println(String.format("из данного набора чисел невозможно составить выражение, равное %s", this.expected));
        } else {
            System.out.println(resStroka);
        }
        return this.stop;
    }

    private void make(Object[] arr, Deque<Integer> indexes, int tempResaltSize, Boolean selector, Optional<Consumer<LinkedList<String>>> opti) {
        if (!stop) {
            if (indexes.size() == tempResaltSize) {
                LinkedList<String> temp = new LinkedList<>();
                for (Integer i : indexes) {
                    temp.add(String.valueOf(arr[i]));
                }
                if (temp.size() > 1) {
                    opti.get().accept(temp);
                }
                return;
            }
            for (int i = 0; i < arr.length; i++) {
                if (!indexes.contains(i) || selector) {
                    indexes.addLast(i);
                    make(arr, indexes, tempResaltSize, selector, opti);
                    indexes.removeLast();
                }
            }
        }
    }

    private void calc(LinkedList<String> nam, int size) {
        this.make(new String[]{"-", "/", "+", "*"}, new LinkedList<>(), size,
                true, Optional.of((znak) -> {
                    LinkedList<String> nuum = new LinkedList<>(nam);
                    ArrayList<String> arifmetic = new ArrayList<>();
                    Double tempResalt = null;
                    while (!nuum.isEmpty() || !znak.isEmpty()) {
                        if (arifmetic.size() == 0) {
                            tempResalt = strRef(arifmetic, list -> {
                                list.addAll(Arrays.asList(nuum.poll(), znak.poll(), nuum.poll()));
                                this.resStroka.append("(").append(arifmetic.get(0)).append(arifmetic.get(1)).append(arifmetic.get(2)).append(")");
                            });
                        } else {
                            tempResalt = strRef(arifmetic, list -> {
                                list.addAll(Arrays.asList(znak.poll(), nuum.poll()));
                                this.resStroka.insert(0, "(");
                                this.resStroka.append(arifmetic.get(arifmetic.size() - 2)).append(arifmetic.get(arifmetic.size() - 1)).append(")");
                            });
                        }
                    }
                    if (this.expected.equals(tempResalt)) {
                        resStroka.append(" = " + this.expected);
                        this.stop = true;
                    } else {
                        this.resStroka.setLength(0);
                    }
                }));
    }

    private Double strRef(List<String> arifmetic, Consumer<List<String>> fank) {
        fank.accept(arifmetic);
        Double tempResalt = this.funcMap.get(arifmetic.get(1)).apply(arifmetic);
        arifmetic.clear();
        arifmetic.add(tempResalt.toString());
        return tempResalt;
    }
}
