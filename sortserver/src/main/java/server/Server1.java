package server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import heapsortutil.Sort;

import java.io.*;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by zgl on 17-10-20.
 */
public class Server1 {
    private static Map<String, Integer> map;
    private static Map<String, float[]> map2;
    static {
        map = new HashMap<String, Integer>();
        map2 = new HashMap<String, float[]>();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(11111);
        Socket socket = null;
        Sort sort = new Sort();
        int count=0;
        while (true) {
            socket = ss.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String string = "";
            StringBuffer sb = new StringBuffer();
            sb.append(br.readLine());
            JSONObject jsonObject = JSON.parseObject(sb.toString());
            List<Float> list = (List<Float>) jsonObject.get("list");
            String id = (String) jsonObject.get("id");
            int state = (Integer) jsonObject.get("state");
            if (state == 1) {
                if (!map.containsKey(id)) {
                    map.put(id, 1);
                    float[] floats = new float[1000];
                    for (int i = 0; i < floats.length; i++) {
                        String s =String.valueOf(list.get(i));
                        floats[i] =Float.valueOf(s);
                    }

                    sort.builHeap(floats);
                    System.out.println(floats[999]);
                    int length = floats.length;
                    for (int i = length; i < list.size(); i++) {
                        String str=String.valueOf(list.get(i));
                        float number =Float.valueOf(str);
                        if (number > floats[length - 1]) {

                            floats[length - 1] = number;
                            sort.builHeap(floats);
                        }
                    }
                    map2.put(id, floats);
                } else {
                    int val = map.get(id);
                    map.put(id, val + 1);
                    List<Float> floats2 = (List<Float>) jsonObject.get("list");
                    float[] floats = map2.get(id);
                    for (int i = 0; i < floats.length; i++) {
                        String numstr =String.valueOf(floats2.get(i));
                        float num = Float.valueOf(numstr);
                        if (num > floats[floats.length - 1]) {
                            floats[floats.length - 1] = num;
                            sort.builHeap(floats);
                        }
                    }
                    System.out.println(floats[999]);
                    map2.put(id, floats);
                }
            } else if(state == 2) {
                float[] floats = map2.get(id);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("responsedata", floats);
                bw.write(jsonObject1.toString()+"\n");
                bw.flush();
                bw.close();
            }
        }
    }
}
