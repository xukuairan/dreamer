import com.krxu.dreamer.jieba.JiebaSegmenter;
import com.krxu.dreamer.jieba.SegToken;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/16
 * @description [添加描述]
 */
public class TestJieba {
    @Test
    public void test() {
        String text = "本报北京8月16日电  （记者王观）国家外汇管理局16日通报21起外汇违规典型案例，包括恒丰银行温州分行违规办理转口贸易案、汇丰银行北京分行违规办理内保外贷案、建设银行连江支行违规办理个人分拆售付汇案、索尔维生物化学（泰兴）有限公司逃汇案等。\n" +
                "\n" +
                "　　其中，2016年1月，恒丰银行温州分行未按规定尽职审核转口贸易真实性，凭企业虚假提单办理转口贸易付汇业务。该行上述行为违反《外汇管理条例》第十二条。根据《外汇管理条例》第四十七条，处以罚没款146.4万元。\n" +
                "\n" +
                "　　外汇局表示，2018年以来，外汇局加强外汇市场监管，对各类外汇违法违规行为保持高压态势，不断加大处罚力度，严厉打击虚假、欺骗性交易和非法套利等资金“脱实向虚”行为，维护健康良性市场秩序。";
        System.out.println(analyzeWords(text));

    }


    public static Map<String, Integer> analyzeWords(String text) {
        Map<String, Integer> map = new HashMap<>();
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> segTokens = segmenter.process(text, JiebaSegmenter.SegMode.SEARCH);
        System.out.println(segTokens);
        for (SegToken segToken : segTokens) {
            String word = segToken.word;
            if (null == map.get(word)) {
                map.put(word, 1);
            } else {
                map.put(word, map.get(word) + 1);
            }
        }

        return map;
    }
}
