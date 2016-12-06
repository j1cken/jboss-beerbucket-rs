package com.redhat.hackathon.rs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.WriteAbortedException;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.util.Base64;

/**
 * Created by torben on 12/6/16.
 */
public class BucketService {

	AmazonS3 s3client = null;

	AmazonS3 getS3Client() {
		if (s3client == null) {
			AWSCredentials credentials = new BasicAWSCredentials(System.getenv("accessKey"),
					System.getenv("secretKey"));
			s3client = new AmazonS3Client(credentials);
		}
		return s3client;
	}

	public boolean createBucket(String email) {
		System.out.println("creating bucket for user " + email);
		System.out.println("key:  "+formatEmail(email));
		try {
			getS3Client().createBucket(formatEmail(email));
		} catch (SdkClientException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	String formatEmail(String email) {
		return email.replace("@", "at");
	}
	
	
	public boolean put(String bucketname, String key, String base64encoded) throws IOException {

		final byte[] byteArray = Base64.getMimeDecoder().decode(base64encoded);
		
		File file = new File(key);
		BufferedOutputStream writer = null;
		try {
			writer = new BufferedOutputStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		writer.write(byteArray);
		writer.flush();
		writer.close();

		System.out.println("creating object " + file.getName());
		System.out.println("key:"+key);
		System.out.println("bucketname"+formatEmail(bucketname));
		System.out.println("content"+new String(byteArray));
		getS3Client().putObject(new PutObjectRequest(formatEmail(bucketname), key, file));

		return true;
	}

	public String get(String bucketName, String key) {
		System.out.println("get object with key " + key);
		System.out.println("bucketname:"+ formatEmail(bucketName));
		S3Object s3Objec = getS3Client().getObject(new GetObjectRequest(formatEmail(bucketName), key));
		return s3Objec.getObjectContent().toString();
	}

	public void delete(String bucketName, String key) {
		System.out.println("delete object with key " + key);
		System.out.println("bucketname:"+ bucketName);
	
		getS3Client().deleteObject(formatEmail(bucketName), key);
	}

}
