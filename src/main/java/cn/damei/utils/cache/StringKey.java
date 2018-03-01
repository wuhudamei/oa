package cn.damei.utils.cache;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
public class StringKey {
    private final String s;
    public StringKey(String s) {
        this.s = s;
    }
    public String getValue() {
        return s;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringKey stringKey = (StringKey) o;
        return new EqualsBuilder()
                .append(s, stringKey.s)
                .isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(s)
                .toHashCode();
    }
}
