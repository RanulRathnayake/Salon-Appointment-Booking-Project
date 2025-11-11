package com.salon.service;

import com.salon.modal.Review;
import com.salon.payload.dto.ReviewDTO;
import com.salon.payload.dto.SalonDTO;
import com.salon.payload.dto.UserDTO;

import javax.naming.AuthenticationException;
import java.util.List;

public interface ReviewService {

    Review createReview(ReviewDTO req,
                        UserDTO user,
                        SalonDTO salon);

    List<Review> getReviewsBySalonId(Long salonId);

    Review updateReview(Long reviewId,
                        String reviewText,
                        double rating,
                        Long userId) throws Exception, AuthenticationException;


    void deleteReview(Long reviewId, Long userId) throws Exception, AuthenticationException;

}
