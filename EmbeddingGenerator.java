import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.embedding.EmbeddingResult;
import java.util.ArrayList;
import java.util.List;

public class EmbeddingGenerator {
    private final OpenAiService openAiService;

    public EmbeddingGenerator(String apiKey) {
        this.openAiService = new OpenAiService(apiKey);
    }

    public List<List<Double>> generateEmbeddings(List<String> chunks) {
        List<List<Double>> embeddings = new ArrayList<>();

        for (String chunk : chunks) {
            EmbeddingRequest request = EmbeddingRequest.builder()
                .model("text-embedding-ada-002")
                .input(chunk)
                .build();
            EmbeddingResult result = openAiService.createEmbeddings(request);
            embeddings.add(result.getData().get(0).getEmbedding());
        }

        return embeddings;
    }
}
