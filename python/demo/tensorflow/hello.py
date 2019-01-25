import tensorflow as tf;
import numpy as np;

n1 = tf.constant(3.0, dtype = tf.float32);
n2 = tf.constant(4.0);
print("n1,n2:", n1, n2);
sess = tf.Session();
print(sess);
print("ft.add(n1,n2):");
print(sess.run(tf.add(n1,n2)));
print("ft.multiply(n1,n2):");
print(sess.run(tf.multiply(n1,n2)));


a = tf.placeholder(tf.float32);
b = tf.placeholder(tf.float32);
adder_node = a + b;
adder_node_mul = (a + b)*3;
x = 3.0;
y = 4.5;
print("x:", x, ";y:", y);
print("x + y:");
print(sess.run(adder_node,{a:x,b:y}));
x = np.array([1,3]);
y = np.array([2,4]);
print("x:", x, ";y:", y);
print("x + y:");
print(sess.run(adder_node,{a:x,b:y}));
print("（x + y）*3:");
print(sess.run(adder_node_mul,{a:x,b:y}));
x = np.array([1,3]);
y = 4;
print("x:", x, ";y:", y);
print("x + y:");
print(sess.run(adder_node,{a:x,b:y}));
print("（x + y）*3:");
print(sess.run(adder_node_mul,{a:x,b:y}));



const = tf.constant(2.0, name='const');
b = tf.Variable(2.0, name = 'b');
c = tf.Variable(1.0, name = 'c');
d = tf.add(b,c, name='d');
e = tf.add(c, const, name='e');
a = tf.multiply(d,e, name = 'a');