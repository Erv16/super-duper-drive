package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFilesByUserId(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    public File getFileByFileName(String fileName) {
        return fileMapper.getFileByFileName(fileName);
    }

    public int uploadFile(MultipartFile fileToUpload, Integer userId) throws IOException, FileSizeLimitExceededException {
        if(fileToUpload.getSize() > 10000000) {
            throw new FileSizeLimitExceededException("File is too large", fileToUpload.getSize(), 10000000);
        }
        File file = new File();
        file.setFileName(fileToUpload.getOriginalFilename());
        file.setContentType(fileToUpload.getContentType());
        file.setFileSize(String.valueOf(fileToUpload.getSize()));
        file.setFileData(fileToUpload.getBytes());
        file.setUserId(userId);
        return fileMapper.uploadFile(file);
    }

    public File getFileByFileId(Integer fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFileByFileId(fileId);
    }
}
