package cn.yj.market.frame.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.cglib.beans.BeanCopier;

import org.springframework.util.Assert;

import cn.yj.market.frame.bean.Copyable;

/**
 * 
 */
@SuppressWarnings("unchecked")
public abstract class ObjectUtils {
	//
	private static final ConcurrentHashMap<Class<?>, BeanCopier> BEAN_COPIERS = new ConcurrentHashMap<Class<?>, BeanCopier>();
	
	//
	private static final Set<Class<?>> PRIMITIVES_AND_WRAPPERS = new HashSet<Class<?>>();
	static {
		PRIMITIVES_AND_WRAPPERS.add(boolean.class);
		PRIMITIVES_AND_WRAPPERS.add(Boolean.class);
		PRIMITIVES_AND_WRAPPERS.add(byte.class);
		PRIMITIVES_AND_WRAPPERS.add(Byte.class);
		PRIMITIVES_AND_WRAPPERS.add(char.class);
		PRIMITIVES_AND_WRAPPERS.add(Character.class);
		PRIMITIVES_AND_WRAPPERS.add(double.class);
		PRIMITIVES_AND_WRAPPERS.add(Double.class);
		PRIMITIVES_AND_WRAPPERS.add(float.class);
		PRIMITIVES_AND_WRAPPERS.add(Float.class);
		PRIMITIVES_AND_WRAPPERS.add(int.class);
		PRIMITIVES_AND_WRAPPERS.add(Integer.class);
		PRIMITIVES_AND_WRAPPERS.add(long.class);
		PRIMITIVES_AND_WRAPPERS.add(Long.class);
		PRIMITIVES_AND_WRAPPERS.add(short.class);
		PRIMITIVES_AND_WRAPPERS.add(Short.class);
	}
	
	//
	private static final Set<Class<?>> IMMUTABLE_CLASSES = new HashSet<Class<?>>();
	static {
		IMMUTABLE_CLASSES.add(Boolean.class);
		IMMUTABLE_CLASSES.add(Byte.class);
		IMMUTABLE_CLASSES.add(Character.class);
		IMMUTABLE_CLASSES.add(Double.class);
		IMMUTABLE_CLASSES.add(Float.class);
		IMMUTABLE_CLASSES.add(Integer.class);
		IMMUTABLE_CLASSES.add(Long.class);
		IMMUTABLE_CLASSES.add(Short.class);
		IMMUTABLE_CLASSES.add(Class.class);
		IMMUTABLE_CLASSES.add(String.class);
		IMMUTABLE_CLASSES.add(BigDecimal.class);
		IMMUTABLE_CLASSES.add(BigInteger.class);
//		IMMUTABLE_CLASSES.add(java.util.Date.class);
//		IMMUTABLE_CLASSES.add(java.sql.Date.class);
	}
	
	/**
	 * 
	 */
	public static final <T> boolean isEquals(T lhs, T rhs) {
		if(lhs == null && rhs == null) {
			return true;
		} else if(lhs == null || rhs == null) {
			return false;
		} else {
			return lhs.equals(rhs);
		}
	}
	
	/**
	 * 
	 */
	public static boolean isArray(Object array) {
		// Precondition checking
		if(array == null) {
			return false;
		}
		
		//
		return array.getClass().isArray();
	}
	
