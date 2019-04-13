package cn.icodelife.concurrency.demo;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @description:
 * @author: modestlee
 * @email: modestlee@126.com
 * @date: 2019-04-09 21:16
 **/
@Slf4j
public class MapExample {

    private static Map<Integer,Integer> map = Maps.newHashMap();

    private static int threadNum = 200;
    private static int clientNum = 5000;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadNum);
        for (int index = 0; index < clientNum; index++) {
            final int threadnum = index;
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    func(threadnum);
                    semaphore.release();
                }catch (Exception e){
                    log.error("error",e);
                }
            });
        }
        executorService.shutdown();
        log.info("map size{}",map.size());
    }

    private static void func(int threadnum) {
        map.put(threadnum,threadnum);
    }
}
