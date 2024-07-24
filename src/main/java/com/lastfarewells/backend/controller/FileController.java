package com.lastfarewells.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/file")
@AllArgsConstructor
public class FileController {

    @PostMapping("/upload")
    public String getUploadUrl(@RequestParam(required = false) Boolean isNew) {
        // Body
        //{"directory":"audios","fileType":"audio/mpeg","name":"Recorded Audio","size":"82705","extension":"mpga"}
        return "Presigned URL";
    }


}
