package com.louie.core;

/**
 * 服务调用分发
 * @author
 *
 */
public interface ServiceDispacher {
	CallResult invoke(Service service);
}
