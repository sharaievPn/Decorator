package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CachedDocument implements Document {
    private static final String DB_URL =
            "jdbc:sqlite:/Users/bitcoin/UCU/"
                    + "JavaProjects/Java_OOP/Decorator/BD/QueriesBD";
    private Document wrappedDocument;

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
        try (Connection CONN = DriverManager.getConnection(DB_URL);
             PreparedStatement PS = CONN.
                     prepareStatement("SELECT result FROM "
                             + "url_requests WHERE url = ?")) {
            PS.setString(1, url);
            try (ResultSet RS = PS.executeQuery()) {
                if (RS.next()) {
                    return RS.getString("result");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveToCache(String url, String result) {
        try (Connection CONN = DriverManager.getConnection(DB_URL);
             PreparedStatement PS =
                     CONN.prepareStatement("INSERT INTO u"
                             + "rl_requests (url, result) "
                                     + "VALUES (?, ?)")) {
            PS.setString(1, url);
            PS.setString(2, result);
            PS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getGcsPath() {
        if (wrappedDocument instanceof SmartDocument) {
            SmartDocument smartDoc = (SmartDocument) wrappedDocument;
            return smartDoc.getGcsPath();
        }
        return null;
    }
}
