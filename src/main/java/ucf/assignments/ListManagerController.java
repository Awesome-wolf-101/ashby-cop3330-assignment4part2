/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joshua Ashby
 */
package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;
import java.util.Scanner;
import java.lang.String;
import java.lang.*;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.scene.control.*;

public class ListManagerController implements Initializable {
    //make three boolean variables each representing which list should be shown to the
    //user at the moment
    Boolean showallitems = true;
    Boolean showincompleteitems = false;
    Boolean showcompleteitems = false;

    //FXML variables imported from the ListManager.fxml File
    @FXML
    public TableView ToDoListViewer;
    public TableColumn DueDateTableColumn;
    public TableColumn CompletedTableColumn;
    public TableColumn DescriptionTableColumn;

    //FXML variables imported from the ListManager.fxml File
    @FXML
    public TextField AddItemDescriptionTextField;
    public TextField AddItemDueDateTextField;
    public TextField EditCurrentItemDescriptionTextField;
    public TextField EditCurrentItemDescriptionDueDate;
    public TextField LoadListTextField;
    public TextField SaveListTextField;

    //make three observable lists for each type of data to be shown to the user
    ObservableList<Item> data = FXCollections.observableArrayList();
    ObservableList<Item> incompletedata = FXCollections.observableArrayList();
    ObservableList<Item> completedata = FXCollections.observableArrayList();

    @FXML
    public void initialize(URL url, ResourceBundle rb)
    {
        //set each collumn to a new value using .setCellValueFactory
        DueDateTableColumn.setCellValueFactory(new PropertyValueFactory("DueDate"));
        CompletedTableColumn.setCellValueFactory(new PropertyValueFactory("completed"));
        DescriptionTableColumn.setCellValueFactory(new PropertyValueFactory("Description"));
    }

    @FXML
    public void AddItemClicked(ActionEvent actionEvent) {
        //get the text from both the additem due date text field
        // and the additem description text field
        String NewDueDate = AddItemDueDateTextField.getText();
        String NewDescription = AddItemDescriptionTextField.getText();
        //make sure that the user entered a valid due date and description
        //using the auxillary functions dateValidation and checkDescriptionLength
        if(dateValidation(NewDueDate) && checkDescriptionLength(NewDescription))
        {
            //if only completed items are supposed to be shown to the user
            if(showcompleteitems)
            {
                //call the auxillary function add an item,
                AddAnItem(data, NewDueDate, NewDescription);
                //call the auxillary function only complete items
                onlyCompleteItems(completedata, data);
                //set the table view items to complete data
                ToDoListViewer.getItems().setAll(completedata);
            }
            //if only incompleted items are supposed to be shown to the user
            if(showincompleteitems)
            {
                //call the auxillary function add an item,
                AddAnItem(data, NewDueDate, NewDescription);
                //call the auxillary function only incomplete items
                onlyIncompleteItems(incompletedata, data);
                //set the table view items to incomplete data
                ToDoListViewer.getItems().setAll(incompletedata);
            }
            //if all items are supposed to be shown to the user
            if(showallitems)
            {
                //add an item to data
                AddAnItem(data, NewDueDate, NewDescription);
                //set the table view items to all data
                ToDoListViewer.getItems().setAll(data);
            }
        }
    }

    @FXML
    public void DeleteCurrentItemClicked(ActionEvent actionEvent) {
            //obtain the index of the item the user is currently clicking on
            int thingToDelete = ToDoListViewer.getSelectionModel().getSelectedIndex();
            //if only complete items are supposed to be shown to the user
            if(showcompleteitems)
            {
                //use the axuillary function delete an item and pass in the completedata
                DeleteanItem(data, completedata, thingToDelete);
                //set the table view items to complete data
                ToDoListViewer.getItems().setAll(completedata);
            }
            //if only complete items are supposed to be shown to the user
            if(showincompleteitems)
            {
                //use the axuillary function delete an item and pass in the incompletedata
                DeleteanItem(data, incompletedata, thingToDelete);
                //set the table view items to incomplete data
                ToDoListViewer.getItems().setAll(incompletedata);
            }
            //if all items are supposed to be shown to the user
            if(showallitems)
            {
                //use the axuillary function delete an item and pass in the data
                DeleteanItem(data, data, thingToDelete);
                //set the table view items to all data
                ToDoListViewer.getItems().setAll(data);
            }
    }

    @FXML
    public void ClearAllListItemsClicked(ActionEvent actionEvent) {
        //call the function clear data on the data
        ClearList(data);
        //set the table view items to all data
        ToDoListViewer.getItems().setAll(data);
    }

