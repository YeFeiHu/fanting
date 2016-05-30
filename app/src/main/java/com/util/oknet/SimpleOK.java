package com.util.oknet;

import android.support.annotation.NonNull;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 没有封装只调用与原生的
 * Created by YeFeiHu on 2016/4/4.
 */
public class SimpleOK {
    private String url;
    public SimpleOK(String url){
        this.url = url;
    }


    private void postGet(boolean isAsyn){
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        Request request = builder.build();

        Call call = new OkHttpClient().newCall(request);




    }


    private void exe(Call call, boolean isAsyn){
        if(!isAsyn){
            try {
                Response result = call.execute();
                if(result.isSuccessful()){
                    result.body().bytes();
                }else{

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }


        // 异步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
            }
        });
    }

    public void executeGet(){
       postGet(false);

    }

    public void executeSynGet(){
        postGet(true);
    }

    private void postform(@NonNull HashMap<String,String> map, boolean isAsyn){
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        Map.Entry element = null;
        while(iterator.hasNext()){
            element = (Map.Entry<String,String>)iterator.next();
            String key = (String) element.getKey();
            String value = (String) element.getValue();
            formEncodingBuilder.add(key, value);
        }
        RequestBody body = formEncodingBuilder.build();

        Request request = new Request.Builder().post(body).build();
        Call call = new OkHttpClient().newCall(request);
        exe(call, isAsyn);
    }

    public void form(@NonNull HashMap<String,String> map){
        postform(map, false);
    }

    public  void formAsyn(@NonNull HashMap<String,String> map){
        postform(map, true);
    }

    public void file() {
        MultipartBuilder builder = new MultipartBuilder();
        builder.type(MultipartBuilder.FORM);

        builder.addFormDataPart("id", "dd");
        builder.addFormDataPart("name", "sdd");

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), new File("aa.png"));
        builder.addFormDataPart("head", "aa.png", fileBody);

        RequestBody body = builder.build();
        Request request = new Request.Builder().post(body).build();

        // do call...
    }

}
