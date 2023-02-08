package org.example;


import static org.junit.Assert.assertTrue;

public class ExtractTextTest {

    @org.junit.Test
    public void extractTextFromPdf() {
        String pdfFilePath = "src/test/resources/bardlind-cv.pdf";
        String text = ExtractText.extractTextFromPdf(pdfFilePath);
        assertTrue(text.contains("Lind"));
    }
}