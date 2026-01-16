package com.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.model.Book;
import com.model.PageResponse;


public class BookService {
    private static final String BASE_URL = "http://localhost:8080/api/books";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper;

    public BookService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public List<Book> getAllBooks() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch books");
        }

        // Parse as PageResponse and extract content
        PageResponse<Book> pageResponse = parsePageResponse(response.body());
        return pageResponse.getContent();
    }

    public PageResponse<Book> getAllBooksPaginated(int page, int size) throws Exception {
        String url = BASE_URL + "?page=" + page + "&size=" + size;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch books");
        }

        return parsePageResponse(response.body());
    }

    public PageResponse<Book> searchBooks(String query, int page, int size) throws Exception {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = BASE_URL + "/search?query=" + encodedQuery + "&page=" + page + "&size=" + size;
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to search books");
        }

        return parsePageResponse(response.body());
    }

    private PageResponse<Book> parsePageResponse(String json) throws Exception {
        // Parse the JSON into a generic map first, then extract Book list
        var node = mapper.readTree(json);
        PageResponse<Book> pageResponse = new PageResponse<>();
        pageResponse.setPage(node.get("page").asInt());
        pageResponse.setSize(node.get("size").asInt());
        pageResponse.setTotalElements(node.get("totalElements").asLong());
        pageResponse.setTotalPages(node.get("totalPages").asInt());
        
        List<Book> books = Arrays.asList(
                mapper.treeToValue(node.get("content"), Book[].class));
        pageResponse.setContent(books);
        
        return pageResponse;
    }

    public void addBook(Book book) throws Exception {
        String json = mapper.writeValueAsString(book);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201 && response.statusCode() != 200) {
            throw new RuntimeException("Failed to add book");
        }
    }

    public void updateBook(Book book) throws Exception {
        String json = mapper.writeValueAsString(book);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + book.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to update book");
        }
    }


    public void deleteBook(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new RuntimeException("Failed to delete book");
        }
    }
}

