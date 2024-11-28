package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private EncryptionService encryptionService;

    // Retrieve all credentials for a specific user
    public List<Credential> getCredentialsByUserId(int userId) {
        List<Credential> credentials = credentialMapper.getCredentialByUserid(userId);

        for (Credential credential : credentials) {
            credential.setPasswordReal(encryptionService.decryptValue(credential.getPassword(),credential.getKey()));
        }

        return credentials;
    }

    // Retrieve a specific credential by its ID
    public Credential getCredentialById(int credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    // Add a new credential
    public int addCredential(Credential credential) {
        return credentialMapper.insert(credential);
    }

    // Update an existing credential
    public int updateCredential(Credential credential) {
        return credentialMapper.update(credential);
    }

    // Delete a credential
    public int deleteCredential(int credentialId) {
        return credentialMapper.delete(credentialId);
    }
}