package com.daisy.myblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daisy.myblog.entity.TestInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface TestDao extends BaseMapper<TestInfo> {

    /**
     * 多表联合查询
     * @param map
     * @return
     */
    List<HashMap> QueryByMutiTable(HashMap map);

}
