package ru.job4j.generic;

abstract class AbstractStore<T extends Base>  {
    SimpleArray<T> baza = new SimpleArray<T>(10);


    public void add(T model) {
        baza.add(model);
    }


    public boolean replace(String id, T model) {
        int index = formatconverter(id);
        T oldElement = this.baza.getT(index);
        return baza.set(index, model) == oldElement;
    }


    public boolean delete(String id) {
        int index = formatconverter(id);
        T oldElement = this.baza.getT(index);
        return baza.delete(index) == oldElement;
    }


    public T findById(String id) {
        int index = formatconverter(id);
        return baza.getT(index);
    }

    /**
     * метод будет возвращать  int
     *
     * @param id
     * @return
     */
    private int formatconverter(String id) {
        int res = 0;
        try {
            res = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ID необходимо указывать только цифрами от 0 до " + baza.size());
        }
        return res;
    }
}
