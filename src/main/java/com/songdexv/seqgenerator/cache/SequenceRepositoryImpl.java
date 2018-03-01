package com.songdexv.seqgenerator.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by songdexv on 2018/3/1.
 */
@Service("sequenceRepository")
public class SequenceRepositoryImpl implements SequenceRepository {
    private static final Logger logger = LoggerFactory.getLogger(SequenceRepositoryImpl.class);
    @Autowired
    private SequenceService sequenceService;

    private Map<String, SequenceRange> seqMap = new ConcurrentHashMap<String, SequenceRange>();

    private final Lock lock = new ReentrantLock();

    @Override
    public String genSequence(String seqName) {
        return String.valueOf(nextValue(seqName));
    }

    private long nextValue(String seqName) {
        if (seqName == null) {
            throw new IllegalArgumentException("seqName为空！");
        }
        SequenceRange range = seqMap.get(seqName);
        if (range == null) {
            lock.lock();
            try {
                range = seqMap.get(seqName);
                if (range == null) {
                    range = sequenceService.nextRange(seqName);
                    seqMap.put(seqName, range);
                }
            } finally {
                lock.unlock();
            }
        }
        if (range == null) {
            throw new IllegalArgumentException("获取序列号失败，seqName=" + seqName);
        }
        long value = range.getAndIncrement();
        if (value == -1) {
            lock.lock();
            try {
                for (; ; ) {
                    if (range.isOver()) {
                        range = sequenceService.nextRange(seqName);
                        seqMap.put(seqName, range);
                    }
                    value = range.getAndIncrement();
                    if (value == -1) {
                        continue;
                    }
                    break;
                }
            } finally {
                lock.unlock();
            }
        }
        logger.debug("生成的sequence值为" + value);
        return value;
    }
}
