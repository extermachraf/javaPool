package fr._42.classes;

import fr._42.intefaces.Printer;
import fr._42.intefaces.Renderer;

public class PrinterWithPrefixImpl implements Printer {
    private String prefix;
    Renderer render;


    public PrinterWithPrefixImpl(Renderer render) {
        this.render = render;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    @Override
    public void displayMessage(String message) {
        render.render(prefix + " " + message);
    }
}
