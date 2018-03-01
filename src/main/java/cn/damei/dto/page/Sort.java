package cn.damei.dto.page;
import org.springframework.util.StringUtils;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
public class Sort implements Iterable<Sort.Order>, Serializable {
    private static final long serialVersionUID = 5737186511678863905L;
    public static final Direction DEFAULT_DIRECTION = Direction.ASC;
    private final List<Order> orders;
    public Sort(Order... orders) {
        this(Arrays.asList(orders));
    }
    public Sort(List<Order> orders) {
        if (null == orders || orders.isEmpty()) {
            throw new IllegalArgumentException("You have to provide at least one sort property to sort by!");
        }
        this.orders = orders;
    }
    @Override
    public String toString() {
        return StringUtils.collectionToCommaDelimitedString(orders);
    }
    @Override
    public Iterator<Order> iterator() {
        return this.orders.iterator();
    }
    public enum Direction {
        ASC, DESC;
        public static Direction fromString(String value) {
            try {
                return Direction.valueOf(value.toUpperCase(Locale.US));
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format(
                        "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
            }
        }
    }
    public static class Order implements Serializable {
        private static final long serialVersionUID = 1522511010900108987L;
        private final Direction direction;
        private final String property;
        public Order(Direction direction, String property) {
            this.direction = direction;
            this.property = property;
        }
        public static Order build(Direction direction, String property) {
            return new Order(direction, property);
        }
        public Direction getDirection() {
            return direction;
        }
        public String getProperty() {
            return property;
        }
    }
}
