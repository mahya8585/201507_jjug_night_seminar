package buckets;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 水の移動手順と現在のステータスを保持するオブジェクト
 * Created by maaya_ishida on 2015/07/11.
 */
public class Status implements Cloneable {

    // このステータスにたどり着くまでの手順
    private List<Status> history;

    private int smallBucketAmount;
    private int largeBucketAmount;
    private String processText;

    Status() {
        history = new ArrayList<>();
    }

    @Override
    protected Status clone() {
        Status result = null;

        try{

            /* historyリストをclone前に保持しておき、clone実施後historyに戻す(deep copy) */
            List<Status> historyList = history.stream().collect(Collectors.toList());
            result = (Status)super.clone();
            result.setHistory(historyList);

        } catch (CloneNotSupportedException ce) {
            System.out.println("ステータスのクローンに失敗しました。");
            ce.printStackTrace();
        }

        return result;
    }

    /** getter/setter */
    public void setSmallBucketAmount(int smallBucketAmount) {
        this.smallBucketAmount = smallBucketAmount;
    }

    public int getSmallBucketAmount() {
        return smallBucketAmount;
    }

    public void setLargeBucketAmount(int largeBucketAmount) {
        this.largeBucketAmount = largeBucketAmount;
    }

    public int getLargeBucketAmount() {
        return largeBucketAmount;
    }

    public String getProcessText() {
        return processText;
    }

    public void setProcessText(String processText) {
        this.processText = processText;
    }

    public List<Status> getHistory() {
        return history;
    }

    public void setHistory(List<Status> history) {
        this.history = history;
    }

}
