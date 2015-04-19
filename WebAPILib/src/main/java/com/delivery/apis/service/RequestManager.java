package com.delivery.apis.service;

import com.delivery.apis.params.Parameters;
import com.delivery.apis.response.ResponseData;

import java.io.File;
import java.util.HashMap;

/**
 * Created by s on 19-04-2015.
 */
public class RequestManager {

    Parameters Params;
    RestServiceImpl service;
    String baseUrl;
    HashMap<String, String> urlParams;
    String postContent;
    File file;

    public RequestManager(String baseUrl,HashMap<String, String> headers){
        service = new RestServiceImpl(headers);
        this.baseUrl = baseUrl;
        urlParams = new HashMap<String, String>();
    }


    public RequestManager(String baseUrl,HashMap<String, String> headers,String postContent){
        service = new RestServiceImpl(headers);
        this.baseUrl = baseUrl;
        urlParams = new HashMap<String, String>();
        this.postContent = postContent;
    }
    public RequestManager(String baseUrl,HashMap<String, String> urlParams,HashMap<String, String> headers){
        service = new RestServiceImpl(headers);
        this.baseUrl = baseUrl;
        this.urlParams = urlParams;
    }

    public RequestManager(String baseUrl,HashMap<String, String> headers,File file){
        service = new RestServiceImpl(headers);
        this.baseUrl = baseUrl;
        this.file = file;
    }

    public ResponseData executeRequest() throws APIException {
        String postContent = new PostXmlCreator().createPostXml(Params);
        return service.fireHttpPost(baseUrl, urlParams, postContent, "application/xml");
    }


    public ResponseData executeEmbededParamsRequest() throws APIException {
        return service.fireHttpPost(baseUrl, urlParams, postContent, "application/xml");
    }

    public ResponseData executeMultipartRequestForImageUpload() throws APIException {
        return service.fireHttpMultipartImagePost(baseUrl,file);
    }

    public ResponseData executeUrlEmbeddedParamsGetRequest() throws APIException{
        return service.fireHttpGet(baseUrl, urlParams);
    }


}