    @FXML
    public void ShowOnlyCompleteItemsCllicked(ActionEvent actionEvent) {
        //call the auxillary function only complete items to update
        //the complete data observable list
        onlyCompleteItems(completedata, data);
        //set all of the boolean values for list to be shown to false
        //except for complete items which should be set to true
        showcompleteitems = true;
        showallitems = false;
        showincompleteitems = false;
        //set the table view items to complete data
        ToDoListViewer.getItems().setAll(completedata);
    }

    @FXML
    public void ShowOnlyIncompleteItemsCllicked(ActionEvent actionEvent) {
        //call the auxillary function only incomplete items to update
        //the incomplete data observable list
        onlyIncompleteItems(incompletedata, data);
        //set all of the boolean values for list to be shown to false
        //except for incomplete items which should be set to true
        showincompleteitems = true;
        showcompleteitems = false;
        showallitems = false;
        //set the table view items to incomplete data
        ToDoListViewer.getItems().setAll(incompletedata);
    }

    @FXML
    public void MakeCurrentItemIncompleteClicked(ActionEvent actionEvent) {
        //get the index currently clicked by the user
        int thingToEdit= ToDoListViewer.getSelectionModel().getSelectedIndex();
        //if only complete items are supposed to be shown to the user
        if(showcompleteitems)
        {
            //call the auxillary function incomplete an item
            //to make an item incomplete in both the data list and the
            //complete items list
            IncompleteanItem(data, completedata, thingToEdit);
            //call the functions only incomplete items
            //and only complete items
            //to update all the datalists
            onlyIncompleteItems(incompletedata, data);
            onlyCompleteItems(completedata, data);
            //set the table view items to complete data
            ToDoListViewer.getItems().setAll(completedata);
        }
        //if only incomplete items are supposed to be shown to the user
        if(showincompleteitems)
        {
            //call the auxillary function incomplete an item
            //to make an item incomplete in both the data list and the
            //incomplete items list
            IncompleteanItem(data, incompletedata, thingToEdit);
            //call the functions only incomplete items
            //and only complete items
            //to update all the datalists
            onlyIncompleteItems(incompletedata, data);
            onlyCompleteItems(completedata, data);
            //set the table view items to complete data
            ToDoListViewer.getItems().setAll(incompletedata);
        }
        //if all items are supposed to be shown to the user
        if(showallitems)
        {
            //call the auxillary function incomplete an item
            //to make an item incomplete in the data list
            IncompleteanItem(data, data, thingToEdit);
            //set the table view items to complete data
            ToDoListViewer.getItems().setAll(data);
        }
    }

    @FXML
    public void MakeCurrentItemCompleteClicked(ActionEvent actionEvent) {
        //get the index currently clicked by the user
        int thingToEdit= ToDoListViewer.getSelectionModel().getSelectedIndex();
        //if only complete items are supposed to be shown to the user
        if(showcompleteitems)
        {
            //call the auxillary function complete an item
            //to make an item complete in both the data list and the
            //complete items list
            CompleteanItem(data, completedata, thingToEdit);
            //call the functions only incomplete items
            //and only complete items
            //to update all the datalists
            onlyIncompleteItems(incompletedata, data);
            onlyCompleteItems(completedata, data);
            //set the table view items to complete data
            ToDoListViewer.getItems().setAll(completedata);
        }
        //if only incomplete items are supposed to be shown to the user
        if(showincompleteitems)
        {
            //call the auxillary function complete an item
            //to make an item complete in both the data list and the
            //complete items list
            CompleteanItem(data, completedata, thingToEdit);
            //call the functions only incomplete items
            //and only complete items
            //to update all the datalists
            onlyIncompleteItems(incompletedata, data);
            onlyCompleteItems(completedata, data);
            //set the table view items to incomplete data
            ToDoListViewer.getItems().setAll(incompletedata);
        }
        //if all items are supposed to be shown to the user
        if(showallitems)
        {
            //call the auxillary function incomplete an item
            //to make an item complete in both the data list and the
            //complete items list
            CompleteanItem(data, data, thingToEdit);
            //set the table view items to incomplete data
            ToDoListViewer.getItems().setAll(data);
        }
;
    }

    @FXML
    public void ShowAllItemsClicked(ActionEvent actionEvent) {
        //set all of the boolean values for list to be shown to false
        //except for show all items which should be set to true
        showallitems = true;
        showcompleteitems = false;
        showincompleteitems = false;
        //set the table view items to incomplete data
        ToDoListViewer.getItems().setAll(data);
    }

