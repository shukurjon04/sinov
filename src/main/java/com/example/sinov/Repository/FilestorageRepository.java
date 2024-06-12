package com.example.sinov.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sinov.entity.FileStorage;
import java.util.List;

@Repository
public interface FilestorageRepository extends JpaRepository<FileStorage,Long>{
    FileStorage findByHashid(String hashid);

}
