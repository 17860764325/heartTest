package org.jeecg.modules.demo.chars.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lihaoran
 * @date 2023/11/17 00:37
 */
@Data
public class NameValue implements Serializable {
    private  String name;
    private  String value;
    private  String other;
}
