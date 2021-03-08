package com.fxb.mocker.springbootmockdemo.vo.speficed;

import org.springframework.beans.BeanUtils;

/**
 * @author fangjiaxiaobai
 * @date 2021-03-01 17:38
 * @since 1.0.0
 */
public class Pager {
    private static final long serialVersionUID = -3638444579627050762L;
    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;
    private int pageSize;
    private int pageNum;
    private int count;

    public static Pager defaultPager() {
        Pager pager = new Pager();
        pager.pageNum = 1;
        pager.pageSize = 20;
        return pager;
    }

    public Pager newResultPager(int count) {
        Pager pager = new Pager();
        BeanUtils.copyProperties(this, pager);
        pager.setCount(count);
        return pager;
    }

    public int getOffset() {
        return (this.pageNum - 1) * this.pageSize;
    }

    public Pager() {
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public int getCount() {
        return this.count;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Pager)) {
            return false;
        } else {
            Pager other = (Pager)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getPageSize() != other.getPageSize()) {
                return false;
            } else if (this.getPageNum() != other.getPageNum()) {
                return false;
            } else {
                return this.getCount() == other.getCount();
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Pager;
    }

}
