package ru.job4j.tracker.modules;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;


/**
 * я переделал на лямбды все методы, которые осуществялют поиск
 * за исключением поиск по имени заявки
 * дело в том что поиск по имени заявки у меня возвращает не конкретную заявку, а список заявок
 * которые содержат в названии запрос СПИСОК - по этому код не стал множить
 */
public class Tracker implements ITracker {
    private ArrayList<Items> items = new ArrayList<>();
    private Random rn = new Random();
    private boolean exitProgramm = true; //пока параметр переменной будет равен шести, программу будет продолжнать работать

    /**
     * планирую сделать так: привыборе в меню цифры 6, у нас вызовется бьект класса
     * Exitprogramm , и его метод  public void execute(Input input, Tracker tracker)
     * этот метод вызовет нашь метод setExitProgramm. который в свою очередь изменит параметр
     * переменно exitProgramm и программа завершит свою работу
     */
    public void setExitPrograpp() {
        this.exitProgramm = false;
    }

    public boolean getExitProgramm() {
        return exitProgramm;
    }

    /**
     * добавление заявок - и мы просто делаем один шаг по элемену++
     *
     * @param item
     * @return
     */
    public Items add(Items item) {
        item.setId(this.generate());
        this.items.add(item);
        return item;
    }

    //метод который будет отвечать за работу функции
    private Items searshItem(int sears, Function<Integer, Items> fanc) {
        Items result = null;
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).getId() == sears) {
                result = fanc.apply(i);
                break;
            }
        }
        return result;
    }

    /**
     * редактирование заявок здесь у нас водит id  заявки и новые данные заявки
     * в методе id  присваивается объекту итем и после мы шагаем по массиву и ищем заявку с таким же id а когда накодим присваиваем этой заявке
     * новые знавения с входящей заявке при этом id  останется преждним
     *
     * @param id
     * @param
     */
    public void replace(int id, Items item) {
        item.setId(id);
        searshItem(id, (i) -> {
            return this.items.set(i, item);
        });
    }

    /**
     * удаление заявок  ну здесь я придумал так - иду по массиву заявок нахоу заявку с нужным id
     * и присваиваю ей значеие null
     *
     * @param id
     */

    public void delete(int id) {
        searshItem(id, i -> {
            int k = i;
            return this.items.remove(k);
        });
    }

    /**
     * получение списка всех заявок - этот метод нам просто возвращает массив заявок
     *
     * @return
     */
    public ArrayList<Items> findAll() {
        return items;
    }

    /**
     * получение списка по имени  - в этом методе я создал новый массив и шёл по старому массиву,
     * находил все заявки с похожим именем, для удобства поиска я спецально сделал чтобы мы могли искать совпадения
     * например если мы ищемвсе заявки в имени которых есть слово "хелп" - он их вернёт
     *
     * @param key
     * @return
     */
    public ArrayList<Items> findByName(String key) {
        ArrayList<Items> res = new ArrayList<>();
        for (Items i : this.items) {
            if (i.getName().contains(key)) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * получение заявки по id находим заявку с нужным id и возвращаем пользвателю, ничего заумного
     *
     * @param id
     * @return
     */
    public Items findById(int id) {
        return searshItem(id, (i) -> this.items.get(i));
    }

    /**
     * генерацию id  я  перенёс в этот класс мне это показалось более логичным
     *
     * @return
     */
    private int generate() {
        int id = rn.nextInt() * 100;
        return id;
    }

    /**
     * Метод добавления комментарий в заявку
     */
    public void addComment(int id, String comments) {
        this.searshItem(id, (i) -> {
                    this.items.get(i).addComment(comments);
                    return this.items.get(i);
                }
        );
    }

}
