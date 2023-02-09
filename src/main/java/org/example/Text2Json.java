package org.example;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

public class Text2Json {
    //connect to openai's gpt3 api

    public static final String QUESTION = "Summarize the text below into a JSON with exactly the following structure {basic_info: {first_name, last_name, full_name, email, phone_number, location, portfolio_website_url, linkedin_url, github_main_page_url, university, education_level (BS, MS, or PhD), graduation_year, graduation_month, majors, GPA}, work_experience: [{job_title, company, location, duration: {start, end}, job_summary}], project_experience:[{project_name, duration: {start, end}, project_discription}]}";

    public String queryGPT3(String text) {
        //read OPENAI_TOKEN from environment variable

        text = cleanup(text);
        OpenAiService service = new OpenAiService("OPENAI_API_KEY");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("Somebody once told me the world is gonna roll me")
                .model("ada")
                .echo(true)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        //query gpt3
        //return the response
        return null;
    }

    public static void main(String[] args) {
        String token = System.getenv("OPENAI_API_KEY");
        if (isEmpty(token)) {
            throw new IllegalArgumentException("OPENAI_API_KEY environment variable is required");
        }
        OpenAiService service = new OpenAiService(token);

        System.out.println("\nCreating completion...");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("ada")
                .prompt("Somebody once told me the world is gonna roll me")
                .echo(true)
                .user("testing")
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
    }

    private static boolean isEmpty(String token) {
        return token == null || token.isEmpty();
    }

    protected String cleanup(String text) {
        //remove all newlines
        text = text.replaceAll("\\r\\n|\\r|\\n", " ");
        //remove all tabs
        text = text.replaceAll("\\t", " ");
        //remove all multiple spaces
        text = text.replaceAll("\\s+", " ");
        //remove all leading and trailing spaces
        text = text.trim();
//        pdf_str = "\n\n".join(pdf)
//        pdf_str = re.sub('\s[,.]', ',', pdf_str)
//        pdf_str = re.sub('[\n]+', '\n', pdf_str)
//        pdf_str = re.sub('[\s]+', ' ', pdf_str)
//        pdf_str = re.sub('http[s]?(://)?', '', pdf_str)
        return text;

    }

    //    Base function for querying GPT-3.
    //        Send a request to GPT-3 with the passed-in function parameters and return the response object.

}
