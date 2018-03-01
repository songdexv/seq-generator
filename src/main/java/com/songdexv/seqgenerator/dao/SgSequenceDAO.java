package com.songdexv.seqgenerator.dao;

import com.songdexv.seqgenerator.dao.entity.SgSequence;

/**
 * Created by songdexv on 2018/3/1.
 */
public interface SgSequenceDAO {
    public int insert(SgSequence sequence);

    public int update(String seqName, long newValue, long originValue);

    public SgSequence select(String seqName);
}
