package AutoRefer;


//菜品类
public class MyElement implements Comparable{
    //菜品
    public int id;
    //菜品价格
    public float price;
    //菜品评分
    public float score;
    //是否推荐该菜品
    public boolean take ;

    public int getId() {
        return id;
    }
    public float getPrice() {
        return price;
    }
    public float getScore() {
        return score;
    }
    public boolean isTake() {
        return take;
    }
    public MyElement(int id, float price, float score, boolean take) {
        this.id = id;
        this.price = price;
        this.score = score;
        this.take = take;
    }
    // 更改实现的方法，方便调用系统函数
    @Override
    public int compareTo(Object o) {
        if (score / price < ((MyElement) o).score / ((MyElement) o).price) {
            return 1; // 注意，此处主要用于排序，从大到小排序，所以故意反
        } else {
            return -1;
        }
    }
}
