package demo.custom.service.impl;

import demo.custom.mapper.SeqGenMapper;
import demo.custom.service.SeqGenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("seqGenService")
public class SeqGenServiceImpl implements SeqGenService{

    @Autowired
    SeqGenMapper seqGenMapper;

    @Override
    public synchronized String newSeqGen(String genId) throws Exception {
        String value = seqGenMapper.selectSeqGen(genId.trim());
        
        return value;
    }

    @Override
    public synchronized String newSeqGenId(String genId) throws Exception {
        String value = seqGenMapper.selectSeqGen(genId.trim());
        
        return genId + value;
    }

    
}