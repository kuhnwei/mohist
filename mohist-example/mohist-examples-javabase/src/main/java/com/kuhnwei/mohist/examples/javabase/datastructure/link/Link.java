package com.kuhnwei.mohist.examples.javabase.datastructure.link;

/**
 * 链表接口
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 14:43
 */
public interface Link {

    /**
     * 增加链表节点
     * @param object 被添加节点
     */
    void add(Object object);

    /**
     * 删除节点
     * @param object 被删除节点
     */
    void remove(Object object);

    /**
     * 获取指定位置的节点对象
     * @param index 节点所在链表的位置
     * @return 节点对象
     */
    Object get(int index);

    /**
     * 修改指定位置节点内容
     * @param index 节点所在链表位置
     * @param object 新节点对象
     */
    void set(int index, Object object);

    /**
     * 链表转对象数组
     * @return 链表数组
     */
    Object[] toArray();

    /**
     * 获取链表长度
     * @return 链表长度
     */
    int size();

    /**
     * 判断链表是否为空
     * @return true:空
     */
    boolean isEmpty();

    /**
     * 判断链表中是否包含该对象
     * @param object 被查找的对象
     * @return true:存在
     */
    boolean contains(Object object);

    /**
     * 清空链表内容
     */
    void clear();
}