    @FXML
    public void EditItemDueDateClicked(ActionEvent actionEvent) {
        //get the text in the edit due date field
        String NewDueDate = EditCurrentItemDescriptionDueDate.getText();
        //if the date entered is a valid date
        if(dateValidation(NewDueDate))
        {
            //get the index to edit
            int IndexToEdit = ToDoListViewer.getSelectionModel().getSelectedIndex();
            //if only completed items are supposed to be shown to the user
            if(showcompleteitems)
            {
                //use the auxillary function change due date to change the due date
                //in both the complete data and data lists
                ChangeItemDueDate(data, completedata, IndexToEdit, NewDueDate);
                //display the complete data
                ToDoListViewer.getItems().setAll(completedata);
            }
            //if only incompleted items are supposed to be shown to the user
            if(showincompleteitems)
            {
                //use the auxillary function change due date to change the due date
                //in both the incomplete data and data lists
                ChangeItemDueDate(data, incompletedata, IndexToEdit, NewDueDate);
                //display the incomplete data
                ToDoListViewer.getItems().setAll(incompletedata);
            }
            //if all items are supposed to be shown to the user
            if(showallitems)
            {
                //use the auxillary function change due date to change the due date
                //in the data list
                ChangeItemDueDate(data, data, IndexToEdit, NewDueDate);
                //display all the data
                ToDoListViewer.getItems().setAll(data);
            }
        }
    }

    @FXML
    public void EditItemDesciptionClicked(ActionEvent actionEvent) {
        //get the text in the edit Desciption field
        String NewDescription = EditCurrentItemDescriptionTextField.getText();
        //if the description entered is of a valid length
        if(checkDescriptionLength(NewDescription))
        {
            //get the index to edit
            int IndexToEdit = ToDoListViewer.getSelectionModel().getSelectedIndex();
            //if only completed items are supposed to be shown to the user
            if(showcompleteitems)
            {
                //use the auxillary function change due date to change the due date
                //in both the complete data and data lists
                ChangeItemDescription(data, completedata, IndexToEdit, NewDescription);
                //display the complete data
                ToDoListViewer.getItems().setAll(completedata);
            }
            //if only incompleted items are supposed to be shown to the user
            if(showincompleteitems)
            {
                //use the auxillary function change due date to change the due date
                //in both the incomplete data and data lists
                ChangeItemDescription(data, incompletedata, IndexToEdit, NewDescription);
                //display the incomplete data
                ToDoListViewer.getItems().setAll(incompletedata);
            }
            //if all items are supposed to be shown to the user
            if(showallitems)
            {
                //use the auxillary function change due date to change the due date
                //in the data list
                ChangeItemDescription(data, data, IndexToEdit, NewDescription);
                //display all the data
                ToDoListViewer.getItems().setAll(data);
            }
        }
    }

    @FXML
    public void SaveThisListClicked(ActionEvent actionEvent) {
        //get the user's working directory and then make a new directory called "SavedLists
        String Pathname = System.getProperty("user.dir") + "\\\\" + "SavedLists";
        File file1 = new File(Pathname);
        file1.mkdirs();
        //get the list name to save
        String ListNameToSave = SaveListTextField.getText();
        //pass all of the data into an auxillary function
        //which turns the data into a string and store that data
        //in a new string
        String StringToWrite = PutDataToString(data);
        //use the auxillary function put data to File to save the new data in a .txt file
        PutDataToFile(ListNameToSave, StringToWrite);

    }

    @FXML
    public void LoadSavedListClicked(ActionEvent actionEvent) {
        //get the name of the list to be loaded
        String ListNameToLoad = LoadListTextField.getText();
        //make the pathname for the list to be loaded
        String Pathname2 = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + ListNameToLoad + ".txt";
        //make a file object based on this pathname
        File file5 = new File(Pathname2);
        //if the file with the given pathname exists
        if(file5.exists())
        {
            //use a try catch block
            try {
                //make a file reader to read from the file
                FileReader file1R = new FileReader(Pathname2);
                //set the data list to the list returned by the LoadAList function
                data = LoadAList( ListNameToLoad, file1R);
                //set the table view items to the new data list
                ToDoListViewer.getItems().setAll(data);
            }
            //if the try block fails
            catch (IOException e) {
                //print the exception
                e.printStackTrace();
            }
        }
    }


    public  boolean dateValidation(String date)
    {
        //make a boolean value false to begin with
        boolean status = false;
        //make sure the data is in the correct format ("dddd-dd-dd" where d is any number)
        if (checkDate(date)) {
            //make a new instance of a simple date format with "yyyy-MM-dd"
            //is the correct pattern
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //set lenient to false meaning this date must be a valid date on the gregorian
            //calendar
            dateFormat.setLenient(false);
            //set up a try block and a catch block
            try {
                //if the date format can be parsed set status to true
                dateFormat.parse(date);
                status = true;
            } catch (Exception e) {
                //if the date format cannot be parsed set status to false
                status = false;
            }
        }
        //return status
        return status;
    }

