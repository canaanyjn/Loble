package com.canaan.lockbible.Tools;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.Model.Verse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by canaan on 2015/4/18 0018.
 */
public class LeanCloudQueryUtils {

    public static List<Verse> findVerses() {
        // 查询当前Todo列表
        AVQuery<Verse> query = AVQuery.getQuery(Verse.class);
        // 按照更新时间降序排序
        query.orderByDescending("updatedAt");
        // 最大返回1000条
        query.limit(50);
        try {
            return query.find();
        } catch (AVException exception) {
            Log.e("tag", "Query verses failed.", exception);
            return Collections.emptyList();
        }
    }
}
