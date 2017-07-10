package com.ttd.utils;

import java.io.Closeable;
import java.io.Flushable;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Closeables;
import com.google.common.io.Flushables;

public class StreamUtil {
	protected final static Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);
    
	/**
     * 刷新流对象
     * @param stream
     */
    public static void flush(Flushable... flush) {
        if (flush == null || flush.length <= 0) {
            return;
        }
        for (Flushable sh : flush) {
            try {
                Flushables.flush(sh, false);
            } catch (Exception e) {
                LOGGER.warn("IOException thrown while flushing Flushable.", e);
            }
        }
    }
	
    /**
     * 关闭流对象
     * @param stream
     */
    public static void close(Closeable... stream) {
        if (stream == null || stream.length <= 0) {
            return;
        }
        for (Closeable close : stream) {
            try {
                Closeables.close(close, false);
            } catch (Exception e) {
                LOGGER.warn("IOException thrown while closing Closeable.", e);
            }
        }
    }

    /**
     * 关闭所有数据库对象
     * @param conn
     * @param stmt
     * @param rs
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            rs = null;
        } catch (Exception e) {
            ;
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
            stmt = null;
        } catch (Exception e) {
            ;
        }
        try {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (Exception e) {
            ;
        }
    }
    
    /**
     * 写入数据到指定流中,并刷新关闭流对象
     * @param data
     * @param out
     */
    public static void writeData(byte[] data,OutputStream out){
        try {
            if(out == null || data == null) {
                return;
            }
            out.write(data);
        } catch(Exception ex){
            LOGGER.error("往OutputStream写入数据异常.",ex);
        } finally {
            flush(out);
            close(out);
        }
    }
    
    public static void writeUnClose(InputStream is,OutputStream os) {
        try {
            byte[] bs = new byte[1024];
            int readed = -1;
            while ((readed = is.read(bs)) != -1) {
                os.write(bs, 0, readed);
            }
            os.flush();
        } catch (Exception e) {
            throw new RuntimeException("往OutputStream写入数据异常.",e);
        } finally {
            close(is);
        }
    }
 
}
