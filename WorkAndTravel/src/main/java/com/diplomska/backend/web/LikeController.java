package com.diplomska.backend.web;

import com.diplomska.backend.helpers.LikeHelper;
import com.diplomska.backend.service.interfaces.LikeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/likeOrDislike")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    public void likeOrDislike(@RequestBody LikeHelper likeHelper){
        likeService.likeOrDislike(likeHelper);
    }



}
