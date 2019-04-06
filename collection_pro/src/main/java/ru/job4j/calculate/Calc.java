package ru.job4j.calculate;
/**
 * попробуем сделать универсальный класс для подбора выражений
 * реализация будет построена на очереди, одна из них будет блокирующая очередь
 * что -то типо ProductConsumer, в начале когда в метод поступит массив - мы глянем на его размер
 * и заполним всевозможными вариантами randomZnak размер каждой вложенной очереди будет
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
import java.util.function.Function;

class Calc {

    private final HashMap<String, Function<List<String>, Double>> funcMap;
    private final Double expected;
    private final CyclicBarrier barrier = new CyclicBarrier(2);
    private final BlockingDeque<LinkedList<String>> data = new LinkedBlockingDeque<>();
    private final LinkedList<LinkedList<String>> randomZnak = new LinkedList<>();
    private final StringBuilder resStroka = new StringBuilder();
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
        Thread producter = new Product(nums, data);
        Thread consumer = new Consumer(data);
        producter.start();
        consumer.start();
        producter.join();
        consumer.join();
        if (this.resStroka.length() > 0) {
            System.out.println(resStroka);
            return true;
        } else {
            System.out.println(String.format("из данного набора чисел невозможно составить выражение, равное %s", this.expected));
            return false;
        }
    }

    /**
     * метод который будет генерировать всевозможные варианты наборов чисел, они не должны почторяться
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
                if (!selector) {
                    barrier.await();
                }
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
        try {
            this.barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new InterruptedException();
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
     * поток будет добавлять 1 в очередь и будет переходить в режим ожидания
     */
    private class Product extends Thread {
        private final BlockingDeque<LinkedList<String>> data;
        private final Integer[] nums;

        Product(Integer[] nums, BlockingDeque<LinkedList<String>> data) {
            this.data = data;
            this.nums = nums;
            this.setName("Producter");
        }

        @Override
        public void run() {
            try {
                make(nums, new LinkedList<>(), nums.length, this.data, false);
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + "  завершил свою работу");
            }
            System.out.println(Thread.currentThread().getName() + "  завершил свою работу");
            stop = true;
        }
    }

    /**
     * поток будет добавлять 1 элемент но будет ждать у барьера пока не добавитсяпервый элемент
     */
    private class Consumer extends Thread {
        private BlockingDeque<LinkedList<String>> data;

        Consumer(BlockingDeque<LinkedList<String>> data) {
            this.data = data;
            this.setName("Consumer");
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    if (stop) {
                        throw new InterruptedException();
                    }
                    if (!this.data.isEmpty()) {
                        calc(this.data.take());
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(Thread.currentThread().getName() + "  завершил свою работу");
        }
    }
}
