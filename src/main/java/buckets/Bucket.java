package buckets;

/**
 * バケツに許された行動をそれぞれメソッド化します。
 * Created by maaya_ishida on 2015/06/30.
 */
public class Bucket {
    //バケツの容量
    private int capacity;
    //今現在の水の容量
    private int amount;

    /**
     * コンストラクタ
     * バケツ容量の設定を行います。
     * @param capacity バケツ容量の設定
     */
    Bucket(int capacity){
        this.capacity = capacity;
        setAmount(0);
    }

    /**
     * バケツいっぱいに水を満たす
     */
    public void fullIn() {
        amount = capacity;
    }

    /**
     * バケツの中身を全て捨てる
     * @return
     */
    public void empty() {
        amount = 0;
    }

    /**
     * 別のバケツに水を注ぐ
     * @param toBucket 注ぎ先バケツデータ
     * @return 注ぎ先バケツへ移動した水の量
     */
    public int move(Bucket toBucket) {
        int toBucketFreeSpace = toBucket.getCapacity() - toBucket.getAmount();
        if(toBucketFreeSpace <= amount) {
            amount = amount - toBucketFreeSpace;

            return toBucketFreeSpace;
        } else {
            int moveAmount = amount;
            amount = 0;

            return moveAmount;
        }
    }

    /**
     * getter/setter
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getCapacity() {
        return capacity;
    }
}
