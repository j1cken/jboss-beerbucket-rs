/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.hackathon.rs;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.io.IOException;

/**
 * A simple REST service which provides operations on S3 buckets.
 *
 * @author gbrey@redhat.com
 */

@Path("/")
public class BucketEndpoint {
    @Inject
    BucketService bucketService;

    @PUT
    @Path("/register")
    @Produces({"application/json"})
    public String register(@DefaultValue("xyz@abc.com") @QueryParam("email") String email) {
        return "{\"result\":\"" + bucketService.createBucket(email) + "\"}";
    }

    @POST
    @Path("/put")
    @Produces({ "application/json" })
    @Consumes({ "application/x-www-form-urlencoded"})
    public boolean put(@QueryParam("bucketName") String bucketname, @QueryParam("key") String key,  @FormParam("base64") String base64){
    	try {
    		return bucketService.put(bucketname, key, base64.replace(" ", "+"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
    }

    @GET
    @Path("/get")
    @Produces({"application/json"})
    public String get(@QueryParam("bucketName") String bucketname, @QueryParam("key") String key) {

        return bucketService.get(bucketname, key);

    }


    @DELETE
    @Path("/delete")
    @Produces({"application/json"})
    public void delete(@QueryParam("bucketName") String bucketname, @QueryParam("key") String key) {

        bucketService.delete(bucketname, key);

    }
}
