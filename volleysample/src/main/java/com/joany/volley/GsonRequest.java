package com.joany.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by joany on 2016/8/8.
 */
public class GsonRequest<T> extends Request<T>{

    private Response.Listener<T> listener;
    private Gson gson;
    private Class<T> clazz;

    public GsonRequest(String url, Class<T> clazz,
                       Response.Listener<T> listener,Response.ErrorListener errorListener) {
        this(Method.GET,url,clazz,listener,errorListener);
    }

    public GsonRequest(int method, String url, Class<T> clazz,
                       Response.Listener<T> listener,Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.listener = listener;
        this.gson = new Gson();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response){
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.fromJson(jsonString,clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
