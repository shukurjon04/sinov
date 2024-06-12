package com.example.sinov.Service;
import java.io.File;
import java.util.Date;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sinov.Repository.FilestorageRepository;
import com.example.sinov.entity.FileStorage;
import com.example.sinov.entity.enummration.FileStoragesTATUS;

@Service
public class FileService {

    private final FilestorageRepository filestorageRepository;

    private Hashids hashids;
    @Autowired
    public FileService(FilestorageRepository filestorageRepository) {
        this.filestorageRepository = filestorageRepository;
        this.hashids = new Hashids(getClass().getName(),6);
    }

    private String pathfile = "D:/yuklanmalar/backendFileUpload";

    public FileStorage save(MultipartFile file){
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(file.getOriginalFilename());
        fileStorage.setContentype(file.getContentType());
        fileStorage.setFileSize(file.getSize());
        fileStorage.setExtansion(getEXT(file.getOriginalFilename()));
        fileStorage.setStoragesTATUS(FileStoragesTATUS.DRAFT);
        filestorageRepository.save(fileStorage);
    

        Date now =  new Date();
        File uploadFolder = new File(this.pathfile + "/upload" + (1900+now.getYear()) + "/" + (1+now.getMonth()) + "/" + now.getDate());
        if (!uploadFolder.exists()&&uploadFolder.mkdirs()) {
            System.out.println("created folder");
        }
        fileStorage.setHashid(hashids.encode(fileStorage.getId()));
        fileStorage.setUploadFolder(this.pathfile + "/upload" + (1900+now.getYear()) + "/" + (1+now.getMonth()) + "/" + now.getDate()+"/"+fileStorage.getHashid()+"."+fileStorage.getExtansion());
        uploadFolder =uploadFolder.getAbsoluteFile();
        File file1 = new File(uploadFolder,String.format("%s.%s",fileStorage.getHashid(),fileStorage.getExtansion()));
        try {
            file.transferTo(file1);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return filestorageRepository.save(fileStorage);

    }

    public FileStorage findbyhashid(String hashid){
        return filestorageRepository.findByHashid(hashid);
    }

    public void delete(String hashid){
        FileStorage fileStorage = filestorageRepository.findByHashid(hashid);
        File file = new File(fileStorage.getUploadFolder());
        if(file.delete()){
            filestorageRepository.delete(fileStorage);
        }
    }
    private String getEXT(String name){
        String ext = null;
        if(name!=null&&!name.isEmpty()){
            int dot = name.lastIndexOf(".");
            if (dot>0&&dot<=name.length()-2) {
                return name.substring(dot+1);
            }
        }
        return ext;
    }


}
