package ru.job4j.calculate;
/**
 * @autor Alexandr Kaleganov
 * @version 1
 * @since 11.04.2019
 *
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

class Calc {
    //мапа с функиональными методами, в контрукторе проинициализируем её чтобы знать что делать
    private final HashMap<String, Function<List<String>, Double>> funcMap;
    //искомый результат
    private final Double expected;
    //для контроля очерёдности работы потокв
    private final BlockingDeque<LinkedList<String>> data = new LinkedBlockingDeque<>();
    //все возможные варианты знаков
    private final LinkedList<LinkedList<String>> randomZnak = new LinkedList<>();
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
        //инициализация моей базы всевозможных вариантов символов
        this.make(new String[]{"-", "/", "+", "*"}, new LinkedList<>(), nums.length - 1, this.randomZnak, true);
        Thread consumer = new Consumer(data);
        Thread producter = new Product(nums, data, consumer);
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
    private void make(Object[] arr, Deque<Integer> indexes, int tempResaltSize, Queue<LinkedList<String>> data, Boolean selector) throws InterruptedException, BrokenBarrierException {
        if (stop) {
            throw new InterruptedException();
        }
        if (indexes.size() == tempResaltSize) {
            LinkedList<String> temp = new LinkedList<String>();
            for (Integer i : indexes) {
                temp.add(String.valueOf(arr[i]));
            }
            if (temp.size() > 1) {
                data.offer(temp);
            }
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            if (!indexes.contains(i) || selector) {
                indexes.addLast(i);
                make(arr, indexes, tempResaltSize, data, selector);
                indexes.removeLast();
            }
        }
    }

    /**
     * в метод из потока Consumer  будет прилетать вариант чисел
     * если Double tempResalt  будет равен нашему искомому аргументу,
     * то мы остановим потоки и посмотрим что у нас получилось в  StringBuilder resStroka
     *
     * @param num
     */
    private void calc(LinkedList<String> num) throws InterruptedException {
        for (int i = 0; i < this.randomZnak.size(); i++) {
            Queue<String> tempZnak = new LinkedList<>();
            tempZnak.addAll(randomZnak.get(i));
            LinkedList<String> tempNum = new LinkedList<>();
            tempNum.addAll(num);
            ArrayList<String> arifmetic = new ArrayList<>();
            Double tempResalt = null;
            while (!tempNum.isEmpty() || !tempZnak.isEmpty()) {
                if (arifmetic.size() == 0) {
                    arifmetic.addAll(Arrays.asList(tempNum.poll(), tempZnak.poll(), tempNum.poll()));
                    this.resStroka.append("(" + arifmetic.get(0) + arifmetic.get(1) + arifmetic.get(2) + ")");
                    tempResalt = strRef(arifmetic);
                } else {
                    arifmetic.addAll(Arrays.asList(tempZnak.poll(), tempNum.poll()));
                    this.resStroka.insert(0, "(");
                    this.resStroka.append(arifmetic.get(arifmetic.size() - 2) + arifmetic.get(arifmetic.size() - 1) + ")");
                    tempResalt = strRef(arifmetic);
                }
            }
            if (this.expected.equals(tempResalt)) {
                resStroka.append(" = " + this.expected);
                this.stop = true;
                break;
            } else {
                this.resStroka.setLength(0);
                arifmetic.clear();
            }
        }
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
        private final BlockingDeque<LinkedList<String>> data;
        private final Integer[] nums;
        private final Thread consumer;

        Product(Integer[] nums, BlockingDeque<LinkedList<String>> data, Thread consumer) {
            this.data = data;
            this.nums = nums;
            this.start();
            this.consumer = consumer;
        }

        @Override
        public void run() {
            try {
                make(nums, new LinkedList<>(), nums.length, this.data, false);
            } catch (InterruptedException | BrokenBarrierException ignored) {
            } finally {
                this.consumer.interrupt();
            }
        }
    }

    /**
     * поток будет забирать 1 элемент из очереди и прогонять его по условию
     */
    private class Consumer extends Thread {
        private BlockingDeque<LinkedList<String>> data;

        Consumer(BlockingDeque<LinkedList<String>> data) {
            this.data = data;
            this.start();
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted() || !this.data.isEmpty()) {
                    calc(this.data.take());
                    if (stop) {
                        throw new InterruptedException();
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }
    }
}
