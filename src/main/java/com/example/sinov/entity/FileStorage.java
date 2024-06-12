package com.example.sinov.entity;

import java.io.StringReader;

import com.example.sinov.entity.enummration.FileStoragesTATUS;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class FileStorage {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String extansion;

    private Long FileSize;

    private String uploadFolder;

    private String Contentype;

    private String hashid;

    private FileStoragesTATUS storagesTATUS;

   


}
