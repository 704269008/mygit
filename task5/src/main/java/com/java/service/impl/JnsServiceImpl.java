package com.java.service.impl;

import com.java.mapper.JnsMapper;
import com.java.pojo.Jns;
import com.java.service.JnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JnsServiceImpl implements JnsService{
    @Autowired
    private JnsMapper jnsMapper;
    public List<Jns> list() {
        return jnsMapper.list();
    }


}
