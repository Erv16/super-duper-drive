package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getCredentialsByUserId(Integer userId) {
        return credentialMapper.getCredentialsByUserId(userId);
    }

    public int createCredential(Credential credential) {
        return credentialMapper.insert(credential);
    }

    public int updateCredential(Credential credential) {
        return credentialMapper.update(credential);
    }

    public Credential getCredentialByCredentialId(Integer credentialId) {
        return credentialMapper.getCredentialByCredentialId(credentialId);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    public String getKeyByUserId(Integer userId) {
        return credentialMapper.getKeyByUserId(userId);
    }
}
