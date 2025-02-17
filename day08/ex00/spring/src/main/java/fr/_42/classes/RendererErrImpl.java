package fr._42.classes;

import fr._42.intefaces.PreProcessor;
import fr._42.intefaces.Renderer;

public class RendererErrImpl implements Renderer {
    private final PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }
    @Override
    public void render(String message) {
        preProcessor.preProcess(message);
        System.err.println(message);
    }
}
