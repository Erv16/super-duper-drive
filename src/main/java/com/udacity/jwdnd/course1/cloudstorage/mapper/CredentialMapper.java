package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM credentials WHERE userid=#{userId}")
    List<Credential> getCredentialsByUserId(Integer userId);

    @Insert("INSERT INTO credentials (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Update("UPDATE credentials SET url=#{url}, username=#{username}, key=#{key}, password=#{password} WHERE credentialid=#{credentialId}")
    int update(Credential credential);

    @Select("SELECT * FROM credentials WHERE credentialid=#{credentialId}")
    Credential getCredentialByCredentialId(Integer credentialId);

    @Delete("DELETE FROM credentials WHERE credentialid=#{credentialId}")
    void deleteCredential(Integer credentialId);

    @Select("SELECT key FROM credentials WHERE userid=#{userId}")
    String getKeyByUserId(Integer userId);
}
