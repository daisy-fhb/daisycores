package com.daisy.bangsen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daisy.bangsen.entity.financial.AccountReceivable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface AccountReceivableDao extends BaseMapper<AccountReceivable> {

    List<AccountReceivable> selectByParam(HashMap paraMap);
}
