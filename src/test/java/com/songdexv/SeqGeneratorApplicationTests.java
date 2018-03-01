package com.songdexv;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.songdexv.seqgenerator.cache.SequenceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SeqGeneratorApplication.class})
public class SeqGeneratorApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(SeqGeneratorApplicationTests.class);
    @Autowired
    private SequenceRepository sequenceRepository;

    @Test
    public void getSeq() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
		Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                    for (int i=0;i<20;i++) {
                        logger.info("-----" + sequenceRepository.genSequence("test"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);
		Thread t3 = new Thread(runnable);
		Thread t4 = new Thread(runnable);
		Thread t5 = new Thread(runnable);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		latch.countDown();
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		t5.join();
    }

}
