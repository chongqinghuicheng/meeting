package com.cqhc.modules.system.domain;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-28
*/
@Entity
@Data
@Table(name="file_info")
public class FileInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name",nullable = false)
    private String fileName;

    @Column(name = "file_path",nullable = false)
    private String filePath;

    @Column(name = "file_size")
    private String fileSize;

    @Column(name = "file_page")
    private Short filePage;

    @Column(name = "version",nullable = false)
    private Short version;

    /**
     * 方便统计
     */
    @Column(name = "link_name",nullable = false)
    private String linkName;

    @Column(name = "link_id",nullable = false)
    private Long linkId;

    @Column(name = "cookie")
    private String cookie;

    @Column(name = "creater",nullable = false)
    private Long creater;

    @Column(name = "create_time",nullable = false)
    private Timestamp createTime;
}