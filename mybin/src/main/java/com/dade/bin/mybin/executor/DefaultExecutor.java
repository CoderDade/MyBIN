package com.dade.bin.mybin.executor;

import com.dade.bin.mybin.executor.resultset.DefaultResultSetHandler;
import com.dade.bin.mybin.executor.resultset.ResultSet;
import com.dade.bin.mybin.session.BINConfig;
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
import static com.dade.bin.mybin.util.HexUtil.getStringFromBytes;
import static com.dade.bin.mybin.util.HexUtil.suffix;

public class DefaultExecutor implements Executor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExecutor.class);

    private final DefaultResultSetHandler resultSetHandler = new DefaultResultSetHandler();

    @Override
    public Object clean(BINConfig config) {
        if (config.getReverse()) {
            return cleanWithReverse(config);
        }
        return cleanWithoutReverse(config);
    }

    private Object cleanWithReverse(BINConfig config) {

        if (CollectionUtils.isEmpty(config.getRegularConfig())) {
            return cleanWithoutRegularConfig(config);
        }
        return cleanWithRegularConfig(config);
    }

    private Object cleanWithoutReverse(BINConfig config) {
        return null;
    }

    private Object cleanWithoutRegularConfig(BINConfig config) {
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

            int count = 0;
            while ((length = channel.read(blockLenLenBuffer)) != -1) {
                Map<Integer, byte[]> resultMap = Maps.newHashMap();
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
                resultMaps.add(resultMap);
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

    private Object cleanWithRegularConfig(BINConfig config) {
        return null;
    }

    private Object demo(BINConfig config){
        String pathname = config.getFilePackage();
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(new File(pathname));
            FileChannel channel = fin.getChannel();

            int lenCap = config.getBlockLen();
            ByteBuffer lenBf = ByteBuffer.allocate(lenCap);
            int length = -1;

            int count = 0;
//            while ((length = channel.read(lenBf)) != -1) {
            while ((length = channel.read(lenBf)) != -1 && count < 100) {

                lenBf.clear();
                byte[] bytes = lenBf.array();
                ArrayUtils.reverse(bytes);
                Integer len = Integer.valueOf(binary(bytes, 10));
                ByteBuffer rowBf = ByteBuffer.allocate(len);

                count++;
                System.out.println(count + " : " + len);

                try {
                    if ((length = channel.read(rowBf)) != -1) {
                        rowBf.clear();
                        byte[] rowArray = rowBf.array();
                        int index = 0;

                        int flagLen = rowArray[index++];

                        // flag
//                    System.out.print(count + " : " + flagLen + " === ");
                        byte[] flag = ArrayUtils.subarray(rowArray, index, index + flagLen);
                        index += flagLen;
                        String flagStr = binary(flag, 2);
                        flagStr = suffix(flagStr, flagLen);
                        char[] chars = flagStr.toCharArray();
//                    System.out.println(Arrays.toString(chars));

                        // partsCode
                        int partsCodeLen = rowArray[index++];
                        byte[] partsCodeArr = ArrayUtils.subarray(rowArray, index, index + partsCodeLen);
                        index += partsCodeLen;
                        String partsCode = getStringFromBytes(partsCodeArr);
//                    System.out.println(count + " : " + partsCodeLen + " === ");
//                    System.out.println(count + " : " + partsCode + " === ");

                        // nameTs
                        int nameLen = rowArray[index++];
                        byte[] nameArr = ArrayUtils.subarray(rowArray, index, index + nameLen);
                        index += nameLen;
                        ArrayUtils.reverse(nameArr);
                        Integer nameTs = Integer.valueOf(binary(nameArr, 10));
//                    System.out.println( "nameTs " + count + " : " + nameTs);

                        // str_1
                        byte[] str_1Arr = ArrayUtils.subarray(rowArray, index, index + 4);
                        index += 4;
                        String str_1 = getStringFromBytes(str_1Arr);
//                    System.out.println( "str_1 " + count + " : " + str_1);

                        // times
                        int times = rowArray[index++];
//                    System.out.println( "times " + count + " : " + times);

                        // interchangeCode
                        int interchangeCodeLen = rowArray[index++];
                        byte[] interchangeCodeArr = ArrayUtils.subarray(rowArray, index, index + interchangeCodeLen);
                        index += interchangeCodeLen;
                        String interchangeCode = getStringFromBytes(interchangeCodeArr);
//                    System.out.println("interchangeCodeLen " + count + " : " + partsCodeLen);
//                    System.out.println("interchangeCode " + count + " : " + partsCode);

                        // str_2
                        byte[] str_2Arr = ArrayUtils.subarray(rowArray, index, index + 4);
                        index += 7;
                        String str_2 = getStringFromBytes(str_2Arr);
//                    System.out.println( "str_2 " + count + " : " + str_2);

                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                }

//                System.out.println(binary(bytes, 10));

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

        return null;

    }

}
