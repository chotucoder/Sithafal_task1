import java.io.IOException;
import java.util.List;

public class Index{
    public static void main(String[] args) {
        String pdfPath = "path/to/your/pdf/file.pdf"; // Change this to your PDF file path
        String openAiApiKey = "your-openai-api-key"; // Your OpenAI API key

        try {
            // Step 1: Extract text chunks from PDF
            List<String> chunks = PDFProcessor.extractChunks(pdfPath);

            // Step 2: Generate embeddings for the chunks
            EmbeddingGenerator embeddingGenerator = new EmbeddingGenerator(openAiApiKey);
            List<List<Double>> embeddings = embeddingGenerator.generateEmbeddings(chunks);

            // Step 3: Store embeddings in the vector database
            VectorDatabaseClient vectorDatabaseClient = new VectorDatabaseClient();
            for (int i = 0; i < chunks.size(); i++) {
                vectorDatabaseClient.storeEmbedding("chunk-" + i, embeddings.get(i));
            }

            // Step 4: Handle user query
            QueryHandler queryHandler = new QueryHandler(embeddingGenerator, vectorDatabaseClient);
            List<String> relevantChunks = queryHandler.handleQuery("What is the unemployment rate for a Master's degree?", 3);

            // Step 5: Generate response using the retrieved chunks
            ResponseGenerator responseGenerator = new ResponseGenerator(openAiApiKey);
            String response = responseGenerator.generateResponse("What is the unemployment rate for a Master's degree?", relevantChunks);

            // Output the response
            System.out.println("Response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
