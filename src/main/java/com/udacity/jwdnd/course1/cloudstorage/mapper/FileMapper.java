package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM files WHERE userid=#{userId}")
    List<File> getFilesByUserId(Integer userId);

    @Select("SELECT * FROM files WHERE filename=#{fileName}")
    File getFileByFileName(String fileName);

    @Insert("INSERT INTO files(filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(File file);

    @Select("SELECT * FROM files WHERE fileid=#{fileId}")
    File getFileByFileId(Integer fileId);

    @Delete("DELETE FROM files WHERE fileId=#{fileId}")
    void deleteFileByFileId(Integer fileId);
}
