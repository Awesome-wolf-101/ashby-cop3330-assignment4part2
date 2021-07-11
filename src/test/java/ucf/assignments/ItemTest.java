/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joshua Ashby
 */
package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void isCompleted() {
        //make a new item object
        Item item = new Item("2020-03-04", "Eat tacos");
        //make a boolean variable to see if the item is incomplete
        boolean actual = item.isCompleted().equals("incomplete");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void setCompleted() {
        //make a new item object
        Item item = new Item("2020-03-04", "Eat tacos");
        //set the item to complete
        item.setCompleted("complete");
        //make a boolean variable to see if the item is complete
        boolean actual = item.isCompleted().equals("complete");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void getDueDate() {
        //make a new item object
        Item item = new Item("2020-03-04", "Eat tacos");
        //make a boolean variable to see if the item is duedate is returned
        boolean actual = item.getDueDate().equals("2020-03-04");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void setDueDate() {
        //make a new item object
        Item item = new Item("2020-03-04", "Eat tacos");
        //set the item to a new due date
        item.setDueDate("2021-09-13");
        //make a boolean variable to see if the item has the new due date
        boolean actual = item.getDueDate().equals("2021-09-13");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void getDescription() {
        //make a new item object
        Item item = new Item("2020-03-04", "Eat tacos");
        //make a boolean variable to see if the item is description is returned
        boolean actual = item.getDescription().equals("Eat tacos");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void setDescription() {
        //make a new item object
        Item item = new Item("2020-03-04", "Eat tacos");
        //set the item to a new description
        item.setDescription("Yogurt eating");
        //make a boolean variable to see if the item has the new description
        boolean actual = item.getDescription().equals("Yogurt eating");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }
}