package fr._42.classes;

import fr._42.intefaces.PreProcessor;

public class PreProcessorToLowerImpl implements PreProcessor {
    @Override
    public  String preProcess(String input) {
        return input.toLowerCase();
    }
}
