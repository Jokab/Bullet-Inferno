package se.dat255.bulletinferno.util;

/**
 * Generic callback listener
 * @param <E> The type of data wanted
 */
public interface Listener<E> {
	public void call(E e);
}
