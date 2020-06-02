
package onjlib.utils;

public interface ObjectConstructorIF {
	public Object create(Object o);
	public Object create();
	public void destroy(Object o);
	public boolean valid(Object o);
}
