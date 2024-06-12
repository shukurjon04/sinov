package com.example.sinov.Controller;

import java.net.MalformedURLException;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import com.example.sinov.Service.FileService;
import com.example.sinov.entity.FileStorage;

@RestController
@RequestMapping("/file")
public class Filecontroller {

    private final FileService fileService;
    private FileStorage save;

    @Autowired
    public Filecontroller(FileService fileService) {
        this.fileService = fileService;
    }
    @GetMapping("/up")
    public String qale(){
        return "salom";
    }


    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file")MultipartFile file){
        FileStorage save = fileService.save(file);
        return ResponseEntity.ok(save);
    }
    @GetMapping("/view/{Hashid}")
    public ResponseEntity view(@PathVariable String Hashid) throws MalformedURLException{
        FileStorage fileStorage = fileService.findbyhashid(Hashid);
        return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,"inline; fileNmae\""+UriEncoder.encode(fileStorage.getName()))
        .contentType(org.springframework.http.MediaType.parseMediaType(fileStorage.getContentype()))
        .contentLength(fileStorage.getFileSize())
        .body(new FileUrlResource(fileStorage.getUploadFolder()));

    }

    @DeleteMapping("/delete/{hashid}")
    public ResponseEntity delete(@PathVariable String hashid)
    {
        fileService.delete(hashid);
        return ResponseEntity.ok("success");
    }


}
