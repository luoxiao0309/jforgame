package jforgame.server.utils;

import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jforgame.server.logs.LoggerUtils;

/**
 * json序列号工具（使用jackson）
 * 
 * @author kinson
 *
 */
public final class JsonUtils {

	private static TypeFactory typeFactory = TypeFactory.defaultInstance();

	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static String object2String(Object object) {
		StringWriter writer = new StringWriter();
		try {
			MAPPER.writeValue(writer, object);
		}catch(Exception e) {
			return null;
		}
		return writer.toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> T string2Object(String json, Class<T> clazz) {
		JavaType type = typeFactory.constructType(clazz);
		try {
			return (T) MAPPER.readValue(json, type);
		} catch(Exception e) {
			LoggerUtils.error("", e);
			return null;
		}
	}

	public static String map2String(Map<?,?> map) {
		StringWriter writer = new StringWriter();
		try {
			MAPPER.writeValue(writer, map);
		} catch(Exception e) {
			return null;
		}
		return writer.toString();
	}

	public static Map<String, Object> string2Map(String json) {
		JavaType type = typeFactory.constructMapType(HashMap.class, String.class, Object.class);
		try {
			return MAPPER.readValue(json, type);
		} catch(Exception e) {
			return null;
		}
	}

	public static <K, V> Map<K, V> string2Map(String json, Class<K> keyClazz, Class<V> valueClazz) {
		JavaType type = typeFactory.constructMapType(HashMap.class, keyClazz, valueClazz);
		try {
			return MAPPER.readValue(json, type);
		} catch(Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] string2Array(String json, Class<T> clazz) {
		ArrayType type = typeFactory.constructArrayType(clazz);
		try {
			return (T[]) MAPPER.readValue(json, type);
		} catch(Exception e) {
			return null;
		}
	}

	public static <C extends Collection<E>, E> C string2Collection(String json, Class<C> collectionType,
			Class<E> elemType) {
		JavaType type = typeFactory.constructCollectionType(collectionType, elemType);
		try {
			return MAPPER.readValue(json, type);
		} catch(Exception e) {
			return null;
		}													
	}

}
