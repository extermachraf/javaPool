package fr._42.reflection;

import fr._42.classes.Car;
import fr._42.classes.User;
import fr._42.exeptions.ReflectExceptions;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReflectionService {
    BufferedReader reader;
    final Map<String, Object> classes = new HashMap<>();


    public ReflectionService(BufferedReader reader) {
        this.reader = reader;
        classes.put("user", new User());
        classes.put("car", new Car());
    }
    public void invokeService() throws ReflectExceptions, IOException, IllegalAccessException, NoSuchFieldException
        , InvocationTargetException {
        this.displayClasses();
        Object classe = this.selectClass();
        this.printFieldsAndMethods(classe);
        this.createObject(classe);
        this.changeFields(classe);
        this.invokeMethode(classe);
    }

    private void displayClasses(){
        System.out.println("Classes");
        for(String key : classes.keySet()){
            System.out.println(key);
        }
        System.out.println("---------------------");
    }
    private Object selectClass() throws IOException, ReflectExceptions {
        String selectedClass = reader.readLine().toLowerCase().trim();
        if(!classes.containsKey(selectedClass)){
            throw new ReflectExceptions("Class not found");
        }
        return classes.get(selectedClass);
    }
    private String printFunctionParameters(Class<?>[] array){
        StringBuilder sb = new StringBuilder();

        sb.append("(");
        for(int i = 0; i < array.length; i++){
            sb.append(array[i].getName());
            if(i < array.length - 1){
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private void printFieldsAndMethods(Object classe){
        Field[] fields = classe.getClass().getDeclaredFields();
        Method[] methods = classe.getClass().getMethods();

        System.out.println("fields");
        for(Field field : fields){
            if(field.getDeclaringClass() != Object.class){
                System.out.println("     " + field.getType().getSimpleName() + " " + field.getName());
            }
        }

        System.out.println("methods");
        for(Method method : methods){
            if(method.getDeclaringClass() != Object.class){
                System.out.println("     " + method.getReturnType().getSimpleName() + " " +
                        method.getName() +  this.printFunctionParameters( method.getParameterTypes()));
            }
        }
    }

    private void  createObject(Object classe) throws IOException, IllegalAccessException{
        //create instance of the class
        System.out.println("----------------------");
        System.out.println("Let's create an object.Let's create an object.");
        Field[] fields = classe.getClass().getDeclaredFields();
        for(Field field : fields){
            if(field.getDeclaringClass() != Object.class){
                System.out.println(field.getName());
                String data = reader.readLine().trim();
                field.setAccessible(true);
                Object convertedValue = convertToFieldType(field.getType(), data);
                field.set(classe, convertedValue);
            }
        }
        System.out.println("Object created: " + classe);
    }

    private Object convertToFieldType(Class<?> fieldType, String inputValue) {
        try {
            if (fieldType.isPrimitive()) {
                if (fieldType == int.class) {
                    return Integer.parseInt(inputValue);
                } else if (fieldType == double.class) {
                    return Double.parseDouble(inputValue);
                } else if (fieldType == boolean.class) {
                    return Boolean.parseBoolean(inputValue);
                } else if (fieldType == long.class) {
                    return Long.parseLong(inputValue);
                } else if (fieldType == char.class) {
                    return inputValue.charAt(0);  // Assumes a single character input
                } else if (fieldType == float.class) {
                    return Float.parseFloat(inputValue);
                } else if (fieldType == short.class) {
                    return Short.parseShort(inputValue);
                } else if (fieldType == byte.class) {
                    return Byte.parseByte(inputValue);
                }
            }
            Method valueOfMethod = fieldType.getMethod("valueOf", String.class);
            return valueOfMethod.invoke(null, inputValue);

        } catch (Exception e) {
            return inputValue;
        }
    }


    private void changeFields(Object classe) throws IOException, NoSuchFieldException, IllegalAccessException{
        System.out.println("--------------------");
        System.out.println("Enter name of the field for changing:\n-->");
        String fieldName = reader.readLine().trim();
        Field field =  classe.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        System.out.println("enter the new Value");
        String updatedValue = reader.readLine().trim();
        field.set(classe, convertToFieldType(field.getType(), updatedValue));
        System.out.println("Object Updated: "+ classe);
    }

    private void invokeMethode(Object classe) throws IOException, ReflectExceptions, InvocationTargetException, IllegalAccessException {

        System.out.println("--------------------");
        System.out.print("Enter name of the method for call:\n-->");
        String calledMethode = reader.readLine().trim();
        if(!methodeIsValid(calledMethode))
            throw new ReflectExceptions("Method not found");
        Method[] methods = classe.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(method.getDeclaringClass() != Object.class){
                if(method.getName().equals(getMethodeNameFromInput(calledMethode))){
                    Parameter[] parameters =  method.getParameters();
                    Object[] parameterValues = new Object[parameters.length];
                    for(int i = 0; i < parameters.length; i++){
                        System.out.println(parameters[i].getType().getSimpleName() + " " + parameters[i].getName());
                        String inputValue = reader.readLine().trim();
                        parameterValues[i] = convertToFieldType(parameters[i].getType(), inputValue);
                    }
                    method.setAccessible(true);
                    Object result = method.invoke(classe, parameterValues);
                    if(result != null)
                        System.out.println("Methode returned:\n"+ result);
                }
            }
        }
    }

    private String getMethodeNameFromInput(String data){
        StringBuilder builder = new StringBuilder();
        for(char c : data.toCharArray()){
            if(c == '(')
                break;
            builder.append(c);
        }
        return builder.toString();
    }
    

    private boolean methodeIsValid(String inputValue) throws ReflectExceptions {
        String regex = "^[a-zA-Z_][a-zA-Z0-9_]*\\s*\\((\\s*(byte|short|" +
                "int|long|float|double|char|boolean|String|Object)\\s*" +
                "(,\\s*(byte|short|int|long|float|double|char|boolean|String|Object)\\s*)*)?\\)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputValue);
        return matcher.matches();
    }

    //helpers

}
