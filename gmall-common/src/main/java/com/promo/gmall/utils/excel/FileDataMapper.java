package com.promo.gmall.utils.excel;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 文件和数据转换器：包含两个核心功能：
 * i). 给定一个文件流转化为对应数据集合。
 * ii). 给定一个数据转化为对应的文件流字节。
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public interface FileDataMapper {


    /**
     * 通过流信息解析出对应集合数据
     *
     * @param inputStream 文件流信息
     * @param clazz       需要装换数据的类对象
     * @return 返回文件内容对应对象集合
     */
    <T> FileLoadResult<T> convert2Data(InputStream inputStream, Class<T> clazz);


    /**
     * 将集合数据转化为具体的文件输出流【excel, csv等】。
     *
     * @param data  将数据转化为对应的输出流
     * @param clazz 需要装换数据的类对象
     * @return 返回数据文件对应输出流
     */
    <T> OutputStream convert2Stream(List<T> data, Class<T> clazz);


}
