package ru.job4j.calculate;
/**
 * @autor Alexandr Kaleganov
 * @version 1
 * @since 11.04.2019
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

class Calc {
    //мапа с функиональными методами, в контрукторе проинициализируем её чтобы знать что делать
    private final HashMap<String, Function<List<String>, Double>> funcMap;
    //искомый результат
    private final Double expected;
    //блокирующаяя очередь туда потом Producter будет генерировать новые варианты чисел
    private final LinkedBlockingDeque<LinkedList<String>> data = new LinkedBlockingDeque<>();
    //переменная будет хранить результат
    private final StringBuilder resStroka = new StringBuilder();
    //для остановки потоков
    private volatile boolean stop = false;

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

    public boolean canBeEqualTo24(Integer[] nums) throws InterruptedException, BrokenBarrierException {
        Thread consumer = new Cons(this.data, nums.length - 1);
        Thread producter = new Product(nums, this.data, consumer);
        producter.join();
        consumer.join();
        if (!this.stop) {
            System.out.println(String.format("из данного набора чисел невозможно составить выражение, равное %s", this.expected));
        } else {
            System.out.println(resStroka);
        }
        return this.stop;
    }

    /**
     * метод который будет генерировать всевозможные варианты наборов чисел, они не должны повторяться
     * знаки могут повторяться для переключения генератора используем булеан селектор
     *
     * @param arr
     * @param indexes
     * @param tempResaltSize
     */
    private void make(Object[] arr, Deque<Integer> indexes, int tempResaltSize,
                      LinkedBlockingDeque<LinkedList<String>> lists, Boolean selector,
                      Optional<BiConsumer<LinkedList<String>, LinkedList<String>>> opti) throws InterruptedException {
        if (stop) {
            throw new InterruptedException();
        }
        if (indexes.size() == tempResaltSize) {
            LinkedList<String> temp = new LinkedList<>();
            for (Integer i : indexes) {
                temp.add(String.valueOf(arr[i]));
            }
            if (temp.size() > 1 && !selector) {
                lists.offer(temp);
            } else if (temp.size() > 1 && selector) {
                opti.get().accept(new LinkedList<>(lists.getLast()), temp);
            }
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            if (!indexes.contains(i) || selector) {
                indexes.addLast(i);
                make(arr, indexes, tempResaltSize, lists, selector, opti);
                indexes.removeLast();
            }
        }
    }

    /**
     * в метод из потока Consumer  будет прилетать вариант чисел
     * если Double tempResalt  будет равен нашему искомому аргументу,
     * то мы остановим потоки и посмотрим что у нас получилось в  StringBuilder resStroka
     *
     * @param nam, size
     */
    private void calc(LinkedList<String> nam, Integer size) throws InterruptedException {
        this.make(new String[]{"-", "/", "+", "*"}, new LinkedList<>(), size,
                new LinkedBlockingDeque<>(new LinkedList<>(Collections.singleton(nam))), true, Optional.of((nuum, znak) -> {
                    ArrayList<String> arifmetic = new ArrayList<>();
                    Double tempResalt = null;
                    while (!nuum.isEmpty() || !znak.isEmpty()) {
                        if (arifmetic.size() == 0) {
                            arifmetic.addAll(Arrays.asList(nuum.poll(), znak.poll(), nuum.poll()));
                            this.resStroka.append("(").append(arifmetic.get(0)).append(arifmetic.get(1)).append(arifmetic.get(2)).append(")");
                            tempResalt = strRef(arifmetic);
                        } else {
                            arifmetic.addAll(Arrays.asList(znak.poll(), nuum.poll()));
                            this.resStroka.insert(0, "(");
                            this.resStroka.append(arifmetic.get(arifmetic.size() - 2)).append(arifmetic.get(arifmetic.size() - 1)).append(")");
                            tempResalt = strRef(arifmetic);
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

    /**
     * рефактоиринг кода подсчёта чтобы исключить повторения
     *
     * @param arifmetic
     * @return
     */
    private Double strRef(List<String> arifmetic) {
        Double tempResalt = this.funcMap.get(arifmetic.get(1)).apply(arifmetic);
        arifmetic.clear();
        arifmetic.add(tempResalt.toString());
        return tempResalt;
    }

    /**
     * поток будет добавлять 1 в очередь
     */
    private class Product extends Thread {
        private final LinkedBlockingDeque<LinkedList<String>> data;
        private final Integer[] nums;
        private final Thread consumer;

        Product(Integer[] nums, LinkedBlockingDeque<LinkedList<String>> data, Thread consumer) {
            this.data = data;
            this.nums = nums;
            this.start();
            this.consumer = consumer;
        }

        @Override
        public void run() {
            try {
                make(nums, new LinkedList<>(), nums.length, this.data, false, Optional.empty());
            } catch (InterruptedException ignored) {
            } finally {
                this.consumer.interrupt();
            }
        }
    }

    /**
     * поток будет забирать 1 элемент из очереди и прогонять его по условию
     */
    private class Cons extends Thread {
        private LinkedBlockingDeque<LinkedList<String>> data;
        private final Integer size;

        Cons(LinkedBlockingDeque<LinkedList<String>> data, Integer size) {
            this.data = data;
            this.size = size;
            this.start();
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted() || !this.data.isEmpty()) {
                    calc(this.data.take(), size);
                    if (stop) {
                        throw new InterruptedException();
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }
    }
}
