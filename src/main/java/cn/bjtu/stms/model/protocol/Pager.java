package cn.bjtu.stms.model.protocol;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 分页信息数据类
 *
 * @param <T> 数据类型
 */
@Data
public class Pager<T> {

    private int totalCount;

    private int totalPage;

    // 默认每页显示10条
    private int pageSize = 10;

    // 当前页默认为第一页
    private int currentPage = 1;

    // 是否还有下一页
    private boolean hasNext = false;

    private List<T> dataList = Collections.emptyList();

    public Pager() {

    }

    public Pager(List<T> data, Integer pageNo, Integer pageSize, Integer total) {
        this.dataList = data;
        this.currentPage = getValidPageNo(pageNo, 1);
        this.pageSize = getValidPageSize(pageSize, 10);
        this.totalCount = (total == null || total < 0) ? 0 : total;
        this.totalPage = this.totalCount % this.pageSize == 0 ? (this.totalCount / this.pageSize) : (this.totalCount / this.pageSize + 1);
        this.hasNext = (this.totalPage > this.currentPage);
    }

    public static Integer getOffset(Integer pageNo, Integer pageSize) {
        return (pageNo != null && pageSize != null) ? (getValidPageNo(pageNo, 1) - 1) * getValidPageSize(pageSize, 10) : null;
    }

    public static int getValidPageNo(Integer pageNo, Integer defaultPageNo) {
        if (pageNo == null || pageNo <= 0) {
            pageNo = defaultPageNo;
        }
        return pageNo;
    }

    public static int getValidPageSize(Integer pageSize, Integer defaultPageSize) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = defaultPageSize;
        }
        return pageSize;
    }

}