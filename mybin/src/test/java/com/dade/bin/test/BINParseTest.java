package com.dade.bin.test;

import com.dade.bin.mybin.executor.DefaultExecutor;
import com.dade.bin.mybin.session.BinConfig;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

import static com.dade.bin.mybin.util.HexUtil.binary;

public class BINParseTest {

    private BinConfig getMockBINConfig(){
        BinConfig config = new BinConfig();
        config.setBlockLen(2);
        config.setReverse(true);
        config.setSubBlockLen(1);
        config.setFilePackage("D:\\KAT\\standBin.BIN");
        return config;
    }

    @Test
    public void testParseTem(){
        DefaultExecutor executor = new DefaultExecutor();
        executor.cleanWithoutRegularConfig(getMockBINConfig());
    }

    @Test
    public void testParse(){
        BinConfig config = getMockBINConfig();
        String pathName = config.getFilePackage();
        FileInputStream fin = null;
        Map<Integer, byte[]> resultMap = Maps.newHashMap();
        try {
            fin = new FileInputStream(new File(pathName));
            FileChannel channel = fin.getChannel();

            int blockLenLen = config.getBlockLen();
            Integer subLenLen = config.getSubBlockLen();

            // 读取 blockLenLen 长度的Buffer
            ByteBuffer blockLenLenBuffer = ByteBuffer.allocate(blockLenLen);
            int length = -1;

            int count = 0;
            while ((length = channel.read(blockLenLenBuffer)) != -1) {
                blockLenLenBuffer.clear();
                byte[] bytes = blockLenLenBuffer.array();
                ArrayUtils.reverse(bytes);
                Integer blockLen = Integer.valueOf(binary(bytes, 10));
                ByteBuffer blockBuffer = ByteBuffer.allocate(blockLen);

                count++;
                try {
                    if ((length = channel.read(blockBuffer)) != -1) {
                        blockBuffer.clear();
                        byte[] rowArray = blockBuffer.array();
                        int index = 0;

                        int subBlockLen = rowArray[index+subLenLen];
                        index += subLenLen;
                        byte[] singleBlock = ArrayUtils.subarray(rowArray, index, index + subBlockLen);

                        resultMap.put(count, singleBlock);
                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        resultMap.entrySet().forEach(System.out::println);

    }

}
