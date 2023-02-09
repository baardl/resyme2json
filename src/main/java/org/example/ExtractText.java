package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.slf4j.LoggerFactory.getLogger;


public class ExtractText {
    private static final Logger log = getLogger(ExtractText.class);
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

    //Create method to Read text from file
    public static String readText(String textFilePath) {
        String text = "";
        try {
            File file = new File(textFilePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                text += scanner.nextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            Path currentRelativePath = Paths.get("");
            String path = currentRelativePath.toAbsolutePath().toString();
            log.info("Current relative path is: " + path);
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