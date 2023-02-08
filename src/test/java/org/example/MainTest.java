package org.example;


import static org.junit.Assert.assertTrue;

public class MainTest {

    @org.junit.Test
    public void extractTextFromPdf() {
        String pdfFilePath = "src/test/resources/bardlind-cv.pdf";
        String text = Main.extractTextFromPdf(pdfFilePath);
        assertTrue(text.contains("Lind"));
    }
}