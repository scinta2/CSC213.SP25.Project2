package edu.canisius.csc213.complaints.storage;

import edu.canisius.csc213.complaints.model.Complaint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComplaintMerger {

    /**
     * Matches complaints to their corresponding embedding vectors by complaint ID.
     *
     * @param complaints List of complaints (from CSV)
     * @param embeddings Map from complaintId to embedding vector (from JSONL)
     */
    public static void mergeEmbeddings(List<Complaint> complaints, Map<Long, double[]> embeddings) {
        // TODO: For each complaint, match the ID to an embedding and set it
        //List<Complaint> mergedList = new ArrayList<>();
        //  complaints.forEach(complaint -> {
        //     String complaintText = complaint.getText();
        //     List<Double> embedding = embeddings.get(complaintText);
        //      mergedList.add(new ComplaintWithEmbeddings(complaint.getId(), complaintText, embedding));
        // });
        for (Complaint complaint : complaints) {
            long id = complaint.getComplaintId();
            double[] embedding = embeddings.get(id);

            System.out.println("id: " + id + ", embedding: " + embeddings.get(id));
            if (embedding != null) {
                complaint.setEmbedding(embedding);
            }
        }
    }
    

}
