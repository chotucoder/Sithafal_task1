import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import java.util.List;

public class ResponseGenerator {
    private final OpenAiService openAiService;

    public ResponseGenerator(String apiKey) {
        this.openAiService = new OpenAiService(apiKey);
    }

    public String generateResponse(String query, List<String> relevantChunks) {
        String context = String.join("\n", relevantChunks);
        String prompt = "Context:\n" + context + "\n\nAnswer the following query: " + query;

        CompletionRequest request = CompletionRequest.builder()
            .model("text-davinci-003")
            .prompt(prompt)
            .maxTokens(500)
            .build();

        CompletionResult result = openAiService.createCompletion(request);
        return result.getChoices().get(0).getText().trim();
    }
}
