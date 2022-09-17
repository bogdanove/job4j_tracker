package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.store.Store;

import static java.lang.System.out;

public class DeleteAllAction implements UserAction {

    private final Output out;

    public DeleteAllAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "=== Delete All Item ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        var list = tracker.findAll();
        list.forEach(item -> tracker.delete(item.getId()));
        out.println("Items successfully deleted!");
        return true;
    }
}
