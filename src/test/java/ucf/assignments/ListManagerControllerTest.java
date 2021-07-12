/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joshua Ashby
 */
package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

class ListManagerControllerTest  {


    //test if an item can be added to the list (requirement #4)
    @Test
    void AddAnItem() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //add an item
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        //check to make sure the description of the first item of the list is the same
        //as the description in AddanItem's parameters
        assertEquals("eat at Dino's", data.get(0).getDescription());
    }

    //test if an list can be cleared of all items (requirement #6)
    @Test
    void clearAllItems() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //add an item
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        //call clear the list on the data list
        controller.ClearList(data);
        //make sure the datalist is empty
        assertEquals(true, data.isEmpty());
    }

    //test for a function that ensures that A due date
    // shall be a valid date within the Gregorian Calendar (part of requirement #3)
    @Test
    void dateValidation() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make a boolean to store the result of calling the datevalidationfunction
        Boolean actual = controller.dateValidation("2020-03-04");
        //check to make sure the date validation function returned true
        assertEquals(true, actual);
    }

    //test for a function that ensures that A due date shall be displayed to users
    // in the format: YYYY-MM-DD (part of requirement #3)
    @Test
    void checkDate() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make a boolean to store the result of calling the checkDatefunction
        Boolean actual = controller.checkDate("3333-11-99");
        //check to make sure the check Date function returned true
        assertEquals(true, actual);
    }

    //test for a function that ensures a description can be only 1-256 in length(requirement #2)
    @Test
    void checkDescriptionLength() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make a boolean to store the result of calling the checkDescriptionLengthfunction
        Boolean actual = controller.checkDescriptionLength("eat at Dino's");
        //check to make sure the check Description Length function returned true
        assertEquals(true, actual);
    }

    //test if an item can be removed (requirement #5)
    @Test
    void deleteCurrentItemClicked() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //populate the new observable list
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        controller.AddAnItem(data, "2020-04-05", "do laundry");
        controller.AddAnItem(data, "2020-07-23", "eat pie");
        //delete the item at the second index
        controller.DeleteAnItem(data, data, 1);
        //make a boolean variable to store the result of if
        //the description of the second index is the description of the formerly
        //thrid index
        boolean actual = data.get(1).getDescription().equals("eat pie");
        //check to make sure the boolean variable istrue
        assertEquals(true, actual);
    }

    //test if a List can be made up of only complete items (requirement #12)
    @Test
    void onlyCompleteItems() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //make an observable list for storing the complete data
        ObservableList<Item> completedata = FXCollections.observableArrayList();
        //populate the first observable list
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        controller.AddAnItem(data, "2020-04-05", "do laundry");
        controller.AddAnItem(data, "2020-07-23", "eat pie");
        //make the data at the second index complete
        controller.CompleteanItem(data, data, 1);
        //store the completed data in the complete data list by calling the onlyCompleteItems function
        controller.onlyCompleteItems(completedata, data);
        //use a boolean variable to store if complete data's first index has the description
        //of the only compled item
        boolean actual = completedata.get(0).getDescription().equals("do laundry");
        //check if the boolean variable is true
        assertEquals(true, actual);
    }

    //test if a List can be made up of only complete items (requirement #11)
    @Test
    void onlyIncompleteItems() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //make an observable list for storing the incomplete data
        ObservableList<Item> incompletedata = FXCollections.observableArrayList();
        //populate the first observable list
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        controller.AddAnItem(data, "2020-04-05", "do laundry");
        controller.AddAnItem(data, "2020-07-23", "eat pie");
        //make the data at the second index complete
        controller.CompleteanItem(data, data, 1);
        //use the onlyIncompleteItems function to store all the incomplete items in
        //the incomplete data list
        controller.onlyIncompleteItems(incompletedata, data);
        //use a boolean variable to see if incomplete data's second item
        //has a description equal to the first observable list's third item
        boolean actual = incompletedata.get(1).getDescription().equals("eat pie");
        //make sure the boolean variable is true
        assertEquals(true, actual);
    }

    //test if an item can be set as incomplete  (part of requirement #9)
    @Test
    void incompleteanItem() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //populate the observable list
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        controller.AddAnItem(data, "2020-04-05", "do laundry");
        controller.AddAnItem(data, "2020-07-23", "eat pie");
        //make the second index item complete
        controller.CompleteanItem(data, data, 1);
        //make the second index item incomplete
        controller.IncompleteanItem(data, data, 1);
        //use a boolean variable to see if the item at the second index is incomplete
        boolean actual = data.get(1).isCompleted().equals("incomplete");
        //make sure the boolean variable is true
        assertEquals(true, actual);

    }

    //test if an item can be set as complete (part of requirement #9)
    @Test
    void completeanItem() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //populate the observable list
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        controller.AddAnItem(data, "2020-04-05", "do laundry");
        controller.AddAnItem(data, "2020-07-23", "eat pie");
        //make the second index item complete
        controller.CompleteanItem(data, data, 1);
        //use a boolean variable to see if the item at the second index is incomplete
        boolean actual = data.get(1).isCompleted().equals("complete");
        //make sure the boolean variable is true
        assertEquals(true, actual);
    }

    //test if a list of items can be turned into a test string (part of requirement #13)
    @Test
    void putDataToString() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //populate the observable list
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        //store the result of the PutDataToString function in a string
        String actualString = controller.PutDataToString(data);
        boolean actual = actualString.equals("2020-03-04 incomplete eat at Dino's \n");
        //make sure the string is true
        assertEquals(true, actual);
    }

    //test if a string can be written to a data file (part of requirement #13)
    @Test
    void putDataToFile() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //populate the observable list
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        //make an output string fot the result of the function PutDataToString when passed
        //the data array list
        String StringToOutPut = controller.PutDataToString(data);
        //call the put data to file function
        controller.PutDataToFile("New file", StringToOutPut);
        //get the pathname of the new file
        String Pathname2 = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + "New file" + ".txt";
        //make a file object for this new file name
        File file5 = new File(Pathname2);
        //make a boolean variable to see if this file now exists
        boolean actual = file5.exists();
        //check to make sure this boolean variable is true
        assertEquals(true, actual);
    }

    //test if a string can be created from the second index and onward of a string array (part of requirement #14)
    @Test
    void getLastString() {
        //make a new controller object
        ListManagerController controller = new ListManagerController();
        //make a test sting
        String TestString = "I like to eat pie";
        //make an array of strings using the test strings
        String[] arrOfStr = TestString.split(" ");
        //store the result of calling the GetLastString function on the array of strings
        //in a new test string
        String TestString2 = controller.GetLastString(arrOfStr);
        //use a boolean variable to test if the second testring equals the last three strings
        //of the string array
        boolean actual = TestString2.equals("to eat pie ");
        //make sure the boolean variable is true
        assertEquals(true, actual);
    }

    //test if a list can be created from a text file (part of requirement #14)
    @Test
    void loadAList() {
        //create a try block and a catch block
        try
        {
            //make a new controller
            ListManagerController controller = new ListManagerController();
            //make an observable list
            ObservableList<Item> data = FXCollections.observableArrayList();
            //populate the observable list
            controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
            controller.AddAnItem(data, "2020-03-04", "eating food");
            controller.AddAnItem(data, "2020-03-04", "wash car");
            //call the PutDataToString function on the observable array and store the result in a string
            String StringToOutPut = controller.PutDataToString(data);
            //put the resulting string in the PutDataToFile function
            controller.PutDataToFile("New file", StringToOutPut);
            //clear the data array
            data.clear();
            //get the pathname of the new file
            String Pathname2 = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + "New file" + ".txt";
            //make a file reader
            FileReader file1R = new FileReader(Pathname2);
            //call the load a list function and store the result in data
            data = controller.LoadAList( "New file", file1R);
            //check to see if the item at the second index of data has a description
            //equal to it's original description
            assertEquals("eating food ", data.get(1).getDescription());
        }
        //if the try block fails
        catch(IOException e)
        {
            //print the exception
            e.printStackTrace();
        }
    }

    //test if an you can find the index of the same item in two different lists (auxillary function used in many other functions)
    @Test
    void FindIndexOfItemInAnotherList() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //make another observable list
        ObservableList<Item> completedata = FXCollections.observableArrayList();
        //populate the observable list
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        controller.AddAnItem(data, "2020-03-04", "eating food");
        controller.AddAnItem(data, "2020-03-04", "wash car");
        //call the auxillary function CompleteanItem,
        controller.CompleteanItem(data, data, 1);
        //call the auxillary function only complete items
        controller.onlyCompleteItems(completedata, data);
        //make an index to return the result of the function FindIndexOfItemInAnotherList being called
        //on completed data and data
        int ReturnedIndex = controller.FindIndexOfItemInAnotherList(completedata.get(0), data);
        assertEquals(1, ReturnedIndex);
    }

    //test if an you can change the description of an item in a list (requirement #7)
    @Test
    void changeItemDescription() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //populate the observable list
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        //change the item description
        controller.ChangeItemDescription(data, data, 0, "Gamer time");
        //see if the item has a new changed description using assert equals
        assertEquals("Gamer time", data.get(0).getDescription());
    }

    //test if an you can change the description of an item in a list (requirement #8)
    @Test
    void changeItemDueDate() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //populate the observable list
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        //change the item duedate
        controller.ChangeItemDueDate(data, data, 0, "2021-09-03");
        //see if the item has a new changed duedate using assert equals
        assertEquals("2021-09-03", data.get(0).getDueDate());
    }

    //test if an you can sort a list by due date (requirement bonus)
    @Test
    void sortList() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //populate the observable list that is out of order
        controller.AddAnItem(data, "2020-03-04", "eat at Dino's");
        controller.AddAnItem(data, "2021-04-05", "do laundry");
        controller.AddAnItem(data, "2002-07-23", "eat pie");
        //call sortlist on the observable list
        controller.SortList(data);
        //use a boolean variable to test if the item at the first index of data
        //has the description of the item that comes first by due date
        boolean actual = data.get(0).getDescription().equals("eat pie");
        //make sure the boolean variable is true
        assertEquals(true, actual);

    }

    //test if an a list can hold at least 100 items (requirement #1)
    @Test
    void make100Items() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //populate the observable list by calling make100Items
        controller.Make100Items(data);
        //use a boolean variable to test if the item at the first index of data
        //has the description of the item that comes first by due date
        boolean actual = data.get(99).getDueDate().equals("2099-03-04");
        //make sure the boolean variable is true
        assertEquals(true, actual);
    }

    //test if the correct list to show is returned given a set of boolean values (requirement #10)
    @Test
    void obtainListToShow() {
        //make a new controller
        ListManagerController controller = new ListManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //make an observable list
        ObservableList<Item> incompletedata = FXCollections.observableArrayList();
        //make an observable list
        ObservableList<Item> completedata = FXCollections.observableArrayList();
        //make three boolean values
        boolean b1 = true;
        boolean b2 = false;
        boolean b3 = false;
        //make a list to store the value of obtainListToShow
        ObservableList<Item> listToDisplay = controller.ObtainListToUse(b1, b2, b3, data, completedata, incompletedata);
        //use a boolean variable to test if the list returned by
        // listToDisplay is equal to the first list declared
        boolean actual = listToDisplay.equals(data);
        //make sure the boolean variable is true
        assertEquals(true, actual);
    }



}