package fr._42.classes;

public class Car  {
    private String brand;
    private String model;
    private int price;
    
    public Car(){
        this.brand = "default";
        this.model = "default";
        this.price = 0;
    }
    
    public Car(String brand, String model, int price){
        this.brand = brand;
        this.model = model;
        this.price = price;
    }


    public int AddDiscount(int percentage){
        this.price = this.price - (this.price * percentage / 100);
        return this.price;
    }

    public void test(double b){
        System.out.println(b);
    }


    @Override
    public String toString() {
        return "Car[" +
                "brand='" + brand + ", "  +
                ", model='" + model + ", "  +
                ", price=" + price +
                ']';
    }
}
