package com.beifeng.filter.codec;

import com.beifeng.util.DataTypeUtil;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.lang.reflect.Array;
import java.nio.charset.Charset;

/**
 * Created by 胡志洁 on 2016/6/20.
 */
public class DTUTextDecoder extends CumulativeProtocolDecoder {

    private final static Charset charset = Charset.forName("UTF-8");

    private final int headSize = 4;



    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer in, ProtocolDecoderOutput out) throws Exception {

        if(in.remaining() > 0) {
            //有数据时，读取前8字节判断消息长度
            byte [] headBytes = new byte[this.headSize];

            in.mark();//标记当前位置，以便reset

            //因为我的前数据包的长度是保存在第2-4字节中，

            in.get(headBytes,0,4);//读取4字节
            byte[] sizeByte = new byte[2];
            System.arraycopy(headBytes, 2, sizeByte, 0, 2);
            //DataTypeChangeHelper是自己写的一个byte[]转int的一个工具类
            //int size = sizeByte[0] * 256 + sizeByte[1];


            //String isizeByte0 = toHexString(sizeByte[0]);
            //String isizeByte1 = toHexString(sizeByte[1]);
            int size = DataTypeUtil.byteToInt(sizeByte);
            in.reset();

            if(size > in.remaining()) {//如果消息内容不够，则重置，相当于不读取size

                return false;//父类接收新数据，以拼凑成完整数据
            }
            else{
                byte[] bytes = new byte[size];

                in.get(bytes, 0, size);

                //String message = new String(bytes, charset);

                out.write(bytes);

                if(in.remaining() > 0){//如果读取内容后还粘了包，就让父类再重读一次，进行下一次解析

                    return true;

                }
            }
        }

        return false;//处理成功，让父类进行接收下个包
    }
}
