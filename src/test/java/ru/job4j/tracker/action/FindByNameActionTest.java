package ru.job4j.tracker.action;

import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;
import ru.job4j.tracker.store.MemTracker;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindByNameActionTest {


    @Test
    public void execute() {
        Output out = new StubOutput();
        MemTracker tracker = new MemTracker();
        tracker.add(new Item("Name"));
        String name = "Name";
        FindByNameAction rep = new FindByNameAction(out);
        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn(name);
        rep.execute(input, tracker);
        assertThat(tracker.findAll().get(0).getName(), is(name));
    }

    @Test
    public void notExecute() {
        Output out = new StubOutput();
        MemTracker tracker = new MemTracker();
        tracker.add(new Item("New item"));
        FindByNameAction rep = new FindByNameAction(out);
        Input input = mock(Input.class);
        rep.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is("Wrong name!" + ln));
    }
}