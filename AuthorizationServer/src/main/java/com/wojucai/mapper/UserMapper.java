package com.wojucai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wojucai.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
