package com.dade.bin.test;

import com.dade.bin.mybin.annotations.Block;
import com.dade.bin.mybin.annotations.FileName;
import com.dade.bin.mybin.annotations.FilePackage;
import com.dade.bin.mybin.annotations.SubBlock;

public interface BINTestInterface {

    @Block(len=1, reverse = true)
    @SubBlock(len=1)
    @FileName("KAT04.BIN")
    @FilePackage("d:\test")
    BINTestEntity testSingleEntity();

}
