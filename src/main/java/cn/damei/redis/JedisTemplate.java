package cn.damei.redis;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.nosql.redis.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class JedisTemplate {
    private static Logger logger = LoggerFactory.getLogger(JedisTemplate.class);
    private final Pool<Jedis> jedisPool;
    public JedisTemplate(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }
    public <T> T execute(JedisAction<T> jedisAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = jedisPool.getResource();
            return jedisAction.action(jedis);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            broken = true;
            throw e;
        } finally {
            closeResource(jedis, broken);
        }
    }
    public void execute(JedisActionNoResult jedisAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = jedisPool.getResource();
            jedisAction.action(jedis);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            broken = true;
            throw e;
        } finally {
            closeResource(jedis, broken);
        }
    }
    protected void closeResource(Jedis jedis, boolean connectionBroken) {
        if (jedis != null) {
            try {
                if (connectionBroken) {
                    jedisPool.returnBrokenResource(jedis);
                } else {
                    jedisPool.returnResource(jedis);
                }
            } catch (Exception e) {
                logger.error("Error happen when return jedis to pool, try to close it directly.", e);
                JedisUtils.closeJedis(jedis);
            }
        }
    }
    public Long incrByOptimisticLock(final String counterKey) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                jedis.watch(counterKey);
                Transaction redisTranct = jedis.multi();
                redisTranct.incr(counterKey);
                List<Object> result = redisTranct.exec();
                if (result != null && result.size() == 1) {
                    return NumberUtils.toLong(result.get(0).toString());
                }
                return null;
            }
        });
    }
    public void executeByOptimisticLock(final String lockCounter, final BussiCallback action) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.watch(lockCounter);
                Transaction redisTranct = jedis.multi();
                redisTranct.incr(lockCounter);
                action.doBussiInAction();
                List<Object> result = redisTranct.exec();
                if (result == null) {
                    action.rollback();//业务代码回滚
                }
            }
        });
    }
    public Pool<Jedis> getJedisPool() {
        return jedisPool;
    }
    public Boolean del(final String... keys) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.del(keys) == 1 ? true : false;
            }
        });
    }
    public void flushDB() {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.flushDB();
            }
        });
    }
    public String get(final String key) {
        return execute(new JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }
    public boolean hasKey(final String key) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.exists(key);
            }
        });
    }
    public boolean expire(final String key, final int seconds) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.expire(key, seconds) == 1;
            }
        });
    }
    public boolean expireAt(final String key, final long expireAt) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.expireAt(key, expireAt) == 1;
            }
        });
    }
    public Long getAsLong(final String key) {
        String result = get(key);
        return result != null ? NumberUtils.toLong(result) : null;
    }
    public Long getAsLong(final String key, Long defautValue) {
        Long num = getAsLong(key);
        return num != null ? num : defautValue;
    }
    public Integer getAsInt(final String key) {
        String result = get(key);
        return result != null ? NumberUtils.toInt(result) : null;
    }
    public Integer getAsInt(final String key, Integer defaultValue) {
        String result = get(key);
        return result != null ? NumberUtils.toInt(result) : defaultValue;
    }
    public void set(final String key, final String value) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.set(key, value);
            }
        });
    }
    public void setex(final String key, final String value, final int seconds) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.setex(key, seconds, value);
            }
        });
    }
    public Boolean setnx(final String key, final String value) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.setnx(key, value) == 1 ? Boolean.TRUE : Boolean.FALSE;
            }
        });
    }
    public String getset(final String key, final String value) {
        return execute(new JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.getSet(key, value);
            }
        });
    }
    public Long getsetAsLong(final String key, final long value) {
        return NumberUtils.toLong(getset(key, String.valueOf(value)), Long.MAX_VALUE);
    }
    public Boolean setnxex(final String key, final String value, final int seconds) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                String result = jedis.set(key, value, "NX", "EX", seconds);
                return JedisUtils.isStatusOk(result);
            }
        });
    }
    public Long incr(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.incr(key);
            }
        });
    }
    public Long incrBy(final String key, final long increment) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.incrBy(key, increment);
            }
        });
    }
    public Long decr(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.decr(key);
            }
        });
    }
    public void lpush(final String key, final String... values) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.lpush(key, values);
            }
        });
    }
    public void rpush(final String key, final String... values) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.rpush(key, values);
            }
        });
    }
    public void clearList(final String key) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.ltrim(key, 1, 0);
            }
        });
    }
    public List<String> getListAllItem(final String key) {
        return execute(new JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(key, 0, -1);
            }
        });
    }
    public String rpop(final String key) {
        return execute(new JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.rpop(key);
            }
        });
    }
    public Long llen(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.llen(key);
            }
        });
    }
    public Boolean lremOne(final String key, final String value) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                Long count = jedis.lrem(key, 1, value);
                return (count == 1);
            }
        });
    }
    public Boolean lremAll(final String key, final String value) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                Long count = jedis.lrem(key, 0, value);
                return (count > 0);
            }
        });
    }
    public Boolean zadd(final String key, final String member, final double score) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.zadd(key, score, member) == 1 ? true : false;
            }
        });
    }
    public Long zmutiadd(final String key, final Map<String, Double> scoreMemberMap) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.zadd(key, scoreMemberMap);
            }
        });
    }
    public boolean hasMemberInzset(final String key, final String member) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                Long index = jedis.zrank(key, member);
                return index != null && index >= 0;
            }
        });
    }
    public Boolean zrem(final String key, final String member) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.zrem(key, member) == 1 ? Boolean.TRUE : Boolean.FALSE;
            }
        });
    }
    public List<String> findAllMemberOfzsetByScoreDesc(final String key) {
        Set<String> memberSet = execute(new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.zrevrangeByScore(key, "+inf", "-inf");
            }
        });
        List<String> memberList = Collections.emptyList();
        if (!CollectionUtils.isEmpty(memberSet)) {
            memberList = Lists.newArrayList(memberSet.iterator());
        }
        return memberList;
    }
    public boolean zremOfIndexByScoreAsc(final String key, final long index) {
        return zremRangeByScoreAsc(key, index, index) == 1;
    }
    public Long zremRangeByScoreAsc(final String key, final long startIdx, final long endIndex) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.zremrangeByRank(key, startIdx, endIndex);
            }
        });
    }
    public Double zscore(final String key, final String member) {
        return execute(new JedisAction<Double>() {
            @Override
            public Double action(Jedis jedis) {
                return jedis.zscore(key, member);
            }
        });
    }
    public Long zcard(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.zcard(key);
            }
        });
    }
    public interface JedisAction<T> {
        T action(Jedis jedis);
    }
    public interface BussiCallback {
        public void doBussiInAction();
        public void rollback();
    }
    public interface JedisActionNoResult {
        void action(Jedis jedis);
    }
}
