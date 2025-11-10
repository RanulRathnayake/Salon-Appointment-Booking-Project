package com.salon.service.client;



import com.salon.payload.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("USER-SERVICE")
public interface UserFeignClient {


    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") long id) throws Exception ;


    @GetMapping("/api/user/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception ;
}
