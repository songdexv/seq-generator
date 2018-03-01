package com.songdexv.seqgenerator.cache;

/**
 * Created by songdexv on 2018/3/1.
 */
public interface SequenceService {
    /**
     * 取得下一个可用的序列区间
     *
     * @param seqName
     *
     * @return
     *
     * @throws IllegalArgumentException
     */
    public SequenceRange nextRange(String seqName) throws IllegalArgumentException;
}
