package com.songdexv.seqgenerator.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.songdexv.seqgenerator.dao.SgSequenceDAO;
import com.songdexv.seqgenerator.dao.entity.SgSequence;
import com.songdexv.seqgenerator.dao.entity.SgSequenceExample;
import com.songdexv.seqgenerator.dao.mapper.SgSequenceMapper;

/**
 * Created by songdexv on 2018/3/1.
 */
@Repository("sgSequenceDAO")
public class SgSequenceDAOImpl implements SgSequenceDAO {
    @Autowired
    private SgSequenceMapper sgSequenceMapper;

    @Override
    public int insert(SgSequence sequence) {
        return sgSequenceMapper.insert(sequence);
    }

    @Override
    public int update(String seqName, long newValue, long originValue) {
        SgSequenceExample example = new SgSequenceExample();
        example.createCriteria().andSeqNameEqualTo(seqName).andSeqValueEqualTo(originValue);
        SgSequence sequence = new SgSequence();
        sequence.setGmtModified(new Date());
        sequence.setSeqValue(newValue);
        return sgSequenceMapper.updateByExampleSelective(sequence, example);
    }

    @Override
    public SgSequence select(String seqName) {
        SgSequenceExample example = new SgSequenceExample();
        example.createCriteria().andSeqNameEqualTo(seqName);
        List<SgSequence> list = sgSequenceMapper.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
