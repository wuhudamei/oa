package cn.damei.redis;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RLock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class RedissonTemplate {
    public final static String SCRAPE_LOCK = "scrapeLock";
    public final static String AUCTION_LOCK = "auctionLock";
    public final static String AUCTION_FREEZEN_FETCH_REFUND_DATA_LOCK = "auctionFetchRefundDataLock";
    public final static String AUCTION_USER_OFFER_CENT_DATA_LOCK = "auctionUserOfferCentLock";
    public final static String ORDER_LOCK = "order_lock_";
    public final static String TURNTABLE_LOCK = "TURNTABLE_LOCK";
    public final static String REGISTER_LOCK = "REGISTER_LOCK_";
    public final static String SEC_KILL = "SEC_KILL";
    public final static String SAME_RECORDE = "SAME_RECORDE_";
    private final Redisson rds;
    private final Map<String, RLock> lockMap = new ConcurrentHashMap<String, RLock>();
    public RedissonTemplate(String masterName, int dbIndex, String password, String... sentinelAddrs) {
        Config config = new Config();
        config.useSentinelConnection().setMasterName(masterName).addSentinelAddress(sentinelAddrs).setDatabase(dbIndex)
                .setPassword(password);
        rds = Redisson.create(config);
    }
    public RedissonTemplate(String address, int dbIndex, int timeout, String password) {
        Config config = new Config();
        config.useSingleServer().setPassword(password).setDatabase(dbIndex).setAddress(address).setTimeout(timeout);
        rds = Redisson.create(config);
    }
    public RLock getDistributeLock(String lockName) {
        RLock lock = lockMap.get(lockName);
        if (lock == null) {
            synchronized (this) {
                if (lock == null) {
                    lock = rds.getLock(lockName);
                }
                lockMap.put(lockName, lock);
            }
        }
        return lock;
    }
}