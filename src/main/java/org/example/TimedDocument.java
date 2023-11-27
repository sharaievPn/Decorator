package org.example;

public class TimedDocument implements Document {
    private Document wrappedDocument;

    public TimedDocument(Document document) {
        this.wrappedDocument = document;
    }

    @Override
    public String parse() {
        long startTime = System.currentTimeMillis();
        String result = wrappedDocument.parse();
        long endTime = System.currentTimeMillis();

        System.out.println("Parsing time: " + (endTime - startTime) + " ms");
        return result;
    }
}
