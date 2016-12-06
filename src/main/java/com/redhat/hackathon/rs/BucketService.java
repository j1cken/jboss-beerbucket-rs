package com.redhat.hackathon.rs;

/**
 * Created by torben on 12/6/16.
 */
public class BucketService {

    boolean register(String email) {
        System.out.println("registering");
        return false;
    }

    String put(Object o) {
        System.out.println("creating object " + o);
        return null;
    }

    Object get(String objectId) {
        System.out.println("retrieving object " + objectId);
        return null;
    }

    boolean delete(String objectId) {
        System.out.println("deleting object " + objectId);
        return false;
    }

}
