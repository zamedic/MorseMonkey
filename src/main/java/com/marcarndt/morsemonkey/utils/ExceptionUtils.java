package com.marcarndt.morsemonkey.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by arndt on 2017/04/12.
 */
public class ExceptionUtils {

  public static String exceptionStacktraceToString(Exception e)
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    e.printStackTrace(ps);
    ps.close();
    return baos.toString();
  }

}
