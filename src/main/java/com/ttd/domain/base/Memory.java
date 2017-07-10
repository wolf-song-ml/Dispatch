package com.ttd.domain.base;

import java.io.File;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import com.ttd.utils.PageUtil;

/**
 * 系统内存
 * @author wolf
 * @since 2016-03-23
 */
public class Memory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String hostName;// 主机名
    private String hostIp;// 主机IP
    private long menoryUsed;// 已用JVM内存
    private String menoryUsedUnit;// 已用JVM内存
    private long menoryMax;// 可用JVM内存
    private String menoryMaxUnit;// 可用JVM内存
    private long totalSpace;// 硬盘空间
    private String totalSpaceUnit;// 硬盘空间
    private long usedSpace;// 已使用硬盘空间
    private String usedSpaceUnit;// 已使用硬盘空间
    private int cpuProcessor;// CPU内核数

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public long getMenoryUsed() {
        return menoryUsed;
    }

    public void setMenoryUsed(long menoryUsed) {
        this.menoryUsed = menoryUsed;
    }

    public long getMenoryMax() {
        return menoryMax;
    }

    public void setMenoryMax(long menoryMax) {
        this.menoryMax = menoryMax;
    }

    public long getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(long totalSpace) {
        this.totalSpace = totalSpace;
    }

    public long getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(long usedSpace) {
        this.usedSpace = usedSpace;
    }

    public int getCpuProcessor() {
        return cpuProcessor;
    }

    public void setCpuProcessor(int cpuProcessor) {
        this.cpuProcessor = cpuProcessor;
    }

    public String getMenoryUsedUnit() {
		return menoryUsedUnit;
	}

	public void setMenoryUsedUnit(String menoryUsedUnit) {
		this.menoryUsedUnit = menoryUsedUnit;
	}

	public String getMenoryMaxUnit() {
		return menoryMaxUnit;
	}

	public void setMenoryMaxUnit(String menoryMaxUnit) {
		this.menoryMaxUnit = menoryMaxUnit;
	}

	public String getTotalSpaceUnit() {
		return totalSpaceUnit;
	}

	public void setTotalSpaceUnit(String totalSpaceUnit) {
		this.totalSpaceUnit = totalSpaceUnit;
	}

	public String getUsedSpaceUnit() {
		return usedSpaceUnit;
	}

	public void setUsedSpaceUnit(String usedSpaceUnit) {
		this.usedSpaceUnit = usedSpaceUnit;
	}

	public static Memory JVMMxMemory() {
        MemoryMXBean mm = (MemoryMXBean) ManagementFactory.getMemoryMXBean();
        MemoryUsage us = mm.getHeapMemoryUsage();
        File baseDir = new File("/data");

        Memory memory = new Memory();
        memory.menoryUsed = us.getUsed();
        memory.menoryUsedUnit = PageUtil.fileSize(memory.menoryUsed);
        memory.menoryMax = us.getMax();
        memory.menoryMaxUnit = PageUtil.fileSize(us.getMax());
        memory.totalSpace = baseDir.getTotalSpace();
        memory.totalSpaceUnit = PageUtil.fileSize(baseDir.getTotalSpace());
        memory.usedSpace = memory.totalSpace - baseDir.getFreeSpace();
        memory.usedSpaceUnit = PageUtil.fileSize(memory.totalSpace - baseDir.getFreeSpace());
        memory.cpuProcessor = Runtime.getRuntime().availableProcessors();
        memory.setHostName(System.getenv("deploy_host_name"));
        memory.setHostIp(System.getenv("deploy_host_ip"));
        return memory;
    }
    
}
