package com.daisy.cbsx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daisy.cbsx.entity.Info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface SanxingDao extends BaseMapper<Info> {
    int save(Info info);

    List<HashMap> querybyPage(HashMap map);

    int delete(@Param("id") String id);
}
