package ru.job4j.calculate;
/**
 * попробуем сделать универсальный класс для подбора выражений
 * реализация будет построена на очереди, одна из них будет блокирующая очередь
 * что -то типо ProductConsumer, в начале когда в метод поступит массив - мы глянем на его размер
 * и заполним всевозможными вариантами random_znak размер каждой вложенной очереди будет
 * на 1 еньше и они будут повторяться чтобы +++ //*  будут все возможные вырианты
 * далее у нас стартует два потока  Producter  и  Consumer
 * Producter будет генерировать вариант из нашего массива и добавлять в блокирующую очередь,
 * когда в ней появляется первый вариант то поток остановится и будет джать пока Consumer
 * не прогонит этот вариант по всем знакам варианта в случае удачного совпадения
 * волотайл переменная изменится и создаст интеррапт исключение в потоках,
 * что заставит их прерваться далее нам надо будет обрабатывать нашь StringBuilder resStroka - и если
 * её рамер равен 0 то выведем сообщение что решение отсутствует, если размер больше 0 то
 * обработаем пока не придумал как
 */

import java.util.*;
import java.util.concurrent.*;

class Calc {
    private Double arg;

    private final BlockingDeque<LinkedList<String>> data = new LinkedBlockingDeque<>();

    private LinkedList<LinkedList<String>> random_znak = new LinkedList<>();
    private final StringBuilder resStroka = new StringBuilder();
    private volatile boolean stop = false;

    public boolean canBeEqualTo24(Integer[] nums, Double arg) {
        this.arg = arg;
        //инициализация моей базы всевозможных вариантов символов
        this.make(new String[]{"+", "-", "/", "*"}, new LinkedList<>(), nums.length - 1, this.random_znak, true);
        System.out.println(this.random_znak);

        return true;
    }

    /**
     * метод который будет генерировать всевозможные варианты наборов чисел, они не должны почторяться
     * знаки могут повторяться для переключения генератора используем булеан селектор
     *
     * @param arr
     * @param indexes
     * @param expectedSize
     */
    private void make(Object[] arr, Deque<Integer> indexes, int expectedSize, Queue<LinkedList<String>> data, Boolean selector) {
        try {
            while (this.data.size() == 1) {
                if (stop) {
                    throw new InterruptedException();
                }
                wait();
            }
            if (indexes.size() == expectedSize) {
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
                    make(arr, indexes, expectedSize, data, selector);
                    indexes.removeLast();
                }
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    /**
     * если Double expected  будет равен нашему искомому аргументу,
     * то мы остановим потоки и посмотрим что у нас получилось в  StringBuilder resStroka
     * @param num
     */
    private void calc(LinkedList<String> num) {
        for (int i = 0; i < this.random_znak.size(); i++) {
            Queue<String> tem_znak = new LinkedList<>();
            tem_znak.addAll(random_znak.get(i));
            LinkedList<String> tem_num = new LinkedList<>();
            tem_num.addAll(num);
            StringBuilder temp = new StringBuilder();
            Double expected = null;
            while (!tem_num.isEmpty() || tem_znak.isEmpty()) {
                if (temp.length() == 0) {
                    temp.append(tem_num.poll() + tem_znak.poll() + tem_num.poll());
                    expected = strRef(temp);
                } else {
                    temp.append(tem_znak.poll() + tem_num.poll());
                    expected = strRef(temp);
                }
            }
            if (expected == this.arg) {
                this.stop = true;
                break;
            }
        }
    }

    /**
     * рефактоиринг кода подсчёта чтобы исключить повторения
     * @param temp
     * @return
     */
    private Double strRef(StringBuilder temp) {
        Double expected = Double.valueOf(temp.toString());
        resStroka.append(temp.toString());
        temp.setLength(0);
        temp.append(expected.toString());
        return expected;
    }

    /**
     * поток будет добавлять 1 в очередь и будет переходить в режим ожидания
     */
    private class Product implements Runnable {
        private final BlockingDeque<LinkedList<String>> data;
        private final Integer[] nums;

        Product(Integer[] nums, BlockingDeque<LinkedList<String>> data) {
            this.data = data;
            this.nums = nums;
        }

        @Override
        public void run() {
            make(nums, new LinkedList<>(), nums.length, this.data, false);
        }
    }

    /**
     * поток будет добавлять 1 элемент но будет ждать у барьера пока не добавитсяпервый элемент
     */
    private class Consumer implements Runnable {
        private BlockingDeque<LinkedList<String>> data;

        Consumer(BlockingDeque<LinkedList<String>> data) {
            this.data = data;

        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    calc(this.data.take());
                    if (stop) {
                        throw new InterruptedException();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
