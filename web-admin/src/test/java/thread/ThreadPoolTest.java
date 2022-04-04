package thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ThreadPoolTest
 * @Author: SZL
 * @Date: 2022/3/21 21:46
 * @Description: TODO
 * @Version 1.0
 */
public class ThreadPoolTest {


//
//    @Test
//    public  void testThread() {
//
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,8,1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(100000));
//
//        List<Map<String,String>> list = new ArrayList<>();
//
//        Map<String,String> map;
//        for(int i =0;i<100000;i++){
//            map = new HashMap<>();
//            map.put("id" ,"ID:"+i);
//            map.put("name" ,"name:"+i);
//            list.add(map);
//        }
//
//        for (Map<String,String> map1:list){
//            final String id = map1.get("id");
//            threadPoolExecutor.execute(() -> plantThread(id,map1.get("name")));
//        }
//
//
//
//    }

    private  static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,8,1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(100000));


    public static void main(String[] args) {

        List<Map<String,String>> list = new ArrayList<>();

        Map<String,String> map;
        for(int i =0;i<1000;i++){
            map = new HashMap<>();
            map.put("id" ,"ID:"+i);
            map.put("name" ,"name:"+i);
            list.add(map);
        }

        for (Map<String,String> map1:list){
            final String id = map1.get("id");
            threadPoolExecutor.execute(() -> plantThread(id,map1.get("name")));
        }
    }


    public static boolean plantThread(String id, String name){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+":"+id+name);

        return false;
    }
}
