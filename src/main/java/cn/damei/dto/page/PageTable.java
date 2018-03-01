package cn.damei.dto.page;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
public class PageTable<E> implements Serializable {
    private static final PageTable EMPTY_PAGE = new PageTable<>(Collections.emptyList(), 0);
    private List<E> rows;
    private long total;
    public PageTable(List<E> rows, long total) {
        this.rows = rows;
        this.total = total;
    }
    public static <E> PageTable<E> build(List<E> rows, int total) {
        return new PageTable<E>(rows, total);
    }
    public List<E> getRows() {
        return rows;
    }
    public void setRows(List<E> rows) {
        this.rows = rows;
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
}
