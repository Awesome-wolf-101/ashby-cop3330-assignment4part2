/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joshua Ashby
 */
package ucf.assignments;

public class Item {
//these three primary attributes describe eveything about an item
    String Description = "";
    String DueDate = "";
    String completed = "incomplete";
    //constructor to initialize an instance of an object
    public Item(String DueDate, String Description)
    {
        this.DueDate = DueDate;
        this.Description = Description;
    }

    //getter method for completed variable
    public String isCompleted() {
        return completed;
    }

    //setter method for completed variable
    public void setCompleted(String completed) {
        this.completed = completed;
    }

    //getter method for DueDate variable
    public String getDueDate() {
        return DueDate;
    }

    //setter method for DueDate variable
    public void setDueDate(String DueDate) {
        this.DueDate = DueDate;
    }

    //getter method for Description variable
    public String getDescription() {
        return Description;
    }

    //setter method for Description variable
    public void setDescription(String Description) {
        this.Description = Description;
    }
}
