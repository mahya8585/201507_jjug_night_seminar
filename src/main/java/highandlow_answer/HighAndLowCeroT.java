package highandlow_answer;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * High and Low ゲームクラス
 */
class HighAndLowCeroT {
    // @cero_t compileは事前に。
    private static final Pattern RIGHT_CHOICE = Pattern.compile("^[12]$");

    // @cero_t System.outもフィールド化して差し替えれば、JUnitでメッセージをテストできる。
    protected static PrintStream out = System.out;

    public static void main(String[] args) {
        // スタート文言の表示
        out.println("High and Low ゲームをはじめます。");
        // 基準値の生成
        int first = makeRandomNumber();
        // ユーザの入力
        out.println(first + "! 次の値は High? Low?");
        int userAnswer = inputUserAnswer();
        // 勝負値の生成
        int second = makeRandomNumber();
        out.println(second + "がでました！");
        // 結果判定
        showResult(first, second, userAnswer);
        out.println("ゲームを終わります。");
    }

    /**
     * ランダム数の生成を行います。
     * 最大値・最小値の指定も今回はこのメソッド内で設定しています。
     * 最小値：1 最大値：10
     * @return int 乱数
     */
    static int makeRandomNumber(){
        return (int)(Math.random() * 10 + 1);
    }

    /**
     * ユーザの答えの入力を制御します。
     * 1: High
     * 2: Low
     * 入力チェックも合わせて実施します。
     * @return ユーザが入力した値
     */
    static int inputUserAnswer(){
        String choices = "1: High   2: Low";
        out.println(choices);

        // @cero_t System.inはcloseしないほうが良いので、try-with-resourcesには含めない。
        // @cero_t Scannerを使うと不要なインスタンス生成を少し減らせる。でもマイナーだからBufferedReaderで良いや感はある。
        Scanner scanner = new Scanner(System.in);

        // ユーザの入力を待ちます
        String userAnswer = scanner.nextLine();

        // 正しい値が入力されるまで入力させ続けます。
        while (!validateAnswer(userAnswer)) {
            out.println("入力された値が不正です。正しい値を入力してください。");
            out.println(choices);
            userAnswer = scanner.nextLine();
        }

        return Integer.parseInt(userAnswer);
    }

    /**
     * 選択肢の入力チェックを行います。
     * - 数値型であること
     * - 1か2の値であること（1:High 2:Low)
     * @param targetChoice 入力チェック対象
     * @return 正しい入力値の場合はtrue、それ以外の場合はfalseを返します。
     */
    static boolean validateAnswer(String targetChoice) {
        //必須チェック
        if (targetChoice.isEmpty()) {
            return false;
        }

        //数値型チェック
        Matcher matcher = RIGHT_CHOICE.matcher(targetChoice);

        // @cero_t 余計なif文は削除
        return matcher.find();
    }

    /**
     * ユーザの選択肢の正誤を確認します
     * @param first 基準値
     * @param second 勝負値
     * @param userAnswer ユーザが入力した答え(1:High 2:Low)
     */
    static void showResult(int first, int second, int userAnswer){
        out.println(second + "がでました！");
        final Result result = go(first, second, userAnswer);
        out.println(getResultMessage(result));
    }

    static Result go(int first, int second, int userAnswer) {
        final int got = Integer.compare(first, second);
        if (got == 0) return Result.DRAW;
        if (got < 0 && userAnswer == 1) return Result.WIN;
        if (got > 0 && userAnswer == 2) return Result.WIN;
        return Result.LOSE;
    }

    static String getResultMessage(final Result result) {
        switch (result) {
            case WIN:
                return "あたり！ﾜ━ヽ(*´Д｀*)ﾉ━ｨ!!!";
            case LOSE:
                return "はずれ！(´・ω・｀)";
            case DRAW:
                return "引き分け！";
        }

        // @cero_t 例外発生時に原因を特定できるようにメッセージを入れる
        throw new IllegalArgumentException("想定外の値です : " + result);
    }

    enum Result {
        WIN, LOSE, DRAW
    }
}
