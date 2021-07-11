/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joshua Ashby
 */
package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import static org.junit.jupiter.api.Assertions.*;

class ListManagerControllerTest  {

    //test if an item can be added to the list (requirement #4)
    @Test
    void AddAnItem() {
        ListManagerController controller = new ListManagerController();
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.AddAnItem(data, "2020-03-04", "dankmemes");
        assertEquals("dankmemes", data.get(0).getDescription());
    }

    //test if an list can be cleared of all items (requirement #6)
    @Test
    void clearAllItems() {
        ListManagerController controller = new ListManagerController();
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.AddAnItem(data, "2020-03-04", "dankmemes");
        controller.ClearList(data);
        assertEquals(true, data.isEmpty());
    }

    //test for a function that ensures that A due date
    // shall be a valid date within the Gregorian Calendar (part of requirement #3)
    @Test
    void dateValidation() {
        ListManagerController controller = new ListManagerController();
        Boolean actual = controller.dateValidation("2020-03-04");
        assertEquals(true, actual);
    }

    //test for a function that ensures that A due date shall be displayed to users
    // in the format: YYYY-MM-DD (part of requirement #3)
    @Test
    void checkDate() {
        ListManagerController controller = new ListManagerController();
        Boolean actual = controller.checkDate("3333-11-99");
        assertEquals(true, actual);
    }

    //test for a function that ensures a description can be only 1-256 in length(requirement #2)
    @Test
    void checkDescriptionLength() {
        ListManagerController controller = new ListManagerController();
        Boolean actual = controller.checkDescriptionLength("3333-11-99");
        assertEquals(true, actual);
    }

    //test if an item can be removed (requirement #5)
    @Test
    void deleteCurrentItemClicked() {
        ListManagerController controller = new ListManagerController();
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.AddAnItem(data, "2020-03-04", "dankmemes");
        controller.AddAnItem(data, "2020-04-05", "do laundry");
        controller.AddAnItem(data, "2020-07-23", "eat pie");
        controller.DeleteanItem(data, data, 1);
        boolean actual = data.get(1).getDescription().equals("eat pie");
        assertEquals(true, actual);
    }

    //test if a List can be made up of only complete items (requirement #12)
    @Test
    void onlyCompleteItems() {
        ListManagerController controller = new ListManagerController();
        ObservableList<Item> data = FXCollections.observableArrayList();
        ObservableList<Item> completedata = FXCollections.observableArrayList();
        controller.AddAnItem(data, "2020-03-04", "dankmemes");
        controller.AddAnItem(data, "2020-04-05", "do laundry");
        controller.AddAnItem(data, "2020-07-23", "eat pie");
        controller.CompleteanItem(data, data, 1);
        controller.onlyCompleteItems(completedata, data);
        boolean actual = completedata.get(0).getDescription().equals("do laundry");
        assertEquals(true, actual);
    }

    //test if a List can be made up of only complete items (requirement #11)
    @Test
    void onlyIncompleteItems() {
        ListManagerController controller = new ListManagerController();
        ObservableList<Item> data = FXCollections.observableArrayList();
        ObservableList<Item> incompletedata = FXCollections.observableArrayList();
        controller.AddAnItem(data, "2020-03-04", "dankmemes");
        controller.AddAnItem(data, "2020-04-05", "do laundry");
        controller.AddAnItem(data, "2020-07-23", "eat pie");
        controller.CompleteanItem(data, data, 1);
        controller.onlyIncompleteItems(incompletedata, data);
        boolean actual = incompletedata.get(1).getDescription().equals("eat pie");
        assertEquals(true, actual);
    }

    //test if an item can be set as incomplete  (part of requirement #9)
    @Test
    void incompleteanItem() {
        ListManagerController controller = new ListManagerController();
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.AddAnItem(data, "2020-03-04", "dankmemes");
        controller.AddAnItem(data, "2020-04-05", "do laundry");
        controller.AddAnItem(data, "2020-07-23", "eat pie");
        controller.CompleteanItem(data, data, 1);
        controller.IncompleteanItem(data, data, 1);
        boolean actual = data.get(1).isCompleted().equals("incomplete");
        assertEquals(true, actual);

    }

    //test if an item can be set as complete (part of requirement #9)
    @Test
    void completeanItem() {
        ListManagerController controller = new ListManagerController();
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.AddAnItem(data, "2020-03-04", "dankmemes");
        controller.AddAnItem(data, "2020-04-05", "do laundry");
        controller.AddAnItem(data, "2020-07-23", "eat pie");
        controller.CompleteanItem(data, data, 1);
        boolean actual = data.get(1).isCompleted().equals("complete");
        assertEquals(true, actual);
    }


    @Test
    void putDataToString() {
        ListManagerController controller = new ListManagerController();
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.AddAnItem(data, "2020-03-04", "dankmemes");
        String actualString = controller.PutDataToString(data);
        boolean actual = actualString.equals("2020-03-04 incomplete dankmemes \n");
        assertEquals(true, actual);
    }

    @Test
    void putDataToFile() {
        ListManagerController controller = new ListManagerController();
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.AddAnItem(data, "2020-03-04", "dankmemes");
        String StringToOutPut = controller.PutDataToString(data);
        controller.PutDataToFile("New file", StringToOutPut);
        String Pathname2 = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + "New file" + ".txt";
        File file5 = new File(Pathname2);
        boolean actual = file5.exists();
        assertEquals(true, actual);
    }

    @Test
    void getLastString() {
        ListManagerController controller = new ListManagerController();
        String TestString = "I like to eat pie";
        String[] arrOfStr = TestString.split(" ");
        String TestString2 = controller.GetLastString(arrOfStr);
        boolean actual = TestString2.equals("to eat pie ");
        assertEquals(true, actual);
    }

    @Test
    void loadAList() {
        try
        {
            ListManagerController controller = new ListManagerController();
            ObservableList<Item> data = FXCollections.observableArrayList();
            controller.AddAnItem(data, "2020-03-04", "dankmemes");
            controller.AddAnItem(data, "2020-03-04", "eating food");
            controller.AddAnItem(data, "2020-03-04", "wash car");
            String StringToOutPut = controller.PutDataToString(data);
            controller.PutDataToFile("New file", StringToOutPut);
            data.clear();
            String Pathname2 = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + "New file" + ".txt";
            FileReader file1R = new FileReader(Pathname2);
            data = controller.LoadAList( "New file", file1R);
            assertEquals("eating food ", data.get(1).getDescription());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    @Test
    void findIndexToDeleteInDataList() {
    }

    @Test
    void changeItemDescription() {
    }

    @Test
    void changeItemDueDate() {
    }
}