package com.controller;

import java.time.LocalDate;

import com.model.Book;
import com.model.PageResponse;
import com.service.BookService;
import com.util.AlertUtil;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    // Search field
    @FXML private TextField searchField;

    // Pagination controls
    @FXML private Button prevBtn;
    @FXML private Button nextBtn;
    @FXML private Label pageLabel;

    private final BookService service = new BookService();

    // Pagination state
    private int currentPage = 0;
    private int totalPages = 1;
    private static final int PAGE_SIZE = 10;
    private String currentSearchQuery = null;

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
            PageResponse<Book> response;
            
            if (currentSearchQuery != null && !currentSearchQuery.trim().isEmpty()) {
                response = service.searchBooks(currentSearchQuery, currentPage, PAGE_SIZE);
            } else {
                response = service.getAllBooksPaginated(currentPage, PAGE_SIZE);
            }
            
            table.setItems(FXCollections.observableArrayList(response.getContent()));
            totalPages = response.getTotalPages();
            
            // Handle case when there are no results
            if (totalPages == 0) {
                totalPages = 1;
            }
            
            updatePaginationControls();
        } catch (Exception e) {
            AlertUtil.error("Error", e.getMessage());
        }
    }

    // ================= SEARCH =================

    @FXML
    private void searchBooks() {
        String query = searchField.getText();
        if (query != null && !query.trim().isEmpty()) {
            currentSearchQuery = query.trim();
            currentPage = 0; // Reset to first page
            loadBooks();
        }
    }

    @FXML
    private void clearSearch() {
        searchField.clear();
        currentSearchQuery = null;
        currentPage = 0;
        loadBooks();
    }

    // ================= PAGINATION =================

    @FXML
    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadBooks();
        }
    }

    @FXML
    private void nextPage() {
        if (currentPage < totalPages - 1) {
            currentPage++;
            loadBooks();
        }
    }

    private void updatePaginationControls() {
        pageLabel.setText("Page " + (currentPage + 1) + " of " + totalPages);
        prevBtn.setDisable(currentPage <= 0);
        nextBtn.setDisable(currentPage >= totalPages - 1);
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
