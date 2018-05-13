package com.dade.bin.test;

import com.dade.bin.mybin.annotations.Block;
import com.dade.bin.mybin.annotations.FileName;
import com.dade.bin.mybin.annotations.FilePackage;
import com.dade.bin.mybin.annotations.SubBlock;

import java.util.List;

public interface BINTestInterface {

//    @Block(len=2, reverse = true)
//    @SubBlock(len=1)
//    @FilePackage("D:\\KAT\\standBin.BIN")
//    BINTestEntity testSingleEntity();

    @Block(len=2, reverse = true)
    @SubBlock(len=1)
    @FilePackage("D:\\KAT\\standBin.BIN")
    List<BINTestEntity> testMulEntity();

}
