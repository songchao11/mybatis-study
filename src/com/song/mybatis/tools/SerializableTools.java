package com.song.mybatis.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableTools {

	/*
	 * �����л�
	 */
	public static Object byteArrayToObj(byte[] bt) throws Exception{
		ByteArrayInputStream bais = new ByteArrayInputStream(bt);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}
	
	/*
	 * �������л�
	 */
	public static byte[] ObjToByteArray(Object obj) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		return baos.toByteArray();
	}
	
}
