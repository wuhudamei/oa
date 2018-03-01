package cn.damei.dto.page;
public class Pagination {
    private final static int DEFALUT_OFFSET = 0;
    private final static int DEFALUT_LIMIT = 10;
    private int offset;
    private int limit;
    private Sort sort;
    public Pagination() {
        this.offset = DEFALUT_OFFSET;
        this.limit = DEFALUT_LIMIT;
    }
    public Pagination(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }
    public Pagination(int offset, int limit, Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    public Sort getSort() {
        return sort;
    }
    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
