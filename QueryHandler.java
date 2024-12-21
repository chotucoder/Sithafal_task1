import java.util.List;

public class QueryHandler {
    private final EmbeddingGenerator embeddingGenerator;
    private final VectorDatabaseClient vectorDatabaseClient;

    public QueryHandler(EmbeddingGenerator embeddingGenerator, VectorDatabaseClient vectorDatabaseClient) {
        this.embeddingGenerator = embeddingGenerator;
        this.vectorDatabaseClient = vectorDatabaseClient;
    }

    public List<String> handleQuery(String query, int topK) {
        List<List<Double>> queryEmbedding = embeddingGenerator.generateEmbeddings(List.of(query));
        return vectorDatabaseClient.searchSimilarEmbeddings(queryEmbedding.get(0), topK);
    }
}
