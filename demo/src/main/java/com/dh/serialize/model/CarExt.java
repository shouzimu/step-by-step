package com.dh.serialize.model;

import java.io.*;

public class CarExt implements Externalizable {
    private static final Long serialVersionUID = 2L;

    private String color;

    private String brand;

    private double price;


    public CarExt() {
    }

    public CarExt(String brand, String color, double price) {
        this.brand = brand;
        this.color = color;
        this.price = price;
    }

    @Override
    public String toString() {
        return "CarExt{" +
            "brand='" + brand + '\'' +
            ", color='" + color + '\'' +
            ", price=" + price +
            '}';
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(brand);
        out.writeObject(color);
        out.writeDouble(price);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        brand =(String) in.readObject();
        color =(String) in.readObject();
        price = in.readDouble();
    }
}
