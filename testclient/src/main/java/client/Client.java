package client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import consitent.Requesturl;
import entity.Data;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by zgl on 17-10-19.
 */
public class Client {
    public List<ArrayList> getSendData() {
        float min = 0;
        float max = 1000000;
        List<ArrayList> queue = new ArrayList<ArrayList>();
        for (int i = 0; i < 1000; i++) {
            ArrayList<Float> list1 = new ArrayList<Float>();
            queue.add(list1);
        }
        for (int i = 0; i < queue.size(); i++) {
            List<Float> list1 = queue.get(i);
            int count=1000000;
            for (int j = 0; j <1000; j++) {
                Float num = min + (max - min) * new Random().nextFloat();
                list1.add(num);
            }
        }
        return queue;
    }

    public String start(CloseableHttpClient client,CloseableHttpResponse response,
                        HttpGet httpGet) throws IOException {
        client= HttpClients.createDefault();
        httpGet=new HttpGet(Requesturl.starturl);
        response=client.execute(httpGet);
        HttpEntity entity=response.getEntity();
        String body="";
        if(entity!=null){
            body=EntityUtils.toString(entity);
        }
        EntityUtils.consume(entity);
        response.close();
        return body;

    }

    public String hasnext(CloseableHttpClient client,CloseableHttpResponse response,
                          HttpPost httpPost,String id,List<Float> list) throws IOException {
        client = HttpClients.createDefault();
        httpPost=new HttpPost(Requesturl.hasnexturl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("list",list);
        httpPost.setEntity(new StringEntity(jsonObject.toString()));
        httpPost.setHeader("Content-type", "application/json");
        response=client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String body="";
        if(entity!=null){
            body=EntityUtils.toString(entity);
        }
        EntityUtils.consume(entity);
        response.close();
        return body;
    }

    public String end(CloseableHttpClient client,CloseableHttpResponse response,
                      HttpPost httpPost,String id,int number)
            throws IOException {
        client = HttpClients.createDefault();
        httpPost=new HttpPost(Requesturl.endurl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("number",number);
        httpPost.setEntity(new StringEntity(jsonObject.toString()));
        httpPost.setHeader("Content-type","application/json");
        response=client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String body = "";
        if(entity !=null){
            body = EntityUtils.toString(entity);
        }
        EntityUtils.consume(entity);
        response.close();
        return body;
    }


    public static void main(String[] args) throws Exception {
        Client client = new Client();
        CloseableHttpClient client1 = null;
        CloseableHttpResponse response = null;
        HttpGet httpGet = null;
        HttpPost httpPost = null;
        String id=client.start(client1,response,httpGet);
        List<ArrayList> lists = client.getSendData();
        System.out.println(lists.size());
        List<Float> list =null;
        String state=null;
        List<String> states = new ArrayList<String>();
        for (int i=0;i<lists.size();i++){
            list=lists.get(i);
            state = client.hasnext(client1,response,httpPost,id,list);
            System.out.println(state);
            states.add(state);
        }
        System.out.println("Start sorting Please wait for results....");
        Thread.sleep(10000);
        int number=1000;
        String result=client.end(client1,response,httpPost,id,number);
        JSONObject jsonObject = JSON.parseObject(result);
        System.out.println("Sorting success,the results is ...");
        System.out.println(jsonObject.get("list"));
    }
}
