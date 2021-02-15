/*
 * 文件名：VisitVO.java
 * 描述：搭建项目数据传输
 * 修改人：刘可
 * 修改时间：2021-02-16
 */
package com.example.demo.vo;

import java.io.Serializable;
import java.util.List;

import com.example.demo.entity.SignInMethod;

import lombok.*;

/**
 * 描述访问信息。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-16
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class VisitVO implements Serializable, VO
{
    private static final long serialVersionUID = 1L;
    private Long ipv4;
    private List<Byte> ipv6;
    private List<Byte> mac;
    private String gps;
    private SignInMethod method;
}
