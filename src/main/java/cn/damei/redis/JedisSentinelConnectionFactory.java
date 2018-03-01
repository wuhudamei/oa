package cn.damei.redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConverters;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;
public final class JedisSentinelConnectionFactory implements DisposableBean, RedisConnectionFactory {
    private static Logger log = LoggerFactory.getLogger(JedisSentinelConnectionFactory.class);
    private Pool<Jedis> pool = null;
    private int dbIndex = 0;
    private boolean convertPipelineAndTxResults = true;
    public JedisSentinelConnectionFactory(Pool<Jedis> pool) {
        this(pool, 0);
    }
    public JedisSentinelConnectionFactory(Pool<Jedis> pool, int dbIndex) {
        this.pool = pool;
        this.dbIndex = dbIndex;
    }
    protected Jedis fetchJedisConnector() {
        try {
            return pool.getResource();
        } catch (Exception ex) {
            throw new RedisConnectionFailureException("Cannot get Jedis connection", ex);
        }
    }
    protected JedisConnection postProcessConnection(JedisConnection connection) {
        return connection;
    }
    @Override
    public void destroy() {
        try {
            pool.destroy();
        } catch (Exception ex) {
            log.warn("Cannot properly close Jedis pool", ex);
        }
        pool = null;
    }
    @Override
    public JedisConnection getConnection() {
        Jedis jedis = fetchJedisConnector();
        JedisConnection connection = new JedisConnection(jedis, pool, dbIndex);
        connection.setConvertPipelineAndTxResults(convertPipelineAndTxResults);
        return postProcessConnection(connection);
    }
    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return JedisConverters.toDataAccessException(ex);
    }
    public int getDatabase() {
        return dbIndex;
    }
    public void setDatabase(int index) {
        Assert.isTrue(index >= 0, "invalid DB index (a positive index required)");
        this.dbIndex = index;
    }
    @Override
    public boolean getConvertPipelineAndTxResults() {
        return convertPipelineAndTxResults;
    }
    public void setConvertPipelineAndTxResults(boolean convertPipelineAndTxResults) {
        this.convertPipelineAndTxResults = convertPipelineAndTxResults;
    }
}
