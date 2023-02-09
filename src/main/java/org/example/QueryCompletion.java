package org.example;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class QueryCompletion {
    private static final Logger logger = getLogger(QueryCompletion.class);
    public static void doNothing() {
        logger.info("doing nothing");
    }
    /*
    public static Completion queryCompletion(OpenAI openai,
                                             String prompt,
                                             String engine,
                                             double temperature,
                                             int maxTokens,
                                             int topP,
                                             int frequencyPenalty,
                                             int presencePenalty) {
        logger.info("query_completion: using {}", engine);
        int estimatedPromptTokens = (int) (prompt.split(" ").length * 1.6);
        logger.info("estimated prompt tokens: {}", estimatedPromptTokens);
        int estimatedAnswerTokens = 2049 - estimatedPromptTokens;
        if (estimatedAnswerTokens < maxTokens) {
            logger.warn("estimated_answer_tokens lower than max_tokens, changing max_tokens to {}", estimatedAnswerTokens);
        }
        Completion response = openai.completions().create(
                engine,
                prompt,
                temperature,
                Math.min(4096 - estimatedPromptTokens, maxTokens),
                topP,
                frequencyPenalty,
                presencePenalty
        );
        return response;
    }
}/

     */
}
