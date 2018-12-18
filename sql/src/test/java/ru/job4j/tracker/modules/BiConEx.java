package ru.job4j.tracker.modules;

public interface BiConEx<R, K> {

   void submit(R r, K k) throws Exception;
}
