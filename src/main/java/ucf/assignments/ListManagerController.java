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
        String NewDueDate = EditCurrentItemDescriptionDueDate.getText();
        if(dateValidation(NewDueDate))
        {
            int thingToEdit = ToDoListViewer.getSelectionModel().getSelectedIndex();
            if(showcompleteitems)
            {
                ChangeItemDueDate(data, completedata, thingToEdit, NewDueDate);
                ToDoListViewer.getItems().setAll(completedata);
            }
            if(showincompleteitems)
            {
                ChangeItemDueDate(data, incompletedata, thingToEdit, NewDueDate);
                ToDoListViewer.getItems().setAll(incompletedata);
            }
            if(showallitems)
            {
                ChangeItemDueDate(data, data, thingToEdit, NewDueDate);
                ToDoListViewer.getItems().setAll(data);
            }
        }


    }

    @FXML
    public void EditItemDesciptionClicked(ActionEvent actionEvent) {
        String NewDescription = EditCurrentItemDescriptionTextField.getText();

        if(checkDescriptionLength(NewDescription))
        {
            int thingToEdit = ToDoListViewer.getSelectionModel().getSelectedIndex();
            if(showcompleteitems)
            {
                ChangeItemDescription(data, completedata, thingToEdit, NewDescription);
                ToDoListViewer.getItems().setAll(completedata);
            }
            if(showincompleteitems)
            {
                ChangeItemDescription(data, incompletedata, thingToEdit, NewDescription);
                ToDoListViewer.getItems().setAll(incompletedata);
            }
            if(showallitems)
            {
                ChangeItemDescription(data, data, thingToEdit, NewDescription);
                ToDoListViewer.getItems().setAll(data);
            }
        }

    }

    @FXML
    public void SaveThisListClicked(ActionEvent actionEvent) {
        String Pathname = System.getProperty("user.dir") + "\\\\" + "SavedLists";
        File file1 = new File(Pathname);
        file1.mkdirs();
        String ListNameToSave = SaveListTextField.getText();
        String StringToWrite = PutDataToString(data);
        PutDataToFile(ListNameToSave, StringToWrite);

    }

    @FXML
    public void LoadSavedListClicked(ActionEvent actionEvent) {
        String ListNameToLoad = LoadListTextField.getText();
        String Pathname2 = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + ListNameToLoad + ".txt";
        File file5 = new File(Pathname2);
        if(file5.exists())
        {
            try {
                FileReader file1R = new FileReader(Pathname2);
                data = LoadAList( ListNameToLoad, file1R);
                ToDoListViewer.getItems().setAll(data);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


    public  boolean dateValidation(String date)
    {
        boolean status = false;
        if (checkDate(date)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(date);
                status = true;
            } catch (Exception e) {
                status = false;
            }
        }
        return status;
    }

    public  void ClearList(ObservableList list1)
    {
        list1.clear();
    }

    public  boolean checkDate(String date)
    {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public  boolean checkDescriptionLength(String description)
    {
        if(description.length() >= 1 && description.length() <= 256)
        {
            return true;
        }
        return false;

    }

    public void onlyCompleteItems(ObservableList<Item> list1, ObservableList<Item> list2)
    {
        list1.clear();
        for(int i = 0; i < list2.size(); i++)
        {
            if( list2.get(i).isCompleted().equals("complete"))
            {
                list1.add(list2.get(i));
            }
        }

    }

    public void onlyIncompleteItems(ObservableList<Item> list1, ObservableList<Item> list2)
    {
        list1.clear();
        for(int i = 0; i < list2.size(); i++)
        {
            if( list2.get(i).isCompleted().equals("incomplete"))
            {
                list1.add(list2.get(i));
            }
        }

    }

    public String PutDataToString(ObservableList<Item> datalist)
    {

        String OutputString = "";
        for(int i = 0; i < datalist.size(); i++)
        {
            String TempString = datalist.get(i).getDueDate() + " " + datalist.get(i).isCompleted() + " " + datalist.get(i).getDescription() + " \n";
            OutputString += TempString;
        }

        return OutputString;
    }

    public void PutDataToFile(String UserFileName, String textToOutput)
    {

        String Pathname2 = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + UserFileName + ".txt";
        File file4 = new File(Pathname2);
        try {
            file4.createNewFile();
            //make a file writer and write the author and site name inside of a meta and title tag
            FileWriter myWriter = new FileWriter(Pathname2);
            myWriter.write(textToOutput);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int FindIndexToDeleteInDataList(Item itemToFind, ObservableList <Item> data1)
    {
        for(int i = 0; i < data1.size(); i++)
        {
            if(data1.get(i).equals(itemToFind))
            {
                return i;
            }
        }

        return  -1;
    }

    public  String GetLastString(String [] strarr)
    {
        String OutputString = "";
        for(int i = 2; i < strarr.length; i++)
        {
           OutputString += strarr[i] + " ";
        }

        return OutputString;
    }

    public void AddAnItem(ObservableList<Item> list, String NewDueDate, String NewDescription)
    {

        Item tempitem = new Item(NewDueDate, NewDescription);
        list.add(tempitem);
    }

    public void DeleteanItem(ObservableList<Item> list, ObservableList<Item> list2, int currentDeleteIndex)
    {
        if(list.equals(list2))
        {
          list.remove(currentDeleteIndex);
        }
        else
        {
            int DataDeleteIndex = FindIndexToDeleteInDataList(list2.get(currentDeleteIndex), data);
            list2.remove(currentDeleteIndex);
            list.remove(DataDeleteIndex);
        }

    }

    public void IncompleteanItem(ObservableList<Item> list, ObservableList<Item> list2, int currentEditIndex)
    {
        if(list.equals(list2))
        {
            list.get(currentEditIndex).setCompleted("incomplete");
        }
        else
        {
            list2.get(currentEditIndex).setCompleted("incomplete");
            int DataIncompleteIndex = FindIndexToDeleteInDataList( list2.get(currentEditIndex), data);
            list.get(DataIncompleteIndex).setCompleted("incomplete");
        }


    }

    public void CompleteanItem(ObservableList<Item> list, ObservableList<Item> list2, int currentEditIndex)
    {
        if(list.equals(list2))
        {
            list.get(currentEditIndex).setCompleted("complete");
        }
        else
        {
            list2.get(currentEditIndex).setCompleted("complete");
            int DataIncompleteIndex = FindIndexToDeleteInDataList( list2.get(currentEditIndex), data );
            list.get(DataIncompleteIndex).setCompleted("complete");
        }

    }

    public ObservableList<Item> LoadAList( String ListNameToLoad, FileReader file1R)
    {
        Scanner sc = new Scanner(file1R);
        ObservableList<Item> templist = FXCollections.observableArrayList();
        while(sc.hasNextLine() )
        {
            String ItemString = sc.nextLine();
            String[] arrOfStr = ItemString.split(" ");
            Item tempitem = new Item(arrOfStr[0], GetLastString(arrOfStr));
            if(arrOfStr[1].equals("complete"))
            {
                tempitem.setCompleted("complete");
            }
            else
            {
                tempitem.setCompleted("incomplete");
            }
            templist.add(tempitem);
        }
 return templist;
    }

    public void ChangeItemDescription(ObservableList<Item> list, ObservableList<Item> list2, int EditIndex, String NewDescription)
    {
        if(list.equals(list2))
        {
            list.get(EditIndex).setDescription(NewDescription);
        }
        else
        {
            int DataEditIndex = FindIndexToDeleteInDataList(list2.get(EditIndex), data);
            list2.get(EditIndex).setDescription(NewDescription);
            list.get(DataEditIndex).setDescription(NewDescription);
        }

    }

    public void ChangeItemDueDate(ObservableList<Item> list, ObservableList<Item> list2, int EditIndex, String NewDueDate)
    {
        if(list.equals(list2))
        {
            list.get(EditIndex).setDueDate(NewDueDate);
        }
        else
        {
            int DataEditIndex = FindIndexToDeleteInDataList(list2.get(EditIndex), data);
            list2.get(EditIndex).setDueDate(NewDueDate);
            list.get(DataEditIndex).setDueDate(NewDueDate);
        }

    }

    public void TrueFalseSetter(boolean B1, boolean B2, boolean B3)
    {
        B1 = true;
        B2 = false;
        B3 = false;
    }

}
