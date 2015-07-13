package buckets;

/**
 * 定数クラス
 * Created by maaya_ishida on 2015/07/13.
 */
public class Const {
    //小さいバケツの内容量
    public static final int SMALL_BUCKET_CAPACITY = 3;

    //大きいバケツの内容量
    public static final int LARGE_BUCKET_CAPACITY = 8;

    //最終的に得たい水の量
    public static final int ANSWER = 4;

    //組み合わせ作成用の代替変数
    public static enum BucketActions {
        SMALL_FULLIn,
        SMALL_EMPTY,
        SMALL_MOVE,
        LARGE_FULLIN,
        LARGE_EMPTY,
        LARGE_MOVE
    }
}
