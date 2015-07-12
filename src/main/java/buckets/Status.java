package buckets;

import java.util.ArrayList;
import java.util.List;

/**
 * 水の移動手順と現在のステータスを保持するオブジェクト
 * Created by maaya_ishida on 2015/07/11.
 */
public class Status {

    // このステータスにたどり着くまでの手順
    private List<Status> history;

    private int smallBucketAmount;
    private int largeBucketAmount;
    private SearchAnswer.BucketActions actionName;

    Status() {
        history = new ArrayList<>();
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

    public SearchAnswer.BucketActions getActionName() {
        return actionName;
    }

    public void setActionName(SearchAnswer.BucketActions actionName) {
        this.actionName = actionName;
    }


    /**setter/getter*/
    public List<Status> getHistory() {
        return history;
    }

    public void addHistory(Status status) {
        this.history.add(status);
    }

}
