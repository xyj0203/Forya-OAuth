package com.wojucai.entity.reqParam;


import static com.wojucai.entity.codeEnum.ParamConstants.*;

/**
 * @description: 分页查询
 * @author: xuyujie
 * @date: 2023/06/11
 **/
public abstract class PageQuery {

    /**
     * 当前页
     */
    private Integer pageNow;

    /**
     * 条数
     */
    private Integer pageNumber;

    /**
     * 正序排序规则
     */
    private String sortAsc;


    /**
     * 逆序排序规则
     */
    private String sortDesc;

    public PageQuery(Integer pageNow, Integer pageNumber, String sortAsc, String sortDesc) {
        this.sortAsc = sortAsc;
        this.sortDesc = sortDesc;
        this.pageNumber = pageNumber;
        this.pageNow = pageNow;
    }

    public Integer getPageNow() {
        verifyPageNow(pageNow);
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    public Integer getPageNumber() {
        verifyPageNumber(pageNumber);
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    private void verifyPageNow(Integer pageNow) {
        if (pageNow == null || pageNow <= 0) {
            this.pageNow = PAGE_NOW;
        } else {
            this.pageNow = pageNow - 1;
        }
    }

    private void verifyPageNumber(Integer pageNumber) {
        if (pageNumber == null || pageNumber < 0) {
            this.pageNumber = PAGE_NUMBER;
        } else if (pageNumber > MAX_NUMBER){
            this.pageNumber = MAX_NUMBER;
        } else {
            this.pageNumber = pageNumber;
        }
    }

    public String getSortAsc() {
        return sortAsc;
    }

    public void setSortAsc(String sortAsc) {
        this.sortAsc = sortAsc;
    }

    public String getSortDesc() {
        return sortDesc;
    }

    public void setSortDesc(String sortDesc) {
        this.sortDesc = sortDesc;
    }
}
