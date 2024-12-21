import java.util.*;

public class VectorDatabaseClient {
    private final Map<String, List<Double>> vectorDatabase = new HashMap<>();

    public void storeEmbedding(String id, List<Double> embedding) {
        vectorDatabase.put(id, embedding);
    }

    public List<String> searchSimilarEmbeddings(List<Double> queryEmbedding, int topK) {
        Map<String, Double> similarityScores = new HashMap<>();

        for (Map.Entry<String, List<Double>> entry : vectorDatabase.entrySet()) {
            String chunkId = entry.getKey();
            List<Double> chunkEmbedding = entry.getValue();
            double similarity = cosineSimilarity(queryEmbedding, chunkEmbedding);
            similarityScores.put(chunkId, similarity);
        }

        return similarityScores.entrySet().stream()
            .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
            .limit(topK)
            .map(Map.Entry::getKey)
            .toList();
    }

    private double cosineSimilarity(List<Double> vec1, List<Double> vec2) {
        double dotProduct = 0.0, normA = 0.0, normB = 0.0;
        for (int i = 0; i < vec1.size(); i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
            normA += Math.pow(vec1.get(i), 2);
            normB += Math.pow(vec2.get(i), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
