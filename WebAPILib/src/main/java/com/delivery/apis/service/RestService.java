package com.delivery.apis.service;

import com.delivery.apis.response.ResponseData;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by s on 19-04-2015.
 */
    public interface RestService {

        public ResponseData fireHttpGet(String baseUrl, HashMap<String, String> params) throws APIException;
        public ResponseData fireHttspGet(String baseUrl, HashMap<String, String> params) throws APIException;
        public ResponseData fireHttpPost(String baseUrl, HashMap<String, String> params, String postContent, String contentType) throws APIException;

        public ResponseData fireHttpMultipartImagePost(String baseUrl, File file)throws APIException;
    }

