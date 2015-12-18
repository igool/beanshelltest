package com.igool.beanshelltest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.scripting.bsh.BshScriptUtils;

import bsh.EvalError;
import bsh.This;

public class TestBeanshell {
    
    private static final Logger logger = Logger.getLogger(TestBeanshell.class);
    
	 @Test
	public void testExcute() throws EvalError
	{
		String scriptSource = "say(name){ return \"hello,\"+name;}";
		SayHello sh = (SayHello) BshScriptUtils.createBshObject(scriptSource, new Class[] { SayHello.class });
		String res = sh.say("vidy");
		logger.debug(res);
	}



	 @Test
	public void testGClass() throws FileNotFoundException, IOException, EvalError, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException
	{
		String scriptSource = IOUtils.toString(new FileInputStream("src/test/java/Result.txt"));
		Result result = (Result) BshScriptUtils.createBshObject(scriptSource, new Class[] { Result.class });
		result.setCode(2);
		logger.debug(result.getCode() + " message " + result.getMessage());
		// classes.getMethod("setMessage", null).invoke(classes, "abc");
		// System.out.println(classes.getMethod("getMessage",
		// null).invoke(classes, null));
	}

	@Test
	public void testGNoClass() throws FileNotFoundException, IOException, EvalError, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException
	{
		String scriptSource = IOUtils.toString(new FileInputStream("src/test/java/ResultNo.txt"));
		Object result = BshScriptUtils.createBshObject(scriptSource);
		logger.debug(result);
		logger.debug(result.getClass());
		logger.debug(result.getClass().getField("_bshThisResultE"));
		logger.debug(((This) result.getClass().getField("_bshThisResultE").get(result)));
		logger.debug(((This) result.getClass().getField("_bshThisResultE").get(result)).invokeMethod("setMessage", new Object[] { "abcALEX" }));
		logger.debug(((This) result.getClass().getField("_bshThisResultE").get(result)).invokeMethod("getMessage", new Object[] {}));
		
		logger.debug(((This) result.getClass().getField("_bshThisResultE").get(result)).invokeMethod("setCode", new Object[] { 1 }));
		logger.debug(((This) result.getClass().getField("_bshThisResultE").get(result)).invokeMethod("getCode", new Object[] {}));
	}
}
