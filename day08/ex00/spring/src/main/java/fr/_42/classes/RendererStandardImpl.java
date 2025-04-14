package fr._42.classes;

import fr._42.intefaces.PreProcessor;
import fr._42.intefaces.Renderer;

public class RendererStandardImpl implements Renderer {
    private final PreProcessor preProcessor;

    public RendererStandardImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }
    @Override
    public void render(String message){
        String prepr = preProcessor.preProcess(message);
        System.out.println(prepr);
    }
}
