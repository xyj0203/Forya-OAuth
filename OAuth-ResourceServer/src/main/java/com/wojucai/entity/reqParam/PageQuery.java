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
    private Integer pageNow = 0;

    /**
     * 条数
     */
    private Integer pageNumber = PAGE_NUMBER;

    /**
     * 排序规则
     */
    private String sort = "ASC";

    public PageQuery(Integer pageNow, Integer pageNumber, String sort) {
        verifyPageNow(pageNow);
        verifyPageNumber(pageNumber);
        verifySort(sort);
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
        if (pageNow == null || pageNow < 0) {
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

    private void verifySort(String sort) {
        if ((ASC.equals(sort) || DESC.equals(sort))) {
            this.sort = sort;
        }
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
