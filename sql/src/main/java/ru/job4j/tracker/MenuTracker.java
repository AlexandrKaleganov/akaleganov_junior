package ru.job4j.tracker;
/**
 * меню трекера , кадый класс отвечает за отдельное меню
 */

import ru.job4j.tracker.modules.ITracker;
import ru.job4j.tracker.modules.Items;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * внешний класс
 */
class EditItemsclass extends BaseAction {
    EditItemsclass(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, ITracker tracker) {
        int id = Integer.valueOf(input.inputCommand("Введите id заявки, которую вы хтите изменить"));
        Items items = new Items(input.inputCommand("Введите новое им заявки"), input.inputCommand("Введите новое описание заявки"));
        tracker.replace(id, items);
    }
}

/**
 * основной класс
 */
public class MenuTracker {
    private Input input;
    private ITracker tracker;
    private ArrayList<UserAction> actions = new ArrayList<>();
    private Output output = new OutConsole();

    public MenuTracker(Input input, ITracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public MenuTracker() {
    }

    public ArrayList<UserAction> getActions() {
        return actions;
    }

    public void fillAction() {
        this.actions.add(new AddItem(0, "Add new Item"));
        this.actions.add(new ShouALLitems(1, "Show all items"));
        this.actions.add(new EditItemsclass(2, "Edit item"));
        this.actions.add(new MenuTracker.Deleteitems(3, "Delete item"));
        this.actions.add(new FindByaItemsId(4, "Find item by Id"));
        this.actions.add(new Finditemsbyname(5, "Find items by name"));
        this.actions.add(new AddCommentByItems(6, "Add comment by Items"));
        this.actions.add(new Exitprogramm(7, "Exit Program"));
    }

    public void addAction(UserAction action) {
        this.actions.add(action);
    }

    /**
     * метод нужен чтобы знать количество пунктов меню, он возвращает этот массив в StartUI в поле ange
     *
     * @param
     * @return
     */
    public int[] returnFINALmenu() {
        int[] result = new int[this.actions.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    public void shou(Consumer<String> out) {
        for (UserAction action : this.actions) {
            if (action != null) {
                out.accept(action.info());
            }
        }
    }

    /**
     * внутренние не статичные классы
     */
    private static class AddItem extends BaseAction {
        AddItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            tracker.add(new Items(input.inputCommand("Пожалусто введите имя заявки"), input.inputCommand("Пожалусто введите описание заявки")));
        }
    }

    private class Finditemsbyname extends BaseAction {
        Finditemsbyname(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            for (Items item : tracker.findByName(input.inputCommand("Введите имя заявки"))) {
                if (item != null) {
                    output.outthet(item.toString());
                }
            }
        }
    }

    private class Exitprogramm extends BaseAction {
        Exitprogramm(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            tracker.setExitPrograpp();
        }
    }

    private class ShouALLitems extends BaseAction {
        ShouALLitems(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            for (Items items : tracker.findAll()) {
                if (items != null) {
                    output.outthet(items.toString());
                }
            }
        }
    }

    private class FindByaItemsId extends BaseAction {
        FindByaItemsId(int key, String  name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            output.outthet((tracker.findById(Integer.valueOf(input.inputCommand("Введите id заявки, которую необходимо найти")))).toString());
        }
    }

    /**
     * внутренние статичные классы
     */
    private static class Deleteitems extends BaseAction {
        Deleteitems(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            tracker.delete(Integer.valueOf(input.inputCommand("Введите id заявки, которую необходимо удалить")));
        }
    }

    private class AddCommentByItems extends BaseAction {
        AddCommentByItems(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            tracker.addComment(Integer.valueOf(input.inputCommand("Введите id заявки, которую необходимо найти")), input.inputCommand("Введите комментарий к заявке"));
        }
    }

}

