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
        String text = "孩子哈师大在孩子；；》》】阿萨德】";
        System.out.println(analyzeWords(text));

    }


    public static Map<String, Integer> analyzeWords(String text) {
        Map<String, Integer> map = new HashMap<>();
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> segTokens = segmenter.process(text, JiebaSegmenter.SegMode.INDEX);

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
