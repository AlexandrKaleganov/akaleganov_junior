package ru.job4j.forum.combomass;
/**
 * реализовал на основе итератора, только следующая итерация будет выдавать новый массив позицый
 * также будет хранить список с объектами Combo у каждой позиции своё значение и свой флаг
 */

import java.util.*;

public class Itr implements Iterator<int[]> {

    private final Container[] comboNext;  // будет содержать   следующую комбинацию
    private int[] comboLast; // будет содержать последнюю отправленную комбинацию
    private int[] isHashNextList; //содержит финальную возможную комбинацию
    private boolean ishashnext = true;

    Itr(int[] list) {
        comboNext = new Container[list.length];
        comboLast = new int[list.length];
        isHashNextList = new int[list.length];
        isHashlistNexstReload();
    }

    private void isHashlistNexstReload() {
        for (int i = comboNext.length - 1, j = 0; i >= 0; j++, i--) {
            comboNext[i] = new Container(i);
            isHashNextList[j] = i;
        }
    }

    /**
     * метод проверяет есть ли следущий элемент
     * @return
     */
    @Override
    public boolean hasNext() {
        return ishashnext;
    }


    @Override
    public int[] next() {
        boolean exit = false;
        boolean flagRevers = false;
        if (!hasNext()) {
            throw new NoSuchElementException();
        } else {
            for (int i = 0; i < comboNext.length; i++) {
                comboLast[i] = comboNext[i].getValue();
            }
            int n = comboNext.length - 1;
            for (int i = 0; i < comboLast.length; i++) {
                if (isHashNextList[i] == comboLast[i]) {
                    ishashnext = false;
                } else {
                    ishashnext = true;
                    break;
                }
            }
            while (!exit && ishashnext) {
                if (comboNext[n].getValue() < comboNext.length - 1) {
                    if (!flagRevers) {
                        comboNext[n].incrementValue();
                    } else {
                        n++;
                        flagRevers = false;
                    }
                } else {
                    if (!flagRevers) {
                        comboNext[n].zeroValue();
                        n--;
                        comboNext[n].setFlag(false);
                        continue;
                    } else {
                        n++;
                        flagRevers = false;
                    }
                }
                if (!comboNext[n].isFlag()) {
                    for (int i = 0; i < n; i++) {
                        if (comboNext[i].getValue() == comboNext[n].getValue()) {
                            comboNext[n].setFlag(false);
                            break;
                        } else {
                            comboNext[n].setFlag(true);
                        }
                    }
                }
                if (comboNext[n].isFlag() || n == 0) {
                    flagRevers = true;
                    comboNext[n].setFlag(true);
                }
                if (comboNext[n].isFlag() && n >= comboNext.length - 1) {
                    exit = true;
                }
            }
        }
        return comboLast;
    }
}
