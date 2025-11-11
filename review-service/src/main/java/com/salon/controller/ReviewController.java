package com.salon.controller;


import com.salon.modal.Review;
import com.salon.payload.dto.ApiResponse;
import com.salon.payload.dto.ReviewDTO;
import com.salon.payload.dto.SalonDTO;
import com.salon.payload.dto.UserDTO;
import com.salon.service.ReviewService;
import com.salon.service.client.SalonFeignClient;
import com.salon.service.client.UserFeignClient;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
//Directly pass review modal as a response must be changed in to reviewDTO. ADD mapper function.

    private final ReviewService reviewService;
    private final UserFeignClient userFeignClient;
    private final SalonFeignClient salonFeignClient;


    @PostMapping("/salon/{salonId}")
    public ResponseEntity<Review> writeReview(
            @RequestBody ReviewDTO req,
            @PathVariable Long salonId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        Review review = reviewService.createReview(req, user, salon);

        return ResponseEntity.ok(review);
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<Review>> getReviewsBySalonId(
            @PathVariable Long salonId) {

        List<Review> reviews = reviewService.getReviewsBySalonId(salonId);

        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @RequestBody ReviewDTO req,
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        Review review = reviewService.updateReview(reviewId, req.getReviewText(), req.getRating(), user.getId());

        return ResponseEntity.ok(review);
    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        reviewService.deleteReview(reviewId, user.getId());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Review deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

}
