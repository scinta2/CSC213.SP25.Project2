package edu.canisius.csc213.complaints.service;

import edu.canisius.csc213.complaints.model.Complaint;

import java.util.Comparator;
import java.util.stream.Collectors;

import java.util.List;

public class ComplaintSimilarityService {

    private final List<Complaint> complaints;

    public ComplaintSimilarityService(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public List<Complaint> findTop3Similar(Complaint target) {
        // TODO: Return top 3 most similar complaints (excluding itself)
        return complaints.stream()
                .filter(complaint -> complaint.getComplaintId() != (target.getComplaintId()))
                .sorted(Comparator.comparingDouble(complaint -> -cosineSimilarity(target.getEmbedding(), complaint.getEmbedding())))
                .limit(3)
                .collect(Collectors.toList());
        //return List.of();
    }

    private double cosineSimilarity(double[] a, double[] b) {
        // TODO: Implement cosine similarity
        double dot = 0, normA = 0, normB = 0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
        //return 0.0;
    }

    private static class ComplaintWithScore {
        Complaint complaint;
        double score;

        ComplaintWithScore(Complaint c, double s) {
            this.complaint = c;
            this.score = s;
        }
    }
}
