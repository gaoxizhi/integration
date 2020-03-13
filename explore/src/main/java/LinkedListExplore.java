import java.util.LinkedList;
import java.util.List;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/8/4 21:01
 */
public class LinkedListExplore {

    /**
     * LinkedList其实也就是我们在数据结构中的链表，这种数据结构有这样的特性：
     * <p>
     * 分配内存空间不是必须是连续的；
     * 插入、删除操作很快，只要修改前后指针就OK了，时间复杂度为O(1)；
     * 访问比较慢，必须得从第一个元素开始遍历，时间复杂度为O(n)；
     * 在Java中，LinkedList提供了丰富的方法，可以模拟链式队列，链式堆栈等数据结构，为用户带来了极大的方便，下面看看这些方法的用法：
     */

    public static void main(String[] args) {

        LinkedList<String> linked = new LinkedList<>();
        // 添加add
        linked.add("二");
        linked.addFirst("一");
        linked.addLast("三");
        linked.add(3, "四");
        linked.add("六");
        linked.add("七");
        linked.add("八");
        linked.add("九");
        linked.add("十");

        //移除，并返回该元素
        //删除第一个
        String remove = linked.remove();
        linked.removeFirst();
        //移除最后一个
        linked.removeLast();
        //移除index=2
        linked.remove(2);

        //移除，并返回成功状态
        boolean removeObject = linked.remove("3");
        linked.removeFirstOccurrence("1");
        linked.removeLastOccurrence("9");

        //获取
        String getIndex = linked.get(3);
        linked.getFirst();
        linked.getLast();

        //等同addFirst
        linked.push("十一");
        //等同removeFirst
        linked.pop();
        //查询并移除第一个元素，如果长度为空则返回null
        linked.poll();

        String peek = linked.peek();
        linked.peekFirst();
        linked.peekLast();

        linked.offer("十二");
        linked.offerFirst("零");
        linked.offerLast("十三");

        linked.set(6, "七");
        linked.contains("一");
        //等同getFirst
        linked.element();
        //截取子串，不改变原linkedList
        List<String> subList = linked.subList(2, 7);
    }
}