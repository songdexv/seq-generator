package com.songdexv.seqgenerator.cache;

/**
 * Created by songdexv on 2018/3/1.
 */
public interface SequenceRepository {
    /**
     * 生成指定名称的Sequence
     *
     * @param seqName
     *
     * @return
     */
    public String genSequence(String seqName);
}
