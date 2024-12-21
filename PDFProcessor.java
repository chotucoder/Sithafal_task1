import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFProcessor {
    public static List<String> extractChunks(String pdfFilePath) throws IOException {
        File pdfFile = new File(pdfFilePath);
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper textStripper = new PDFTextStripper();

        String text = textStripper.getText(document);
        document.close();

        return chunkText(text, 500); // Chunking into 500-character pieces
    }

    private static List<String> chunkText(String text, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        int length = text.length();

        for (int i = 0; i < length; i += chunkSize) {
            chunks.add(text.substring(i, Math.min(length, i + chunkSize)));
        }
        return chunks;
    }
}
