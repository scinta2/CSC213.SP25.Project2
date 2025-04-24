package edu.canisius.csc213.complaints.controller;

import edu.canisius.csc213.complaints.model.Complaint;
import edu.canisius.csc213.complaints.service.ComplaintSimilarityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ComplaintController {

    private final List<Complaint> complaints;
    private final ComplaintSimilarityService similarityService;

    public ComplaintController(List<Complaint> complaints, ComplaintSimilarityService similarityService) {
        this.complaints = complaints;
        this.similarityService = similarityService;
    }

    @GetMapping("/complaint")
    public String showComplaint(@RequestParam(defaultValue = "0") String index, Model model) {
        int max = complaints.size();
        String error = null;
        int i = 0;
        try {
            i = Integer.parseInt(index);

        }
        catch(NumberFormatException e){
            i = 0;
            error = "Invalid index.";
        }
        if (i < 0){
            i = 0;
            error = "Negative number.";
        } 
        if (i >= max) {
            i = max - 1;
            error = "Too big.";
        }
        

        Complaint current = complaints.get(i);
        List<Complaint> similar = similarityService.findTop3Similar(current);

        model.addAttribute("complaint", current);
        model.addAttribute("similarComplaints", similar);
        model.addAttribute("prevIndex", i > 0 ? i - 1 : 0);
        model.addAttribute("nextIndex", i < max - 1 ? i + 1 : max - 1);
        model.addAttribute("error", error);

        return "complaint"; // ← This maps to complaint.html
    }
    @GetMapping("/search")
    public String showSearch(@RequestParam(defaultValue = "0") String company, Model model) {
        String error = null;
        int i = 0;

        ArrayList<Complaint> searchResults = new ArrayList<>();

        // Make a loop over complaints
        // If complaint  company is equal to the variable company, add to list

        for (i = 0; i < complaints.size(); ++i) {
            if (complaints.get(i).getCompany().toLowerCase().contains(company.toLowerCase())) {
                searchResults.add(complaints.get(i));
            }
        }
        if (company.equals("0")) {
            error = "Please enter a company name.";
        }
        if (searchResults.isEmpty()) {
            error = "No complaints found for that company.";
        }
        

        model.addAttribute("searchResults", searchResults);
        model.addAttribute("error", error);

        return "search"; // ← This maps to search.html
    }
}
