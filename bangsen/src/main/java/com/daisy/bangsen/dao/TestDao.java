package com.daisy.bangsen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.bangsen.entity.TestInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface TestDao extends BaseMapper<TestInfo> {

    /**
     * 多表分页联合查询
     * @return
     */
    List<HashMap> QueryByMutiTable(HashMap map);

}
