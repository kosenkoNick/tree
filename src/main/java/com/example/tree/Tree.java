package com.example.tree;

/*
 * @author Mykyta Kosenko
 */

interface Tree<E> extends Iterable<E> {

  boolean isPresent(E e);

  boolean insert(E e);
  boolean delete(E e);

  void inorder();
  void postorder();
  void preorder();

  int size();
  boolean isEmpty();
}