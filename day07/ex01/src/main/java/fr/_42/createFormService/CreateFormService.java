package fr._42.createFormService;
import fr._42.annotations.HtmlInput;
import fr._42.exeptions.CreateHTMLExeption;
import fr._42.annotations.HtmlForm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

public class CreateFormService {
    Object formClass;
    StringBuilder htmlBuilder = new StringBuilder();
    String fileName;
    public CreateFormService(Object fromClass) throws CreateHTMLExeption  {
        if(!fromClass.getClass().isAnnotationPresent(HtmlForm.class))
            throw new CreateHTMLExeption("The class is not annotated with HtmlForm");
        System.out.println("class is annotated with HtmlForm");
        this.formClass = fromClass;
    }

    public void  create() throws CreateHTMLExeption {
        this.createHtmlForm();
        this.saveToFile();
    }

    private void createHtmlForm() throws CreateHTMLExeption {
        HtmlForm a = this.formClass.getClass().getAnnotation(HtmlForm.class);
        if(a == null)
            throw new CreateHTMLExeption("The class is not annotated with HtmlForm");
        this.fileName = a.fileName();
        this.htmlBuilder.append("<form action=\"")
                .append(a.action())
                .append("\" method=\"")
                .append(a.method())
                .append("\">\n");
        this.addInputs();
        this.htmlBuilder.append("</form>\n");
        System.out.println(this.htmlBuilder.toString());
    }

    private void addInputs(){
        Field[] fields = this.formClass.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(HtmlInput.class)) {
                HtmlInput input = field.getAnnotation(HtmlInput.class);
                this.htmlBuilder.append("<input type=\"")
                        .append(input.type())
                        .append("\" name=\"")
                        .append(input.name())
                        .append("\" placeholder=\"")
                        .append(input.placeholder())
                        .append("\">\n");
            }
        }
    }

    private void saveToFile() throws CreateHTMLExeption {
        if (this.fileName == null || this.fileName.isEmpty())
            throw new CreateHTMLExeption("File name is not specified in the HtmlForm annotation");

        File file = new File(this.fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(this.htmlBuilder.toString());
            System.out.println("HTML form saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new CreateHTMLExeption("Error writing to file: " + e.getMessage());
        }
    }
}
