package com.controller;

import com.model.Book;
import com.service.BookService;
import com.util.AlertUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class BookController {

    @FXML private TableView<Book> table;
    @FXML private TableColumn<Book, String> titleCol;
    @FXML private TableColumn<Book, String> authorCol;
    @FXML private TableColumn<Book, String> isbnCol;
    @FXML private TableColumn<Book, LocalDate> dateCol;

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField isbnField;
    @FXML private DatePicker publishedDatePicker;

    private final BookService service = new BookService();

    // ================= INITIALIZE =================

    @FXML
    public void initialize() {

        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                titleField.setText(newVal.getTitle());
                authorField.setText(newVal.getAuthor());
                isbnField.setText(newVal.getIsbn());
                publishedDatePicker.setValue(newVal.getPublishedDate());
            }
        });

        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        authorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));
        isbnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIsbn()));
        dateCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPublishedDate()));

        loadBooks();
        clearForm();
    }


    // ================= LOAD =================

    @FXML
    private void loadBooks() {
        try {
            table.setItems(FXCollections.observableArrayList(service.getAllBooks()));
        } catch (Exception e) {
            AlertUtil.error("Error", e.getMessage());
        }
    }

    // ================= ADD =================

    @FXML
    private void addBook() {
        try {
            Book book = new Book();
            book.setTitle(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setIsbn(isbnField.getText());
            book.setPublishedDate(publishedDatePicker.getValue());

            service.addBook(book);
            loadBooks();
            clearForm();

        } catch (Exception e) {
            AlertUtil.error("Error", e.getMessage());
        }
    }

    // ================= UPDATE =================

    @FXML
    private void updateBook() {
        try {
            Book selected = table.getSelectionModel().getSelectedItem();

            if (selected == null) {
                AlertUtil.error("Error", "Select a book first");
                return;
            }

            selected.setTitle(titleField.getText());
            selected.setAuthor(authorField.getText());
            selected.setIsbn(isbnField.getText());
            selected.setPublishedDate(publishedDatePicker.getValue());

            service.updateBook(selected);
            loadBooks();
            clearForm();

        } catch (Exception e) {
            AlertUtil.error("Error", e.getMessage());
        }
    }

    // ================= DELETE =================

    @FXML
    private void deleteBook() {
        try {
            Book selected = table.getSelectionModel().getSelectedItem();

            if (selected == null) {
                AlertUtil.error("Error", "Select a book first");
                return;
            }

            service.deleteBook(selected.getId());
            loadBooks();
            clearForm();

        } catch (Exception e) {
            AlertUtil.error("Error", e.getMessage());
        }
    }

    // ================= CLEAR =================

    private void clearForm() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        publishedDatePicker.setValue(null);
        table.getSelectionModel().clearSelection();
    }
}
