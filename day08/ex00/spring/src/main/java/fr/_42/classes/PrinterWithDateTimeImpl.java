package fr._42.classes;

import fr._42.intefaces.Printer;
import fr._42.intefaces.Renderer;

import java.time.LocalDate;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer render;

    public PrinterWithDateTimeImpl(Renderer render) {
        this.render = render;
    }
    @Override
    public void displayMessage(String message) {
        LocalDate date = LocalDate.now();
        render.render(date + " " + message);
    }
}
