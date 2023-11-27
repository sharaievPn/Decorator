package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CachedDocument implements Document {
    private Document wrappedDocument;
    private static final String DB_URL =
            "jdbc:sqlite:/Users/bitcoin/UCU/JavaProjects/Java_OOP/Decorator/BD/QueriesBD";

    public CachedDocument(Document document) {
        this.wrappedDocument = document;
    }

    @Override
    public String parse() {
        String url = this.getGcsPath();
        String cachedResult = getCachedResult(url);
        if (cachedResult != null) {
            return cachedResult;
        }

        String result = wrappedDocument.parse();
        saveToCache(url, result);
        return result;
    }

    private String getCachedResult(String url) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.
                     prepareStatement("SELECT result FROM url_requests WHERE url = ?")) {
            preparedStatement.setString(1, url);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("result");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveToCache(String url, String result) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.
                     prepareStatement("INSERT INTO url_requests (url, result) VALUES (?, ?)")) {
            preparedStatement.setString(1, url);
            preparedStatement.setString(2, result);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getGcsPath() {
        if (wrappedDocument instanceof SmartDocument) {
            SmartDocument smartDoc = (SmartDocument) wrappedDocument;
            return smartDoc.gcsPath;
        }
        return null;
    }
}