    public  void ClearList(ObservableList list1)
    {
        //clear the list passed into the function
        list1.clear();
    }

    public  boolean checkDate(String date)
    {
        //make sure the passed string is in the format "dddd-dd-dd"
        //where d is any integer number
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public  boolean checkDescriptionLength(String description)
    {
        //if the description is between 1 and 256 characters return true
        if(description.length() >= 1 && description.length() <= 256)
        {
            return true;
        }
        //otherwise return false
        return false;

    }

    public void onlyCompleteItems(ObservableList<Item> list1, ObservableList<Item> list2)
    {
        //clear the list to insert the complete items into
        list1.clear();
        //make a for loop to iterate through the list to get items from
        for(int i = 0; i < list2.size(); i++)
        {
            //if an item is complete
            if( list2.get(i).isCompleted().equals("complete"))
            {
                //add that item to the list to insert the complete items into
                list1.add(list2.get(i));
            }
        }

    }

    public void onlyIncompleteItems(ObservableList<Item> list1, ObservableList<Item> list2)
    {
        //clear the list to insert the incomplete items into
        list1.clear();
        //make a for loop to iterate through the list to get items from
        for(int i = 0; i < list2.size(); i++)
        {
            //if an item is complete
            if( list2.get(i).isCompleted().equals("incomplete"))
            {
                //add that item to the list to insert the incomplete items into
                list1.add(list2.get(i));
            }
        }

    }

    public String PutDataToString(ObservableList<Item> datalist)
    {
        //initialize an output string
        String OutputString = "";
        //make a for loop
        for(int i = 0; i < datalist.size(); i++)
        {
            //for all elements of the datalist make a temporary string to add to the output string
            String TempString = datalist.get(i).getDueDate() + " " + datalist.get(i).isCompleted() + " " + datalist.get(i).getDescription() + " \n";
            //add the temporary string to the output string
            OutputString += TempString;
        }
        //return the output string
        return OutputString;
    }

    public void PutDataToFile(String UserFileName, String textToOutput)
    {
        //get the pathname to save to by getting the user's working directory
        //going into the saved lists directory and making/overriting the userfilename.txt
        String Pathname2 = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + UserFileName + ".txt";
        //create a new file object based on this pathname
        File file4 = new File(Pathname2);
        //use a try block and a cathc block
        try {
            file4.createNewFile();
            //make a file writer
            FileWriter myWriter = new FileWriter(Pathname2);
            //write the output text to the file writer
            myWriter.write(textToOutput);
            //close the file writer
            myWriter.close();
        } catch (IOException e) {
            //print out the error if the try block fails
            e.printStackTrace();
        }
    }

    public int FindIndexOfItemInAnotherList(Item itemToFind, ObservableList <Item> data1)
    {
        //use a for loop to look through a list
        for(int i = 0; i < data1.size(); i++)
        {
            //if we find the item in the list return the index
            if(data1.get(i).equals(itemToFind))
            {
                return i;
            }
        }
        //if we cannot find the index return -1
        return  -1;
    }

    public  String GetLastString(String [] strarr)
    {
        //make an output string
        String OutputString = "";
        //sart at the third index of the arraystring and loop
        //through the array using a for loop
        for(int i = 2; i < strarr.length; i++)
        {
            //add each string to the output string while also adding a space
           OutputString += strarr[i] + " ";
        }
        //return the output string
        return OutputString;
    }

    public void AddAnItem(ObservableList<Item> list, String NewDueDate, String NewDescription)
    {
        //create a new item using the item constructor
        Item tempitem = new Item(NewDueDate, NewDescription);
        //add that item to the passed list
        list.add(tempitem);
    }

    public void DeleteanItem(ObservableList<Item> list, ObservableList<Item> list2, int currentDeleteIndex)
    {
        //if both passed lists are the same
        if(list.equals(list2))
        {
          //remove the item from the list
          list.remove(currentDeleteIndex);
        }
        //if both passed list are not the same
        else
        {
            //find the index of the item in the second list using
            //the auxillary function FindIndexOfItemInAnotherList
            //and store that index in a new variable
            int DataDeleteIndex = FindIndexOfItemInAnotherList(list2.get(currentDeleteIndex), data);
            //remove the item from one list using the passed in index
            list2.remove(currentDeleteIndex);
            //remove the item from the other list using the found index
            list.remove(DataDeleteIndex);
        }

    }

    public void IncompleteanItem(ObservableList<Item> list, ObservableList<Item> list2, int currentEditIndex)
    {
        //if both passed lists are the same
        if(list.equals(list2))
        {
            //make the current item in the list incomplete
            list.get(currentEditIndex).setCompleted("incomplete");
        }
        //if both passed lists are not the same
        else
        {
            //set the completed status of one list's item to incomplete
            list2.get(currentEditIndex).setCompleted("incomplete");
            //find the index of the item in the second list using
            //the auxillary function FindIndexOfItemInAnotherList
            //and store that index in a new variable
            int DataIncompleteIndex = FindIndexOfItemInAnotherList( list2.get(currentEditIndex), data);
            //set the completed status of one list's item to incomplete using the found index
            list.get(DataIncompleteIndex).setCompleted("incomplete");
        }
    }

    public void CompleteanItem(ObservableList<Item> list, ObservableList<Item> list2, int currentEditIndex)
    {
        //if both passed lists are the same
        if(list.equals(list2))
        {
            //make the current item in the list incomplete
            list.get(currentEditIndex).setCompleted("complete");
        }
        //if both passed lists are not the same
        else
        {
            //set the completed status of one list's item to incomplete
            list2.get(currentEditIndex).setCompleted("complete");
            //find the index of the item in the second list using
            //the auxillary function FindIndexOfItemInAnotherList
            //and store that index in a new variable
            int DataIncompleteIndex = FindIndexOfItemInAnotherList( list2.get(currentEditIndex), data );
            //set the completed status of one list's item to incomplete using the found index
            list.get(DataIncompleteIndex).setCompleted("complete");
        }
    }

    public ObservableList<Item> LoadAList( String ListNameToLoad, FileReader file1R)
    {
        //make a new scanner
        Scanner sc = new Scanner(file1R);
        //make a temporarylist to store the read data
        ObservableList<Item> templist = FXCollections.observableArrayList();
        //while the file still has lines
        while(sc.hasNextLine() )
        {
            //make each line into a string
            String ItemString = sc.nextLine();
            //split that sting based on spaces
            String[] arrOfStr = ItemString.split(" ");
            //make a new item and set it's due date to the string array's first item
            //set the strings description to every thing past the second item of the string array using
            //the auxillary function GetLastString
            Item tempitem = new Item(arrOfStr[0], GetLastString(arrOfStr));
            //if the string array's second index is equal to completed
            if(arrOfStr[1].equals("complete"))
            {
                //set the temp item's completed status to complete
                tempitem.setCompleted("complete");
            }
            //if the string array's second index is not equal to completed
            else
            {
                //set the temp item's completed status to incomplete
                tempitem.setCompleted("incomplete");
            }
            //add the new temp item to the list
            templist.add(tempitem);
        }
        //return the temporary list
        return templist;
    }

    public void ChangeItemDescription(ObservableList<Item> list, ObservableList<Item> list2, int EditIndex, String NewDescription)
    {
        //if the two lists are equal
        if(list.equals(list2))
        {
            //set description of the item at the given index to the description passed to the function
            list.get(EditIndex).setDescription(NewDescription);
        }
        //if the two lists are not equal
        else
        {
            //using the auxillary function FindIndexOfItemInAnotherList get the index
            //of the new item to edit in the other list and store the index in a new variable
            int DataEditIndex = FindIndexOfItemInAnotherList(list2.get(EditIndex), data);
            //edit description of the item in the list given by the passed index EditIndex
            list2.get(EditIndex).setDescription(NewDescription);
            //edit description of the item in the other list given by the calculated index DataEditIndex
            list.get(DataEditIndex).setDescription(NewDescription);
        }

    }

    public void ChangeItemDueDate(ObservableList<Item> list, ObservableList<Item> list2, int EditIndex, String NewDueDate)
    {
        //if the two lists are equal
        if(list.equals(list2))
        {
            //set DueDate of the item at the given index to the DueDate passed to the function
            list.get(EditIndex).setDueDate(NewDueDate);
        }
        else
        {
            //using the auxillary function FindIndexOfItemInAnotherList get the index
            //of the new item to edit in the other list and store the index in a new variable
            int DataEditIndex = FindIndexOfItemInAnotherList(list2.get(EditIndex), data);
            //edit DueDate of the item in the list given by the passed index EditIndex
            list2.get(EditIndex).setDueDate(NewDueDate);
            //edit DueDate of the item in the other list given by the calculated index DataEditIndex
            list.get(DataEditIndex).setDueDate(NewDueDate);
        }

    }

}
