package com.project.solr.search.service;

import com.project.search.info.base.BaseSearchResultInfo;
import com.project.solr.search.info.base.BaseSearchDocResultInfo;
import com.project.solr.search.info.base.ResultInfo;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.Map;

/**
 * 索引文件读操作接口，主要用于数据的检索
 *
 * @author ftc
 * @date 2018-12-27
 */
public interface SearchService<T extends BaseSearchDocResultInfo> {

    /**
     * 根据关键字查询
     * @param keyword 关键字
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public ResultInfo searchByKeyword(String keyword) throws IOException, SolrServerException;


    /**
     * 根据搜索参数进行高级搜索
     * @param params 搜索域与对应value的Map集合，key为搜索域，value为想要搜索的值
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public ResultInfo seniorSearchByParams(Map<String, String> params) throws IOException, SolrServerException;

}