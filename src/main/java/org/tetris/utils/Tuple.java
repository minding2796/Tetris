package org.tetris.utils;

public class Tuple<A, B> {
	private A a;
	private B b;
	public Tuple(A a, B b) {
		this.a = a;
		this.b = b;
	}
	public A getA() {
		return a;
	}
	public B getB() {
		return b;
	}
	public void setA(A a) {
		this.a = a;
	}
	public void setB(B b) {
		this.b = b;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!getClass().isInstance(obj)) return false;
		Tuple<?, ?> another = ((Tuple<?, ?>) obj);
		if (!another.getA().equals(getA())) return false;
		if (!another.getB().equals(getB())) return false;
		if (!another.getClass().getTypeParameters()[0].equals(this.getClass().getTypeParameters()[0])) return false;
		return another.getClass().getTypeParameters()[1].equals(this.getClass().getTypeParameters()[1]);
	}
	
	@Override
	public int hashCode() {
		return getA().hashCode() + getB().hashCode();
	}
}
