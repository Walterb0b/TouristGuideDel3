package com.touristguidedel3.touristguidedel3.model;

import java.util.List;

public class TouristAttraction {
    private String name;
    private String description;
    private Cities city;
    private List<Tags> tags;
    private double price;
    private Long id;

    public TouristAttraction(String name, String description, Cities city, List<Tags> tags, double price) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
        this.price = price;
    }

    public TouristAttraction(){}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
