package com.dade.bin.mybin.executor;

import com.dade.bin.mybin.executor.resultset.DefaultResultSetHandler;
import com.dade.bin.mybin.executor.resultset.ResultSet;
import com.dade.bin.mybin.session.BinConfig;
import com.dade.bin.mybin.util.HexUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

import static com.dade.bin.mybin.util.HexUtil.binary;

public class DefaultExecutor implements Executor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExecutor.class);

    private final DefaultResultSetHandler resultSetHandler = new DefaultResultSetHandler();

    @Override
    public Object clean(BinConfig config) {
        if (config.getReverse()) {
            return cleanWithReverse(config);
        }
        return cleanWithoutReverse(config);
    }

    private Object cleanWithReverse(BinConfig config) {

        if (CollectionUtils.isEmpty(config.getRegularConfig())) {
            return cleanWithoutRegularConfig(config);
        }
        return cleanWithRegularConfig(config);
    }

    private Object cleanWithoutReverse(BinConfig config) {
        return null;
    }

    public Object cleanWithoutRegularConfig(BinConfig config) {
        String pathName = config.getFilePackage();
        FileInputStream fin = null;
        List<Map<Integer, byte[]>> resultMaps = Lists.newArrayList();
        ResultSet rs = new ResultSet(resultMaps);
        try {
            fin = new FileInputStream(new File(pathName));
            FileChannel channel = fin.getChannel();

            int blockLenLen = config.getBlockLen();
            Integer subLenLen = config.getSubBlockLen();

            // 读取 blockLenLen 长度的Buffer
            ByteBuffer blockLenLenBuffer = ByteBuffer.allocate(blockLenLen);
            int length = -1;

            while ((length = channel.read(blockLenLenBuffer)) != -1) {
                Map<Integer, byte[]> resultMap = Maps.newHashMap();
                blockLenLenBuffer.clear();
                byte[] bytes = blockLenLenBuffer.array();
                ArrayUtils.reverse(bytes);
                Integer blockLen = Integer.valueOf(binary(bytes, 10));
                ByteBuffer blockBuffer = ByteBuffer.allocate(blockLen);

                try {
                    int insideCnt = 0;
                    if ((length = channel.read(blockBuffer)) != -1) {
                        blockBuffer.clear();
                        byte[] rowArray = blockBuffer.array();
                        int index = 0;

                        while (index+subLenLen<rowArray.length){
                            insideCnt++;
//                            int subBlockLen = rowArray[index+subLenLen];
                            int subBlockLen = HexUtil.bytesToInteger(ArrayUtils.subarray(rowArray, index, index + subLenLen), 10);
                            index = index + subLenLen;
                            byte[] singleBlock = ArrayUtils.subarray(rowArray, index, index + subBlockLen);
                            index = index + subBlockLen;

                            resultMap.put(insideCnt, singleBlock);
                        }
                        resultMaps.add(resultMap);
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

        return resultSetHandler.handleResultSets(rs, config);
    }

    private Object cleanWithRegularConfig(BinConfig config) {
        return null;
    }
}
