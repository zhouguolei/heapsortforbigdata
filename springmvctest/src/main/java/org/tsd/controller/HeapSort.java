package org.tsd.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.tsd.entity.Data;
import org.tsd.entity.EndData;
import org.tsd.heapsortutil.Sort;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Created by zgl on 17-10-19.
 */
@Controller
@RequestMapping("/heapsort")
public class HeapSort {
    private final static String ipadd = "127.0.0.1";
    private static List<Float> list;
    private static int count = 0;
    private static Map<Integer, Integer> map;
    private static Set<String> set;

    static {
        list = new ArrayList<Float>();
        map = new HashMap<Integer, Integer>();
        set = new HashSet<String>();
        map.put(0, 11111);
        map.put(1, 22222);
        map.put(2, 33333);

    }

    @ResponseBody
    @RequestMapping(value = "/start")
    public String start() {
        UUID uuid = UUID.randomUUID();
        set.add(uuid.toString());
        return uuid.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/hasnext", method = RequestMethod.POST)
    public String hasNext(@RequestBody Data data) {
        String id = data.getId();
        if (id != null) {
            if (!set.contains(id)) {
                return "error";
            } else {
                List<Float> list = data.getList();
                System.out.println(list);
                Random random = new Random();
                int port = map.get(random.nextInt(3));
                Socket socket = null;
                try {
                    socket = new Socket(ipadd, port);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", id);
                    jsonObject.put("list", list);
                    jsonObject.put("state", 1);
                    bw.write(jsonObject.toString() + "\n");
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return "submit ok!";
            }
        } else {
            return "submit error!";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/end", method = RequestMethod.POST)
    public String end(@RequestBody EndData endData) throws IOException {
        int m = endData.getNumber();
        String id = endData.getId();
        Sort sort = new Sort();
        if (id != null && set.contains(id)) {
            for (Integer integer : map.keySet()) {
                int port = map.get(integer);
                Socket socket = new Socket(ipadd, port);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("state", 2);;
                bw.write(jsonObject.toString() + "\n");
                bw.flush();

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String str = "";
                StringBuffer stringBuffer = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                JSONObject jsonObject1 = JSON.parseObject(stringBuffer.toString());
                List<Float> list1 = (List<Float>) jsonObject1.get("responsedata");
                for (int i = 0; i < list1.size(); i++) {
                    list.add(list1.get(i));
                }

            }
            float data[] = new float[m];
            for (int i = 0; i < data.length; i++) {
                String numstr = String.valueOf(list.get(i));
                float num = Float.valueOf(numstr);
                data[i] =num;
            }
            sort.builHeap(data);
            for (int i = m + 1; i < list.size(); i++) {
                String numstr = String.valueOf(list.get(i));
                float num = Float.valueOf(numstr);
                if (num > data[data.length - 1]) {
                    data[data.length - 1] =num;
                    sort.builHeap(data);
                }
            }
            System.out.println("ttt");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", data);
            System.out.println(jsonObject.toString());
            return jsonObject.toString();
        } else {
            return "sort error!";
        }
    }
}
