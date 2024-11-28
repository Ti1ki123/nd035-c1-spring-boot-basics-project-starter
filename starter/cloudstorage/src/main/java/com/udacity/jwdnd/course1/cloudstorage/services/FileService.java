package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.FileLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    // Retrieve all files for a specific user
    public List<FileLoad> getFilesByUserId(int userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    // Retrieve a specific file by its filename
    public FileLoad getFileByFileName(FileLoad file) {
        return fileMapper.getFilesByFileName(file);
    }

    // Retrieve a specific file by its ID
    public FileLoad getFileById(int fileId) {
        return fileMapper.getFileById(fileId);
    }

    // Add a new file
    public int addFile(FileLoad file) {
        return fileMapper.insert(file);
    }

    // Update an existing file
    public int updateFile(FileLoad file) {
        return fileMapper.update(file);
    }

    // Delete a file by its ID
    public int deleteFile(int fileId) {
        return fileMapper.delete(fileId);
    }
}
