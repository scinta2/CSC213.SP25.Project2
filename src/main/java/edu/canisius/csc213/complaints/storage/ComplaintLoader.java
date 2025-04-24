package edu.canisius.csc213.complaints.storage;

import com.opencsv.bean.CsvToBeanBuilder;
import edu.canisius.csc213.complaints.model.Complaint;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.Reader;

/**
 * Handles loading of complaints and embedding data,
 * and returns a fully hydrated list of Complaint objects.
 */
public class ComplaintLoader {

    /**
     * Loads complaints from a CSV file and merges with embedding vectors from a JSONL file.
     *
     * @param csvPath    Resource path to the CSV file
     * @param jsonlPath  Resource path to the JSONL embedding file
     * @return A list of Complaint objects with attached embedding vectors
     * @throws Exception if file reading or parsing fails
     */
    public static List<Complaint> loadComplaintsWithEmbeddings(String csvPath, String jsonlPath) throws Exception {
        // TODO: Load CSV and JSONL resources, parse, and return hydrated Complaint list
        /*List<Complaint> complaints = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            String[] line;
            reader.skip(1);
            while ((line = reader.readNext()) != null) {
                 complaints.add(new Complaint());
            }
        }
        return complaints;*/
        Reader reader = new InputStreamReader(ComplaintLoader.class.getResourceAsStream(csvPath),StandardCharsets.UTF_8);
        List<Complaint> r = new CsvToBeanBuilder<Complaint>(reader)
        .withType(Complaint.class)
        .withIgnoreLeadingWhiteSpace(true)
        .build()
        .parse();

        reader.close();

        // for(Complaint c : r){
        //     System.out.println(c);
        // }
        InputStream is = ComplaintLoader.class.getResourceAsStream(jsonlPath);
        
        ComplaintMerger.mergeEmbeddings(r, EmbeddingLoader.loadEmbeddings(is));

        return r;
        //return List.of(); // placeholder
    }
}
