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
    private Integer pageNow = 1;

    /**
     * 条数
     */
    private Integer pageNumber = PAGE_NUMBER;

    /**
     * 正序排序规则
     */
    private String sortAsc;

    /**
     * 逆序排序规则
     */
    private String sortDesc;

    public PageQuery(Integer pageNow, Integer pageNumber, String sortAsc, String sortDesc) {
        verifyPageNow(pageNow);
        verifyPageNumber(pageNumber);
        this.sortAsc = sortAsc;
        this.sortDesc = sortDesc;
    }

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        verifyPageNow(pageNow);
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        verifyPageNumber(pageNumber);
    }

    private void verifyPageNow(Integer pageNow) {
        if (pageNow == null || pageNow < 1) {
            this.pageNow = PAGE_NOW;
        } else {
            this.pageNow = pageNow;
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
