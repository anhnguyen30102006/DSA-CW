package org.example;


import java.time.LocalDate;


public class FoundItem extends BaseItem {
    private String finderName;     // not null
    private String foundLocation;  // nullable


    public FoundItem(String itemID, String description, String brand,
                     String color, String condition, LocalDate date,
                     String finderName, String foundLocation) {
        super(itemID, description, brand, color, condition, date);


        if (finderName == null || finderName.isBlank()) {
            throw new IllegalArgumentException("Finder name cannot be null or empty");
        }
        this.finderName = finderName;
        this.foundLocation = foundLocation; // nullable
    }


// getFoundLocation,getFinderName methods are used in future extensions
    public String getFoundLocation() {
        return foundLocation;
    }

    public String getFinderName() {
        return finderName;
    }


    @Override
    public String getDetails() {
        return super.getDetails() +
                "\nFinder Name: " + finderName +
                "\nFound Location: " + (foundLocation != null ? foundLocation : "N/A");
    }
}

