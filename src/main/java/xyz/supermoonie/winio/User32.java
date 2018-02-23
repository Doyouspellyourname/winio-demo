package xyz.supermoonie.winio;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;

import java.util.HashMap;
import java.util.Map;


public class User32 extends org.xvolks.jnative.util.User32{
	private static Map<Integer,Integer> keycache=new HashMap();
	public static int MapVirtualKeyA(int key) throws Exception{
		if(!keycache.containsKey(key)){
			JNative jnative=new JNative(DLL_NAME, "MapVirtualKeyA");
			jnative.setRetVal(Type.INT);
			jnative.setParameter(0, key);
			jnative.setParameter(1, 0);
			jnative.invoke();
			int re=jnative.getRetValAsInt();
			keycache.put(key, re);
		}
		return keycache.get(key);
	}
	
	
}
