package com.delivery.apis.service;

/**
 * Created by s on 19-04-2015.
 */
public abstract class ServerOperations {
    public RequestManager Request  = null;
    public  void executeRequest() throws APIException{
        if(Request==null){
            // Request manager has not been instantiated
            throw new APIException("Request has not been instantiated");
        }
        if(Request.Params==null){
            // params has not been set
            throw new APIException("Parameters to the Request has not been set");
        }
        execute();
    }
    public  void executeGetRequest() throws APIException{
        if(Request==null){
            // Request manager has not been instantiated
            throw new APIException("Request has not been instantiated");
        }
        execute();
    }

    public  void executePostBackRequest() throws APIException{
        if(Request==null){
            // Request manager has not been instantiated
            throw new APIException("Request has not been instantiated");
        }
        execute();
    }

    public  void executeMultipartMediaPostRequest() throws APIException{
        if(Request==null){
            // Request manager has not been instantiated
            throw new APIException("Request has not been instantiated");
        }
        execute();
    }

    protected abstract void execute() throws APIException;

}
