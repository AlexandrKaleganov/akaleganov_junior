package ru.job4j.blocklinkedlist;

import static org.hamcrest.Matchers.is;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ProductConsumerTest {
    @Test
    public void whennqueueProductandConsummer() throws InterruptedException {


            final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
            final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
            Thread consumer = new Thread(
                    () -> {
                        while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                            try {
                                buffer.add(queue.poll());
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            );
            Thread producer = new Thread(
                    () -> {
                        IntStream.range(0, 5).forEach(
                                queue::offer
                        );
                    }
            );
            producer.start();
            consumer.start();
            producer.join();
            consumer.interrupt();
            consumer.join();
            assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}