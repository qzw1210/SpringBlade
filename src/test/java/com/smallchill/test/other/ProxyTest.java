package com.smallchill.test.other;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.smallchill.core.toolbox.kit.ClassKit;

public class ProxyTest {

	@Test
	public void test() {
		TestProxyBean testBean = new TestProxyBean();

		IProxy test = (IProxy) Proxy.newProxyInstance(testBean.getClass()
				.getClassLoader(), testBean.getClass().getInterfaces(),
				new TestProxyHander(testBean));

		test.test();
	}

	@Test
	public void testKit() {
		TestProxyBean testBean = new TestProxyBean();
		IProxyKit test = ClassKit.newProxyInstance(testBean.getClass(),
				new TestProxyHander(testBean));
		test.testKit();
	}
	
	@Test
	public void testCglib(){
		try {
			TestProxyMethodCglib test = (TestProxyMethodCglib) TestProxyFactory.getProxyObj(TestProxyMethodCglib.class.getName());
			test.testCglib();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCglibKit(){
		TestProxyMethodCglib test = ClassKit.newProxyCglibFactory(TestProxyMethodCglib.class, new TestProxyMethodInterceptor());
		test.testCglibKit();
	}

}


/**
 * ==============================Proxy===============================
 */

class TestProxyBean implements IProxy, IProxyKit {

	@Override
	public void test() {
		
		System.out.println("hello~");
	}

	@Override
	public void testKit() {
		
		System.out.println("helloKit~");
	}

}

interface IProxy {
	void test();
}

interface IProxyKit {
	void testKit();
}

class TestProxyHander implements InvocationHandler {

	private Object obj;

	public TestProxyHander(Object obj) {
		super();
		this.obj = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		System.out.println("hello before~");

		Object result = method.invoke(obj, args);

		System.out.println("hello after");

		return result;
	}

}

/**
 * ==============================cglib===============================
 */
class TestProxyMethodCglib {
	public void testCglib() {
		System.out.println("hello cglib~");
	}
	public void testCglibKit() {
		System.out.println("hello testCglibKit~");
	}
}

class TestProxyMethodInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2,
			MethodProxy arg3) throws Throwable {
		
		System.out.println("执行方法" + arg1 + "前");

		// 执行源对象的method方法
		Object returnValue = arg3.invokeSuper(arg0, arg2);

		System.out.println("执行方法" + arg1 + "后");

		return returnValue;
	}

}

class TestProxyFactory {
	/*
	 * 获得代理对象
	 */
	public static Object getProxyObj(String clazz) throws Exception {
		Class<?> superClass = Class.forName(clazz);
		Enhancer hancer = new Enhancer();
		// 设置代理对象的父类
		hancer.setSuperclass(superClass);
		// 设置回调对象，即调用代理对象里面的方法时，实际上执行的是回调对象（里的intercept方法）。
		hancer.setCallback(new TestProxyMethodInterceptor());
		// 创建代理对象
		return hancer.create();
	}
}
