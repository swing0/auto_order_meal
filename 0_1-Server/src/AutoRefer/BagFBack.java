package AutoRefer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 回溯法的01背包
 *
 * @author anLA
 *
 */
public class BagFBack {
    public MyElement[] myelements; // 记录当前菜品是否推荐
    private float MAX;                // 顾客的最大消费额度
    private float nowPrice = 0; // 记录当前已推荐的菜品的总价
    private float nowScore = 0; // 记录当前已推荐的菜品的总评分
    private float betterValue; // 记录最高的评分

    public MyElement[] getMyelements() {
        return myelements;
    }

    /*
     * 构造方法，用于初始化各个变量
     */
    public BagFBack(int[] id,float[] SinglePrice, float[] SingleScore, float MAX) {
        myelements = new MyElement[SinglePrice.length];
        for (int i = 0; i < SinglePrice.length; i++) {
            myelements[i] = new MyElement(id[i],SinglePrice[i],SingleScore[i],false);
        }
        this.MAX= MAX;
        // 对数组进行价值排序,从大到小
        Arrays.sort(myelements);
        System.out.println("菜品ID"+"   "+"菜品评分" + "	" + "菜品价格");
        for (int i = 0; i < myelements.length; i++) {
            System.out.print(Integer.toString(myelements[i].id)+"  "+myelements[i].score + "	" + myelements[i].price);
            System.out.println();
        }
    }

    //开始回溯
    public void traceBack(int t) {
        if (t >= myelements.length) {
            // 已经遍历到最下一层，也就是最后一个
            System.out.println("已找到推荐方案");
            betterValue = nowScore;
            System.out.println("最高评分： " + betterValue);
            output(myelements);
            return;
        }
        // 首先进入走左子树
        if (nowPrice + myelements[t].price <= MAX) {
            // 进入左子树
            nowPrice += myelements[t].price;
            nowScore += myelements[t].score;
            myelements[t].take = true;
            traceBack(t + 1);
            // 还原现场
            nowPrice -= myelements[t].price;
            nowScore -= myelements[t].score;
            //myelements[t].take = false;
        }
        // 进入右子树，以及要进入的条件
        if (bound(t + 1) > betterValue) {
            traceBack(t + 1);
        }
    }

    // 输出方法，用于输出
    public void output(MyElement[] myelements2) {
        System.out.println("建议推荐的菜品方案如下：");
        for (int i = 0; i < myelements2.length; i++) {
            if(nowPrice<=MAX){
                if (myelements2[i].take) {
                    System.out.println(Integer.toString(myelements2[i].id)+"：   "+myelements2[i].price + "元");
                }
            }else {
                System.out.println("error");
            }
        }
    }

    /**
     * 用于计算右边的，如果右边大些，就直接进入
     *
     * @param i
     * @return
     */
    public float bound(int i) {
        // 计算上界
        float cleft = MAX - nowPrice;
        float bound = nowScore;
        // 以菜品单位评分递减顺序装入
        /**
        while (i < myelements.length && cleft > myelements[i].score) {
            cleft -= myelements[i].price;
            bound += myelements[i].score;
            i++;
            myelements[i].take = true;
        }**/
        return bound;
    }
    public static void main(String[] args) {
        //测试菜品的id
        int[] id={1,2,3,4,5,6,7,8};
        //测试菜品的单价
        float[] price = {3.6f,5.6f,2.6f,5.6f,3.6f,2.3f, 3.4f, 2.5f };
        //测试菜品的评分
        float[] score = {10f,3.8f,6.2f,7.7f,4.8f,4.5f, 3f, 2.5f };
        //顾客可接受的最大花费
        float max_price =  13.6f;
        //创建类实例，初始化
        BagFBack bagFBack = new BagFBack(id,price, score, max_price);
        // 从第0层开始回溯，找方案
        bagFBack.traceBack(0);
        /***至此回溯法结束***/


        /**服务器拿到推荐的菜品ID方法可以如下：
        List<Integer> data=new ArrayList<>();
        for(int k=0;k<(bagFBack.getMyelements()).length;k++){
            if((bagFBack.getMyelements())[k].isTake()){
                data.add((bagFBack.getMyelements())[k].getId());
            }
        }**/

    }

}