package org.example;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import org.slf4j.Logger;
import retrofit2.HttpException;

import static org.example.Resyme2Json.isEmpty;
import static org.slf4j.LoggerFactory.getLogger;

public class Text2Json {
    //connect to openai's gpt3 api
    private static final Logger log = getLogger(Text2Json.class);

    private final String OPENAI_API_KEY;

    public static final String QUESTION = "Summarize the text below into a JSON with exactly the following structure {basic_info: {first_name, last_name, full_name, email, phone_number, location, portfolio_website_url, linkedin_url, github_main_page_url, university, education_level (BS, MS, or PhD), graduation_year, graduation_month, majors, GPA}, work_experience: [{job_title, company, location, duration: {start, end}, job_summary}], project_experience:[{project_name, duration: {start, end}, project_discription}]}";

    public Text2Json(String openaiApiKey) {
        OPENAI_API_KEY = openaiApiKey;
    }

    public String queryGPT3(String text, boolean doCleanup) {
        //read OPENAI_TOKEN from environment variable
        String question = "Summarize the text below into a JSON with exactly the following structure {basic_info: {first_name, last_name, full_name, email, phone_number, location, portfolio_website_url, linkedin_url, github_main_page_url, university, education_level (BS, MS, or PhD), graduation_year, graduation_month, majors, GPA}, work_experience: [{job_title, company, location, duration: {start, end}, job_summary}], project_experience:[{project_name, duration: {start, end}, project_discription}]}";
        if (doCleanup) {
            text = cleanup(text);
        }
        log.debug("text: {}", text);
        if (isEmpty(text)) {
            log.warn("text is empty");
            return "";
        }
        String prompt = question + "\n" + text;
        int maxTokens = 1800;
        int estimatedPromptTokens = calculateTokens(text); //see https://platform.openai.com/tokenizer (int) (text.split(" ").length * 1.6);
        log.debug("estimated prompt tokens: {}", estimatedPromptTokens);

        int estimatedAnswerTokens = 4000 - estimatedPromptTokens;
        if (estimatedAnswerTokens < maxTokens) {
            log.warn("estimated_answer_tokens lower than max_tokens, changing max_tokens to {}", estimatedAnswerTokens);
            maxTokens = estimatedAnswerTokens;
        }
        log.debug("estimated_answer_tokens: {}", estimatedAnswerTokens);
        String engine = "text-davinci-002";
        double temperature = 0.0;
        double topP = 1;
        double frequencyPenalty = 0;
        double presencePenalty = 0;

        /*
         engine: str = 'text-curie-001',
                        temperature: float = 0.0,
                        max_tokens: int = 100,
                        top_p: int = 1,
                        frequency_penalty: int = 0,
                        presence_penalty: int = 0
         */

//        maxTokens = 1400; //4096 - estimatedPromptTokens; //1600; //min(4096-estimatedPromptTokens, maxTokens);
        log.debug("maxTokens: {}", maxTokens);
        OpenAiService service = new OpenAiService(OPENAI_API_KEY, 60);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .model(engine)
                .temperature(temperature)
                .topP(topP)
                .frequencyPenalty(frequencyPenalty)
                .presencePenalty(presencePenalty)
                .maxTokens(maxTokens)
                .build();
        try {
            service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        } catch (HttpException e) {
            log.error("error: {}", e.getMessage());
        }
        /*
        List<CompletionChoice> responseChoices = service.createCompletion(completionRequest).getChoices(); //.forEach(System.out::println);
        //query gpt3
        //return the response
        for (CompletionChoice responseChoice : responseChoices) {
            String response = responseChoice.getText();
            log.debug("response: {}", response);
            if (response.contains("basic_info")) {
                return response;
            }
        }

         */
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



    protected String cleanup(String text) {
//        text = "\n\n".join(text);
        text = text.replaceAll("\s[,.]", ",");
        text = text.replaceAll("[\n]+", " ");
        text = text.replaceAll("[\s]+", " ");
        text = text.replaceAll("http[s]?(://)?", "");
        text = text.replaceAll("[^A-Za-z0-9\s]", "b");
        return text;
    }

    public static int calculateTokens(String text) {

//        StringTokenizer str_arr = new StringTokenizer(text, " ");
//            int count = str_arr.countTokens();
//            return count;

        return (int) (text.split(" ").length * 1.6);
    }

    //    Base function for querying GPT-3.
    //        Send a request to GPT-3 with the passed-in function parameters and return the response object.

}
