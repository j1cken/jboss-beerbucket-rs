package com.redhat.hackathon.rs;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Created by torben on 12/6/16.
 */
public class BucketService {

    public boolean createBucket(String email) {
        System.out.println("creating bucket for user " + email);
        try {
            AWSCredentials credentials = new BasicAWSCredentials(System.getenv("accessKey"), System.getenv("secretKey"));
            AmazonS3 s3client = new AmazonS3Client(credentials);
            s3client.createBucket(email.replace("@","at"));
        } catch (SdkClientException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String put(Object o) {
        System.out.println("creating object " + o);
        return null;
    }

    public Object get(String objectId) {
        System.out.println("retrieving object " + objectId);
        return null;
    }

    public boolean delete(String objectId) {
        System.out.println("deleting object " + objectId);
        return false;
    }

}
