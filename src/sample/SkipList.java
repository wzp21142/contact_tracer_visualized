package sample;

import java.util.Random;

class SkipListNode <T> {//跳表节点类
    public int time;
    public T person_info;
    public SkipListNode<T> up, down, left, right; // 上下左右 四个指针

    public SkipListNode(int k,T v) {
        time = k;
        person_info = v;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public T getValue() {
        return person_info;
    }
    public void setValue(T person_info) {
        this.person_info = person_info;
    }
    
    public String toString() {
        String hour = String.valueOf(time / 60).length() == 1 ? "0" + time / 60 : String.valueOf(time / 60);
        String minute = String.valueOf(time % 60).length() == 1 ? "0" + time % 60 : String.valueOf(time % 60);
        //return "time-Info:"+hour+":"+minute+"(第"+time+"分钟)-"+tel_number;
        return hour + ":" + minute + "-" + person_info;
    }
}

public class SkipList <T>{//跳表类
    public final int MaxPerson = 500;
    final int HEAD_KEY = 0; // 负无穷
    final int TAIL_KEY = 1440; // 正无穷

    private SkipListNode<T> head,tail;
    private int nodes;//节点总数
    private int listLevel;//层数
    private Random random;// 用于投掷硬币
    private static final double PROBABILITY=0.5;//向上提升一个的概率

    public SkipList() {
        // TODO Auto-generated constructor stub
        random = new Random();
        clear();
    }

    /**
     * 清空跳跃表
     */
    public void clear() {
        head = new SkipListNode<T>(HEAD_KEY, null);
        tail = new SkipListNode<T>(TAIL_KEY, null);
        horizontalLink(head, tail);
        listLevel = 0;
        nodes = 0;
    }

    public boolean isEmpty() {
        return nodes == 0;
    }

    public int size() {
        return nodes;
    }


    //查找key值在表中应处于的位置,将该位置前一个的node返回
    private SkipListNode<T> findNode(int key) {
        SkipListNode<T> p = head;
        while (true) {
            while ((p.right.time != TAIL_KEY) && (p.right.time <= key)) {//不断向右检索直到检索到边界
                p = p.right;
            }
            if (p.down != null) {//向下移动一层继续检索
                p = p.down;
            } else {
                break;
            }

        }
        return p;
    }

    public SkipListNode<T> findPersonbyPhone(String phone) {
        SkipListNode<T> p = head;
        while (p.down != null)
            p = p.down;
        p = p.right;
        while (p.right.time != TAIL_KEY) {//不断向右检索直到检索到边界
            String temp = (p.getValue().toString().split(" ")[3]);
            if (phone.equals(temp))
                break;
            p = p.right;
        }
        return p;
    }

    public SkipListNode<T> findIlledPerson(SkipListNode<T> p) {
        if (p == null) {
            p = head;
            while (p.down != null)
                p = p.down;
        }
        p = p.right;
        while (p.right.time != TAIL_KEY) {//不断向右检索直到检索到边界
            String temp = (p.getValue().toString().split(" ")[5]);
            if (temp.equals("3"))
                break;
            p = p.right;
            if (p.right.time == TAIL_KEY)
                return null;
        }
        return p;
    }

    public SkipListNode<T> findSuspectedPerson(SkipListNode<T> p) {
        if (p == null) {
            p = head;
            while (p.down != null)
                p = p.down;
        }
        p = p.right;
        while (p.right.time != TAIL_KEY) {//不断向右检索直到检索到边界
            String temp = (p.getValue().toString().split(" ")[5]);
            if (Integer.parseInt(temp) > 0)
                break;
            p = p.right;
            if (p.right.time == TAIL_KEY)
                return null;
        }
        return p;
    }

    public SkipListNode<T> getComparedNode(int x, boolean smaller) {//smaller=1:需求为找出比x小的node,=0:找出比x大的.
        SkipListNode result;
        SkipListNode p = search(x);
        if (p == null) return null;
        if (smaller) {
            result = p.left;
        } else {
            result = p.right;
        }
        return result;
    }

    //返回list中所有time值在arrive中比smaller大的且同时在leave中比bigger小的nodes.
    public SkipListNode<Human>[] FindCommonNodes(SkipListNode<T> smaller, SkipListNode<T> bigger, SkipList list_arrive, SkipList list_leave) {
        SkipListNode<Human>[] result = new SkipListNode[MaxPerson];
        SkipListNode LeaveHead = list_leave.findNode(smaller.time).right;
        int IllLeaveTime = bigger.left.time;
        int i = 0;
        while (LeaveHead.right != null) {
            if (list_arrive.findPersonbyPhone(LeaveHead.person_info.toString().split(" ")[3]).time <= IllLeaveTime) {
                result[i] = new SkipListNode<>(LeaveHead.time, (Human) LeaveHead.person_info);
                i++;
            }
            LeaveHead = LeaveHead.right;
        }
        return result;
    }

    /**
     * 查找是否存储key，存在则返回该节点，否则返回null
     */
    //按key进行查询
    public SkipListNode<T> search(int key) {
        SkipListNode<T> p;
        p = findNode(key);
        if (key == p.getTime()) {
            return p;
        } else {
            return null;
        }
    }

    /**
     * 向跳跃表中添加key-value
     * 
     * */
    public void put(int k,T v){
        SkipListNode<T> p = null;
        p = findNode(k);

        SkipListNode<T> q = new SkipListNode<T>(k, v);
        backLink(p, q);
        int currentLevel = 0;//当前所在的层级是0
        //抛硬币
        while (random.nextDouble() < PROBABILITY) {
            //如果超出了高度，需要重新建一个顶层
            if (currentLevel >= listLevel) {
                listLevel++;
                SkipListNode<T> p1 = new SkipListNode<T>(HEAD_KEY, null);
                SkipListNode<T> p2=new SkipListNode<T>(TAIL_KEY, null);
                horizontalLink(p1, p2);
                verticalLink(p1, head);
                verticalLink(p2, tail);
                head=p1;
                tail=p2;
            }
            //将p移动到上一层
            while (p.up==null) {
                p=p.left;
            }
            p=p.up;

            SkipListNode<T> e=new SkipListNode<T>(k, null);//只保存key就ok
            backLink(p, e);//将e插入到p的后面
            verticalLink(e, q);//将e和q上下连接
            q=e;
            currentLevel++;
        }
        nodes++;//层数递增
    }
    //node1后面插入node2
    private void backLink(SkipListNode<T> node1,SkipListNode<T> node2){
        node2.left=node1;
        node2.right=node1.right;
        node1.right.left=node2;
        node1.right=node2;
    }
    /**
     * 水平双向连接
     * */
    private void horizontalLink(SkipListNode<T> node1,SkipListNode<T> node2){
        node1.right=node2;
        node2.left=node1;
    }
    /**
     * 垂直双向连接
     * */
    private void verticalLink(SkipListNode<T> node1, SkipListNode<T> node2){
        node1.down=node2;
        node2.up=node1;
    }
    /**
     * 打印出原始数据
     * */
    public String toString() {
        if (isEmpty()) {
            return "跳表为空！";
        }
        StringBuilder result=new StringBuilder();
        SkipListNode<T> p=head;
        while (p.down!=null) {
            p=p.down;
        }

        while (p.left!=null) {
            p=p.left;
        }
        if (p.right!=null) {
            p=p.right;
        }
        while (p.right!=null) {
            result.append(p);
            result.append("\n");
            p=p.right;
        }

        return result.toString();
    }

    public String[] getPersonsInfo(){
        return this.toString().split("\n");
    }
}