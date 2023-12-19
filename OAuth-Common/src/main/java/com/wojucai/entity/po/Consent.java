package com.wojucai.entity.po;

import com.wojucai.entity.Bo.key.ConsentKey;
import com.wojucai.utils.conveter.JpaConverterListJson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_consent")
@IdClass(ConsentKey.class)
public class Consent implements Serializable {

    /**
     * 客户端Id
     */
    @Id
    private String clientId;
    /**
     * 用户id
     */
    @Id
    private Long userId;
    /**
     * 作用域
     */
    @Convert(converter = JpaConverterListJson.class)
    private List<Integer> scope;
}


