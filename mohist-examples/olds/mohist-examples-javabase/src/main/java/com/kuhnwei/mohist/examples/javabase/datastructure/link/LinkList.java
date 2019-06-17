package com.kuhnwei.mohist.examples.javabase.datastructure.link;

/**
 * 链表实现类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 14:54
 */
public class LinkList implements Link {

    /** 链表中的头节点对象 */
    private Node root;

    /** 链表中的节点个数 */
    private int count = 0;

    /** 作为标记的下标 */
    private int foot = 0;

    /** 链表的对象数组 */
    private Object[] array;


    @Override
    public void add(Object object) {
        if (object == null) {
            System.err.println("--------------- 被添加的对象为空值 -------------------");
            return ;
        }
        Node newNode = new Node(object);
        if (this.root == null) {
            this.root = newNode;
        } else {
            this.root.addNode(newNode);
        }
        this.count ++ ;
    }

    @Override
    public void remove(Object object) {
        if (this.contains(object)) {
            if (this.root.data.equals(object)) {
                this.root = this.root.next;
            } else {
                this.root.next.removeNode(this.root, object);
            }
            this.count --;
        }
    }

    @Override
    public Object get(int index) {
        if (index >= this.count) {
            return null;
        }
        this.foot = 0;
        return this.root.getNode(index);
    }

    @Override
    public void set(int index, Object object) {
        if (index >= this.count) {
            System.err.println("-------------  链表中不存在位置：" + index + " ----------------");
            return ;
        }
        this.foot = 0;
        this.root.setNode(index, object);
    }

    @Override
    public Object[] toArray() {
        if (this.root == null) {
            return null;
        }
        this.array = new Object[this.count];
        this.foot = 0;
        this.root.toArrayNode();
        return array;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public boolean contains(Object object) {
        return this.root != null && this.root.containsNode(object);
    }

    @Override
    public void clear() {
        this.root = null;
        this.count = 0;
        System.gc();
    }

    /**
     * 节点类
     */
    private class Node {

        /** 节点内容 */
        private Object data;

        /** 下一个节点对象 */
        private Node next;

        /**
         * 构造方法
         * @param object 节点内容
         */
        private Node(Object object) {
            this.data = object;
        }

        /**
         * 添加新节点
         * @param newNode 新节点
         */
        private void addNode(Node newNode) {
            if (this.next == null) {
                this.next = newNode;
            } else {
                this.next.addNode(newNode);
            }
        }

        /**
         * 删除节点
         * @param previous 当前节点的上一个节点对象
         * @param object 被删除的节点内容
         */
        private void removeNode(Node previous, Object object) {
            // 使用递归的方式删除节点
            if (this.data.equals(object)) {
                previous.next = this.next;
            } else {
                this.next.removeNode(this, object);
            }
        }

        /**
         * 根据指定位置获取节点对象
         * @param index 节点位置
         * @return 节点对象
         */
        private Object getNode(int index) {
            // 使用递归的方式获取节点对象
            if (LinkList.this.foot ++ == index) {
                return this.data;
            } else {
                return this.next.getNode(index);
            }
        }

        /**
         * 给指定位置赋新的节点对象
         * @param index 节点位置
         * @param object 新的节点对象
         */
        private void setNode(int index, Object object) {
            if (LinkList.this.foot ++ == index) {
                this.data = object;
            } else {
                this.next.setNode(index, object);
            }
        }

        /**
         * 把链表转出数组
         */
        private void toArrayNode() {
            LinkList.this.array[LinkList.this.foot ++] = this.data;
            if (this.next != null) {
                this.next.toArrayNode();
            }
        }

        /**
         * 判断是链表中是否包含该对象
         * @param object 被查找的对象
         * @return true : 存在
         */
        private boolean containsNode(Object object) {
            return this.data.equals(object) || this.next != null && this.next.containsNode(object);
        }

    }
}
