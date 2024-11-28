package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileLoad;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    // Retrieve all files for a specific user
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<FileLoad> getFilesByUserId(int userid);

    // Retrieve all files for a specific user
    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    FileLoad getFilesByFileName(FileLoad file);

    // Retrieve a specific file by its ID
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    FileLoad getFileById(int fileId);

    // Insert a new file
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(FileLoad file);

    // Update an existing file
    @Update("UPDATE FILES SET filename = #{filename}, contenttype = #{contenttype}, filesize = #{filesize}, filedata = #{filedata} WHERE fileId = #{fileId}")
    int update(FileLoad file);

    // Delete a file by its ID
    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int delete(int fileId);
}
