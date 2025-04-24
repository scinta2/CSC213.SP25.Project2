package edu.canisius.csc213.complaints.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class EmbeddingLoader {

    /**
     * Loads complaint embeddings from a JSONL (newline-delimited JSON) file.
     * Each line must be a JSON object with:
     * {
     * "complaintId": <long>,
     * "embedding": [<double>, <double>, ...]
     * }
     *
     * @param jsonlStream InputStream to the JSONL file
     * @return A map from complaint ID to its embedding vector
     * @throws IOException if the file cannot be read or parsed
     */
    public static Map<Long, double[]> loadEmbeddings(InputStream jsonlStream) throws IOException {
        // TODO: Implement parsing of JSONL to extract complaintId and embedding

        Map<Long, double[]> embeddingsMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        String jsonString = "";

        Scanner scnr = new Scanner(jsonlStream);

        ArrayList<String> lines = new ArrayList<>();

        while (scnr.hasNextLine()) {
            jsonString += scnr.next();
            lines.add(scnr.nextLine());
        }
        scnr.close();

        // JsonNode json = mapper.readTree(jsonString);
        // System.out.println(json.get("id"));


        // System.out.println("{ \"id\": " + lines.get(0));
        for (String s : lines) {

            s = "{ \"id\": " + s;
        //     // JsonNode json = mapper.readTree(jsonString);
        //     // System.out.println(json.get("id"));
            

            JSONObject obj = new JSONObject(s);
            long id = obj.getLong("id");

            JSONArray arr = obj.getJSONArray("embedding");

            double[] list = new double[arr.length()];


            for(int i = 0; i < arr.length(); i++){
                list[i] = arr.getDouble(i);
                //System.out.println(list[i]);
            }

            embeddingsMap.put(id, list);
        }

        return embeddingsMap;
    }

}
