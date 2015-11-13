package museubeacon.museubeacon.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import museubeacon.museubeacon.template.TemplateObject;

/**
 * Created by carlton on 11/12/15
 */
public class BeaconCache {

    private final static Map<String, CacheableList> cache = new HashMap<>();
    private final static Object mutex = new Object();

    static {
        try {
            final Thread expiredReaper = new Thread(new Runnable() {
                @Override
                public void run() {
                    int sleepTimeMillis = 30000;

                    try {
                        while (true) {
                            synchronized (mutex) {

                                Iterator<Map.Entry<String, CacheableList>> it = cache.entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry<String, CacheableList> entry = it.next();
                                    if (entry.getValue().isExpired()) {
                                        it.remove();
                                    }
                                }
                            }

                            Thread.sleep(sleepTimeMillis);
                        }
                    } catch (Exception e) {
                        System.out.println("Cache reaper error");
                        e.printStackTrace();
                    }
                }
            });

            expiredReaper.setPriority(Thread.MIN_PRIORITY);
            expiredReaper.start();
        } catch (Exception e) {
            System.err.println("Error in static block of BeaconCache");
            e.printStackTrace();
        }
    }

    public static void put(TemplateObject obj) {
        synchronized (mutex) {
            String key = obj.getBeaconID();
            CacheableList list = cache.get(key);

            if(list == null) {
                cache.put(key, new CacheableList(obj));
            } else {
                list.getTemplateList().add(obj);
            }
        }
    }

    public static List<TemplateObject> getList(String key) {
        synchronized (mutex) {
            CacheableList obj = cache.get(key);
            return obj != null ? obj.getTemplateList() : null;
        }
    }
}
