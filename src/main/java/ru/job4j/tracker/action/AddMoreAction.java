package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.store.Store;

import static java.lang.System.out;

public class AddMoreAction implements UserAction {

    private final Output out;

    public AddMoreAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "=== Add more Item ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        for (int i = 0; i < 1000000; i++) {
            tracker.add(new Item("Item" + i));
        }
        out.println("Items successfully added!");
        return true;
    }
}
