package com.beifeng;

import org.apache.mina.core.session.IoSession;

/**
 * Created by kfzx-liuyz1 on 2016/5/20.
 */
public class MockDataTask  extends java.util.TimerTask {

    private IoSession session;

    private int count = 0;

    public MockDataTask(IoSession session) {
        this.session = session;
    }

    @Override
    public void run() {
        count++;
        session.write(".............测试数据，第" + String.valueOf(count) + "次............");
    }
}