	public static boolean isCloneable(Object obj) {
		// Precondition checking
		if(obj == null) {
			return false;
		}
		
		//
		final Class<?> clazz = obj.getClass();
		if(!Cloneable.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		
		//
		Method method = ReflectionUtils.findPublicMethod(clazz, "clone", new Class<?>[]{});
		return method != null;
	}
	
	public static boolean isPrimitiveOrWrapper(Object obj) {
		// Precondition checking
		if(obj == null) {
			return false;
		}
		
		//
		return PRIMITIVES_AND_WRAPPERS.contains(obj.getClass());
	}
	
	public static boolean isArrayOfPrimitives(Object array) {
		// Precondition checking
		if (array == null) {
			return false;
		}
		
		//
		Class<?> clazz = array.getClass();
		return clazz.isArray() && clazz.getComponentType().isPrimitive();
	}
	
	public static boolean isArrayOfPrimitivesOrWrappers(Object array) {
		// Precondition checking
		if (array == null) {
			return false;
		}
		
		//
		Class<?> clazz = array.getClass();
		return clazz.isArray() && PRIMITIVES_AND_WRAPPERS.contains(clazz);
	}
	
	/**
	 * 
	 */
	public static Object getDefaultValue(Class<?> clazz) {
		// Precondition checking
		if(clazz == null || !clazz.isPrimitive()) {
			return null;
		}
		
		//
		if(void.class == clazz) return null;
		if(boolean.class == clazz) return Boolean.FALSE;
		if(byte.class == clazz) return Byte.valueOf((byte) 0);
		if(short.class == clazz) return Short.valueOf((short) 0);
		if(int.class == clazz) return Integer.valueOf(0);
		if(long.class == clazz) return Long.valueOf(0);
		if(char.class == clazz) return Character.valueOf((char) 0);
		if(double.class == clazz) return Double.valueOf(0);
		if(float.class == clazz) return Float.valueOf(0);
		throw new RuntimeException("assertion failed, should not reach here, clazz: " + clazz);
	}

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static Object copy(Object obj) {
		// Precondition checking
		if(obj == null) {
			return null;
		}
		
		//
		final Class<?> clazz = obj.getClass();
		if(isPrimitiveOrWrapper(obj)) {
			return obj;
		}
		if(IMMUTABLE_CLASSES.contains(clazz)) {
			return obj;
		}
		
		//
		if(isArray(obj)) {
			return arrayCopy(obj);
		}
		if(Set.class.isAssignableFrom(clazz)) {
			return setCopy((Set)obj);
		}
		if(Map.class.isAssignableFrom(clazz)) {
			return mapCopy((Map)obj);
		}
		if(List.class.isAssignableFrom(clazz)) {
			return listCopy((List)obj);
		}
		
		//
		if(Copyable.class.isAssignableFrom(clazz)) {
			return ((Copyable)obj).copy();
		}
		if(Cloneable.class.isAssignableFrom(clazz) && isCloneable(obj)) {
			return cloneCopy(obj);
		}
		if(Serializable.class.isAssignableFrom(clazz)) {
			return SerializationUtils.copy((Serializable)obj);
		}

		//
		try {
			Object r = clazz.newInstance();
			copy(obj, r);
			return r;
		} catch(Exception e) {
			throw new RuntimeException("failed to copy " + obj, e);
		}
	}
	
	public static Object arrayCopy(Object array) {
		// Precondition checking
		if(!isArray(array)) {
			throw new IllegalArgumentException("parameter array is not an Array");
		}
		
		//
		final int length = Array.getLength(array);
		final Object r = Array.newInstance(array.getClass().getComponentType(), length);
		for(int i = 0; i < length; i++) {
			final Object element = Array.get(array, i);
			if(isArray(element)) {
				Array.set(r, i, arrayCopy(element));
			} else {
				Array.set(r, i, copy(element));
			}
		}
		return r;
	}
	
	@SuppressWarnings("rawtypes")
	public static Object setCopy(Set set) {
		// Precondition checking
		if(set == null) {
			return null;
		}
		
		//
		try {
			Set r = set.getClass().newInstance();
			for(Object obj : set) {
				r.add(copy(obj));
			}
			return r;
		} catch (Exception e) {
			throw new RuntimeException("failed to copy set", e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Object mapCopy(Map map) {
		// Precondition checking
		if(map == null) {
			return null;
		}
		
		//
		try {
			Map r = map.getClass().newInstance();
			for(Object key : map.keySet()) {
				r.put(key, copy(map.get(key)));
			}
			return r;
		} catch (Exception e) {
			throw new RuntimeException("failed to copy map", e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Object listCopy(List list) {
		// Precondition checking
		if(list == null) {
			return null;
		}
		
		//
		try {
			List r = list.getClass().newInstance();
			for(Object obj : list) {
				r.add(copy(obj));
			}
			return r;
		} catch (Exception e) {
			throw new RuntimeException("failed to copy list", e);
		}
	}
	
	public static Object cloneCopy(Object obj) {
		// Precondition checking
		if(obj == null) {
			return obj;
		}
		if(!isCloneable(obj)) {
			throw new IllegalArgumentException("parameter obj: " + obj + " is not cloneable");
		}
		
		//
		try {
			final Class<?> clazz = obj.getClass();
			Method method = ReflectionUtils.findPublicMethod(clazz, "clone", new Class<?>[]{});
			method.setAccessible(true);
			return method.invoke(obj, new Object[]{});
		} catch (Exception e) {
			throw new RuntimeException("failed to clone copy", e);
		}
	}
	
	/**
	 * 
	 */
	public static void copy(Object src, Object dst) {
		// Precondition checking
		if(src == null) {
			throw new IllegalArgumentException("invalid parameter src");
		}
		if(dst == null) {
			throw new IllegalArgumentException("invalid parameter dst");
		}
		if(!src.getClass().equals(dst.getClass())) {
			throw new IllegalArgumentException("the class does not match, src: " + src.getClass() + ", dst: " + dst.getClass());
		}
		
		//
		final Class<?> clazz = src.getClass();
		BeanCopier copier = BEAN_COPIERS.get(clazz);
		if(copier == null) {
			copier = BeanCopier.create(clazz, clazz, false);
			BeanCopier existing = BEAN_COPIERS.putIfAbsent(clazz, copier);
			if(existing != null) {
				copier = existing;
			}
		}
		
		//
		copier.copy(src, dst, null);
	}
	
    /**
     * 获取当前类声明的private/protected变量
     */
    static public Object getPrivateProperty(Object object, String propertyName) throws IllegalAccessException,
            NoSuchFieldException {

        Assert.notNull(object);
        Assert.hasText(propertyName);
        Field field = object.getClass().getDeclaredField(propertyName);
        field.setAccessible(true);
        return field.get(object);
    }

    /**
     * 设置当前类声明的private/protected变量
     */
    static public void setPrivateProperty(Object object, String propertyName, Object newValue)
            throws IllegalAccessException, NoSuchFieldException {

        Assert.notNull(object);
        Assert.hasText(propertyName);

        Field field = object.getClass().getDeclaredField(propertyName);
        field.setAccessible(true);
        field.set(object, newValue);
    }

	/**
	 * 
	 */
	public static boolean registerImmutableClass(Class<?> clazz) {
		return IMMUTABLE_CLASSES.add(clazz);
	}
	
	public static boolean unregisterImmutableClass(Class<?> clazz) {
		return IMMUTABLE_CLASSES.remove(clazz);
	}
}
