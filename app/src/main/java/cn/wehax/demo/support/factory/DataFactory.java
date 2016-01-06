package cn.wehax.demo.support.factory;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * 方便用户获取数据集
 *
 * @note 作为示例程序，对数据模型没什么要求～
 */
@Singleton
public class DataFactory {
    /**
     * 默认的列表Item数量
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    public List<String> getStringList() {
        return getStringList(DEFAULT_PAGE_SIZE);
    }

    public List<String> getStringList(int count) {
        return getStringList(0, count);
    }

    /**
     * 方便获取分页数据
     *
     * @param start 起始position的值
     * @param count
     * @return
     */
    public List<String> getStringList(int start, int count) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            list.add("这是第" + (start + i + 1) + "条");
        }
        return list;
    }
}
