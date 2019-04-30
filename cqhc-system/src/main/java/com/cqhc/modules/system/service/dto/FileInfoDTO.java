package com.cqhc.modules.system.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-28
*/
@Data
public class FileInfoDTO implements Serializable {

    private Long id;

    private String fileName;

    private String filePath;

    private String fileSize;

    private Integer filePage;

    private Integer version;

    /**
     * 方便统计
     */
    private String linkName;

    private Long linkId;

    private String cookie;

    private Long creater;

    private Timestamp createTime;
}