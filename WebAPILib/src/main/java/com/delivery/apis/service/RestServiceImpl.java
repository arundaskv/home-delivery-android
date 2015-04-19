package com.delivery.apis.service;

import android.util.Log;


import com.delivery.apis.response.ResponseData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public class RestServiceImpl implements RestService {
	
	private HashMap<String, String> headers  = null;
	public RestServiceImpl(){}
	
	public RestServiceImpl(HashMap<String, String> headers){
		this.headers = headers;
	}

	// When a HTTP Get Method is used

    public ResponseData fireHttpGet(String baseUrl, HashMap<String, String> params) throws APIException {
        InputStream inputStream = null;
        String completeUrl = createUrl(baseUrl, params);
        Log.d("", completeUrl);
        try {
            HttpGet httpGet = new HttpGet(completeUrl);
            addHeaders(httpGet);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpGet);
            inputStream = response.getEntity().getContent();
            ResponseData responseData = new  ResponseData();
            responseData.setInputStream(inputStream);
            responseData.setStatuscode(response.getStatusLine().getStatusCode());
            return responseData;

        } catch (ClientProtocolException e) {
            throw new APIException(e.getMessage());
        } catch (IOException e) {
            throw new APIException(e.getMessage());
        }

    }



	// When a HTTP Post Method is used
	public ResponseData fireHttpPost(String baseUrl, HashMap<String, String> params,String postContent, String contentType)	throws APIException {
		HttpPost httppost = null;
		InputStream inputStream = null;
		StringEntity se = null;
		HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
        HttpConnectionParams.setSoTimeout(httpParameters, 30000);
		HttpClient httpclient = new DefaultHttpClient(httpParameters);
		String completeUrl = createUrl(baseUrl, params);
		Log.d("", completeUrl);
		httppost = new HttpPost(completeUrl);
		// add headers to the http post request
		addHeaders(httppost);
		Log.d("", "Post content - " + postContent);

		try {
			se = new StringEntity(postContent);
			se.setContentType(contentType);
			httppost.setEntity(se);
			HttpResponse httpresponse = httpclient.execute(httppost);
			HttpEntity resEntity = httpresponse.getEntity();
			inputStream = resEntity.getContent();			
			String resp  = convertStreamToString(inputStream);
			Log.d("", "server response");
			Log.d("", resp);
			inputStream =  new ByteArrayInputStream(resp.getBytes());
			ResponseData responseData = new  ResponseData();
			responseData.setInputStream(inputStream);
			responseData.setStatuscode(httpresponse.getStatusLine().getStatusCode());
			return responseData;

		} catch (ClientProtocolException e) {
			throw new APIException("Error in HTTP Protocol");
		}
		catch (ConnectTimeoutException e) {
			throw new APIException("Connection Timeout");
		}
		catch (SocketTimeoutException e) {
			throw new APIException("Request Timeout");
		}
		catch (IOException e) {
			throw new APIException("There was a problem connecting to the server. Please check your connection settings or contact Network Administrator");
		}
		
		
	}

    @Override
    public ResponseData fireHttpMultipartImagePost(String baseUrl, File file) throws APIException{
        HttpPost httppost = null;
        InputStream inputStream = null;
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
        HttpConnectionParams.setSoTimeout(httpParameters, 30000);
        HttpClient httpclient = new DefaultHttpClient(httpParameters);
        Log.d("", "image upload completeUrl = " + baseUrl);
        httppost = new HttpPost(baseUrl);
        addHeadersToMultipartRequest(httppost);
        try {

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            String fileName = file.getName().split("\\.")[0];

            entityBuilder.addTextBody("filename",fileName, ContentType.TEXT_PLAIN);
            entityBuilder.addBinaryBody("file", file, ContentType.create("image/png"),file.getName().toString());
            HttpEntity entity = entityBuilder.build();

            httppost.setEntity(entity);

            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity resEntity = httpresponse.getEntity();
            inputStream = resEntity.getContent();
            String resp  = convertStreamToString(inputStream);
            Log.d("", "server response");
            Log.d("", resp);
            inputStream =  new ByteArrayInputStream(resp.getBytes());
            ResponseData responseData = new  ResponseData();
            responseData.setInputStream(inputStream);
            responseData.setStatuscode(httpresponse.getStatusLine().getStatusCode());
            return responseData;
        } catch (ClientProtocolException e) {
            throw new APIException("Error in HTTP Protocol");
        }
        catch (ConnectTimeoutException e) {
            throw new APIException("Connection Timeout");
        }
        catch (SocketTimeoutException e) {
            throw new APIException("Request Timeout");
        }
        catch (IOException e) {
            throw new APIException("There was a problem connecting to the server. Please check your connection settings or contact Network Administrator");
        }
    }

    public Object fireHttspPost(String baseUrl, HashMap<String, String> params, String postContent) throws APIException {
		return null;
	}

	

	// Add the headers to the Request
	private void addHeaders(HttpRequestBase request){
		if(headers!=null){
			Iterator<Entry<String,String>> it = headers.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> pair = (Entry<String, String>)it.next();
				request.setHeader(pair.getKey(), pair.getValue());
			}
		}
	}

    private void addHeadersToMultipartRequest(HttpRequestBase request){
        if(headers!=null){
            Iterator<Entry<String,String>> it = headers.entrySet().iterator();
            while(it.hasNext()){
                Entry<String, String> pair = (Entry<String, String>)it.next();
                request.setHeader(pair.getKey(), pair.getValue());

            }
        }
    }



	private String convertStreamToString(InputStream is) {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    try{
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }

	    is.close();
	    }catch(Exception e){
	    	
	    }

	    return sb.toString();
	}
	
	// Creates the URL
	private String createUrl(String baseUrl, HashMap<String, String> params) {
		StringBuffer url = new StringBuffer(baseUrl);
		for (Entry<String, String> entry : params.entrySet()) {
			url.append(entry.getKey());
			url.append("=");
			String value = URLEncoder.encode(entry.getValue());
			url.append(value);
			url.append("&");
		}
		url.deleteCharAt(url.length() - 1);
		return url.toString();

	}

	@Override
	public ResponseData fireHttspGet(String baseUrl,
			HashMap<String, String> params) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

}
