package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;


public class Main {
    //Create method to extract text from pdf file
    public static String extractTextFromPdf(String pdfFilePath) {
        String text = "";
        try {
            PDDocument document = PDDocument.load(new File(pdfFilePath));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    //Create method to extract text from pdf file
    public static String extractTextFromPdf(String pdfFilePath, int startPage, int endPage) {
        String text = "";
        try {
            PDDocument document = PDDocument.load(new File(pdfFilePath));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(startPage);
            pdfStripper.setEndPage(endPage);
            text = pdfStripper.getText(document);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    //Create method to extract text from pdf file
    public static String extractTextFromPdf(String pdfFilePath, int startPage, int endPage, String password) {
        String text = "";
        try {
            PDDocument document = PDDocument.load(new File(pdfFilePath), password);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(startPage);
            pdfStripper.setEndPage(endPage);
            text = pdfStripper.getText(document);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void main(String[] args) {
        //find relative path to "resources" folder  (src/main/resources)
        String pdfFilePath = "src/test/resources/bardlind-cv.pdf";
        String text = extractTextFromPdf(pdfFilePath);
        System.out.println(text);
    }
}