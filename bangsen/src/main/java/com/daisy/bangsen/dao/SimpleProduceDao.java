package com.daisy.bangsen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daisy.bangsen.entity.producement.SimpleProduce;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface SimpleProduceDao extends BaseMapper<SimpleProduce> {

    List<SimpleProduce> selectByParam(HashMap paraMap);
}
