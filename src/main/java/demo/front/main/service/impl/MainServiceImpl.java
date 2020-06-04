package demo.front.main.service.impl;

import demo.front.main.mapper.MainMapper;
import demo.front.main.service.MainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mainService")
public class MainServiceImpl implements MainService {

    @Autowired
    MainMapper mainMapper;

    @Override
    public void insertTemp() throws Exception {
        mainMapper.insertTemp();
    }

   

}