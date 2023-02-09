package org.example;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;

import static org.example.Resyme2Json.isEmpty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

public class Text2JsonManualTest {
    private static final Logger log = getLogger(Text2JsonManualTest.class);

    public static void main(String[] args) throws JSONException {
//        String pdfFilePath = "src/test/resources/bardlind-cv.pdf";
//        String text = ExtractText.extractTextFromPdf(pdfFilePath);
        String txtFilePath = "src/test/resources/cv-text.txt";
        String text = ExtractText.readText(txtFilePath);
        assertTrue(text.contains("Lind"));
        String token = System.getenv("OPENAI_API_KEY");
        if (isEmpty(token)) {
            throw new IllegalArgumentException("OPENAI_API_KEY environment variable is required");
        }

        long start = System.currentTimeMillis();
        try {
            Text2Json text2Json = new Text2Json(token);
            String jsonString = text2Json.queryGPT3(text);
            long end = System.currentTimeMillis();
            log.info("elapsed: " + (end - start) + " ms");
            assertFalse(isEmpty(jsonString));
            String expected = "{friends:[{id:123,name:\"Corby Page\"},{id:456,name:\"Carter Page\"}]}";
            JSONAssert.assertEquals(expected, jsonString, false);
        } catch (Exception e) {
            e.printStackTrace();
            long end = System.currentTimeMillis();
            log.info("elapsed: " + (end - start) + " ms");
        }
    }
}
