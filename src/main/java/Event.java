import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                "(from: " + from.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
    public String toStringFile() {
        return "E | " + super.toStringFile() + "/from " + from + "/to " + to;
    }


    public static void addEvent(String description, ArrayList<Task> list) throws DukeException {
        String[] event = description.stripTrailing().split("/from |/to ");
        if (event[0].isEmpty()) {
            throw new DukeException("☹ OOPS!!! The description of an Event cannot be empty.");
        }
        if (event.length < 3) {
            throw new DukeException("☹ OOPS!!! Please provide a valid start and end date");
        }

        try {
            LocalDate start = LocalDate.parse(event[1].stripTrailing());
            LocalDate end = LocalDate.parse(event[2].stripTrailing());
            if (start.isAfter(end)) {
                throw new DukeException("☹ OOPS!!! Your start date has to be before your end date!");
            }
            Event newTask = new Event(event[0], start, end);

            list.add(newTask);
            System.out.println(line);
            System.out.println("Got it. I've added the Event:\n\t" + newTask.toString());
            System.out.println("Now you have " + list.size() + " tasks in the list.");
            System.out.println(line);
        } catch (DateTimeParseException e) {
            System.out.println("Your date should be formatted as YYYY-MM-DD");
        }


    }
    public static void addSavedEvent(String description, ArrayList<Task> list, String isMarked) {
        String[] event = description.stripTrailing().split("/from |/to ");
        try {
            Event newTask = new Event(event[0], LocalDate.parse(event[1]), LocalDate.parse(event[2]));
            list.add(newTask);
            newTask.markFromRead(isMarked);
        } catch (DateTimeParseException e) {
            System.out.println("Please enter a valid Date!");
        }

    }

}
