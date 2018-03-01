package com.songdexv.seqgenerator.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songdexv.seqgenerator.dao.SgSequenceDAO;
import com.songdexv.seqgenerator.dao.entity.SgSequence;

/**
 * Created by songdexv on 2018/3/1.
 */
@Service("sequenceService")
public class DefaultSequenceService implements SequenceService {
    protected static final Logger logger = LoggerFactory.getLogger(DefaultSequenceService.class);
    /**
     * 获取sequence重试次数
     */
    private static final int retryTimes = 10;
    @Autowired
    private SgSequenceDAO sgSequenceDAO;

    @Override
    public SequenceRange nextRange(String seqName) throws IllegalArgumentException {
        if (seqName == null) {
            throw new IllegalArgumentException("seqName为空！");
        }
        for (int i = 0; i < retryTimes; i++) {
            try {
                SgSequence sequence = sgSequenceDAO.select(seqName);
                if (sequence == null) {
                    throw new IllegalArgumentException("序列配置不存在，seqName=" + seqName);
                }
                long originValue = sequence.getSeqValue();
                long newValue = originValue + sequence.getStep();
                if (newValue < sequence.getMinValue() || newValue > sequence.getMaxValue()) {
                    originValue = 0;
                    newValue = originValue + sequence.getStep();
                }
                int effect = sgSequenceDAO.update(seqName, newValue, originValue);
                if (effect < 1) {
                    logger.warn("获取序列号失败，更新区间失败，seqName=" + seqName);
                    continue;
                }
                logger.info(seqName + ",get newRange:[" + (originValue + 1) + "," + newValue + "]");
                return new SequenceRange(originValue + 1, newValue);
            } catch (Exception e) {
                logger.warn("获取序列号异常，seqName=" + seqName, e);
                continue;
            }
        }
        throw new IllegalArgumentException("获取序列失败，seqName=" + seqName);
    }
}
