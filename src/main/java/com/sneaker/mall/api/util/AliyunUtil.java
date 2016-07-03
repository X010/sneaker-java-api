package com.sneaker.mall.api.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class AliyunUtil {

    private Logger logger = LoggerFactory.getLogger(AliyunUtil.class);

    /**
     * 樋名
     */
    private String bucketName;

    /**
     * 路径
     */
    private String path;

    /**
     * 链接
     */
    private String endpoint;

    /**
     * ACCESS ID
     */
    private String accessId;

    /**
     * ACCESS KEY SECRET
     */
    private String accessKeySecret;

    public AliyunUtil() {
    }

    public AliyunUtil(String bucketName, String path, String endpoint, String accessId, String accessKeySecret) {
        this.bucketName = bucketName;
        this.path = path;
        this.endpoint = endpoint;
        this.accessId = accessId;
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }


    /**
     * 获取OSS CLIENT
     *
     * @return
     */
    private OSSClient getOssClient() {
        return new OSSClient(this.endpoint, this.getAccessId(), this.getAccessKeySecret());
    }


    /**
     * 上传图片
     *
     * @param filePath
     */
    public void putObject(String filePath, String rePath, String key) throws FileNotFoundException {
        OSSClient ossClient = getOssClient();
        File file = new File(filePath);
        InputStream content = new FileInputStream(file);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(file.length());
        meta.setContentType("image/jpeg");
        PutObjectResult result = ossClient.putObject(bucketName, String.format("%s/%s", rePath, key), content, meta);
        logger.info(result.getETag());
        ossClient.setObjectAcl(bucketName, String.format("%s/%s", rePath, key), CannedAccessControlList.PublicRead); //设置ACL
    }

    /**
     * 检测图片是否在阿里去上存在了，如果存在了
     *
     * @return
     */
    public boolean existsObject(String key) {
        OSSClient ossClient = getOssClient();
        return ossClient.doesObjectExist(this.bucketName, key);
    }

    /**
     * 删除对应的图片
     *
     * @param key
     * @return
     */
    public void deleteObject(String key) {
        OSSClient ossClient = getOssClient();
        ossClient.deleteObject(this.bucketName, key);
    }

    /**
     * 上传图片
     *
     * @param file
     */
    public String putObject(File file, String rePath, String key) throws FileNotFoundException {
        OSSClient ossClient = getOssClient();
        InputStream content = new FileInputStream(file);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("image/jpeg");
        meta.setContentLength(file.length());
        PutObjectResult result = ossClient.putObject(bucketName, String.format("%s/%s", rePath, key), content, meta);
        String res = result.getETag();
        ossClient.setObjectAcl(bucketName, String.format("%s/%s", rePath, key), CannedAccessControlList.PublicRead); //设置ACL
        return res;
    }


    private static final CannedAccessControlList[] ACLS = {
            CannedAccessControlList.Private,
            CannedAccessControlList.PublicRead,
            CannedAccessControlList.PublicReadWrite,
            CannedAccessControlList.Default
    };
}
