package org.example;

public class Main {
    public static void main(String[] args) {
        SmartDocument sd = new SmartDocument("gs://some_stuff/Image 26.11.2023 at 21.24.png");
        System.out.println(sd.parse());
        System.out.println();
        TimedDocument td = new TimedDocument(new SmartDocument("gs://some_stuff/Image 26.11.2023 at 21.24.png"));
        System.out.println(td.parse());
        System.out.println();
        CachedDocument cd = new CachedDocument(new SmartDocument("gs://some_stuff/Image 26.11.2023 at 21.24.png"));
        System.out.println(cd.parse());
        System.out.println();
        CachedDocument cd1 = new CachedDocument(new SmartDocument("gs://some_stuff/Image 26.11.2023 at 21.24.jpeg"));
        System.out.println(cd1);
        System.out.println();
        System.out.println(cd.parse());
        System.out.println();
        System.out.println(cd1.parse());
        System.out.println();
        System.out.println(cd1);
        System.out.println();
        System.out.println(cd.parse());
        System.out.println();
        System.out.println(cd1.parse());
        System.out.println();
    }
}