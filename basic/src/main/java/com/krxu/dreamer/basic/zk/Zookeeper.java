package com.krxu.dreamer.basic.zk;

import lombok.extern.log4j.Log4j;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

import java.util.List;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/9/19
 * @description [添加描述]
 */
@Log4j
public class Zookeeper {

    ZkClient zkClient = new ZkClient("10.211.93.169", 2181);

    @Test
    public void test() {
        List<String> rootNode = getChildren("/dubbo");
        for (String node : rootNode) {
            log.info("node:" + node);
            List<String> dubboNode = getChildren("/dubbo/" + node);
            for (String dnode : dubboNode) {
                log.info("dnode:" + dnode);
            }
        }

    }

    /**
     * 子节点
     * @param node
     * @return
     */
    private List<String> getChildren(String node) {
        return zkClient.getChildren(node);
    }

}
