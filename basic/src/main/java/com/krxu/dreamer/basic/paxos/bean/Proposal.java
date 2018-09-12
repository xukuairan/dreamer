package com.krxu.dreamer.basic.paxos.bean;

import lombok.Data;

/**
 * @author xukuairan
 * @version [版本号]
 * @date 2018/8/27
 * @description 提案实体类
 */
@Data
public class Proposal {

    private int id;

    private String name;

    private String value;

    public Proposal() {
    }

    public Proposal(int id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public void copyFromInstance(Proposal proposal){
        this.id = proposal.id;
        this.name = proposal.name;
        this.value = proposal.value;
    }

}
