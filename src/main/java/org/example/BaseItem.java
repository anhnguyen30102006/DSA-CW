package org.example;


import java.time.LocalDate;


public abstract class BaseItem {
    // ===== Fields =====
    protected String itemID;           // not null
    protected String description;      // not null
    protected String brand;             // nullable
    protected String color;             // nullable
    protected String condition;         // nullable
    protected LocalDate date;           // not null
    protected boolean claimed;          // not null, default false


    // ===== Constructor =====
    public BaseItem(String itemID, String description, String brand,
                    String color, String condition, LocalDate date) {
        if (itemID == null || description == null || date == null) {
            throw new IllegalArgumentException("itemID, description, and date cannot be null");
        }
        this.itemID = itemID;
        this.description = description;
        this.brand = brand;         // nullable
        this.color = color;         // nullable
        this.condition = condition; // nullable
        this.date = date;
        this.claimed = false;       // default
    }


    // ===== Getters =====
    public String getItemID() {
        return itemID;
    }


    public boolean isClaimed() {
        return claimed;
    }


    public void markAsClaimed() {
        this.claimed = true;
    }


    public String getDetails() {
        return "ID: " + itemID +
                "\nDescription: " + description +
                "\nBrand: " + (brand != null ? brand : "N/A") +
                "\nColor: " + (color != null ? color : "N/A") +
                "\nCondition: " + (condition != null ? condition : "N/A") +
                "\nDate: " + date +
                "\nClaimed: " + claimed;
    }


    public boolean matchesKeyword(String keyword) {
        if (keyword == null) return false;
        keyword = keyword.toLowerCase();
        return description.toLowerCase().contains(keyword) ||
                (brand != null && brand.toLowerCase().contains(keyword)) ||
                (color != null && color.toLowerCase().contains(keyword)) ||
                (condition != null && condition.toLowerCase().contains(keyword));
    }


    public LocalDate getDate() {
        return date;
    }

// getBrand,getColor,getCondition methods are used in future extensions
    public String getBrand() {
        return brand;
    }


    public String getColor() {
        return color;
    }


    public String getCondition() {
        return condition;
    }
}
