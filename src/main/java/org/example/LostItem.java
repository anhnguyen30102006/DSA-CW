package org.example;

import java.time.LocalDate;

public class LostItem extends BaseItem {
    private String ownerName;      // not null
    private String lostLocation;   // nullable
    private boolean isResolved = false;

    public boolean isResolved() {
        return isResolved;
    }

    public void markAsResolved() {
        this.isResolved = true;
    }

    public LostItem(String itemID, String description, String brand,
                    String color, String condition, LocalDate date,
                    String ownerName, String lostLocation) {
        super(itemID, description, brand, color, condition, date);

        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be null or empty");
        }
        this.ownerName = ownerName;
        this.lostLocation = lostLocation; // nullable
    }


// getLostLocation,getOwnerName methods are used in future extensions
    public String getLostLocation() {
        return lostLocation;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public String getDetails() {
        String status = "Unclaimed";
        if (this.isResolved) {
            status = "Resolved";
        }

        return "ID: " + itemID +
                "\nDescription: " + description +
                "\nBrand: " + (brand != null ? brand : "N/A") +
                "\nColor: " + (color != null ? color : "N/A") +
                "\nCondition: " + (condition != null ? condition : "N/A") +
                "\nDate: " + date +
                "\nOwner Name: " + ownerName +
                "\nLost Location: " + (lostLocation != null ? lostLocation : "N/A") +
                "\nStatus: " + status;
    }

}

